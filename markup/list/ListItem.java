package markup.list;

import markup.language.Texable;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class ListItem implements Texable {
    private List<ItemOfList> content;

    public ListItem(List<ItemOfList> content) {
        this.content = content;
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\item ");
        content.forEach(itemOfList -> itemOfList.toTex(builder));
    }
}
