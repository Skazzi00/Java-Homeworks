package markup.list;

import markup.Texable;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class ListItem implements Texable {

    List<ItemOfList> content;

    public ListItem(List<ItemOfList> content) {
        this.content = content;
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\item ");
        for (Texable item : content) {
            item.toTex(builder);
        }
    }
}
