package markup.list;

import markup.Texable;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class AbstractList implements ItemOfList {
    protected String TexEnvironment;
    private List<ListItem> content;

    public AbstractList(List<ListItem> content) {
        this.content = content;
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\begin{").append(TexEnvironment).append("}");
        for (Texable item : content) {
            item.toTex(builder);
        }
        builder.append("\\end{").append(TexEnvironment).append("}");
    }
}
