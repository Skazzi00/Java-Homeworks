package markup.list;

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
        content.forEach(listItem -> listItem.toTex(builder));
        builder.append("\\end{").append(TexEnvironment).append("}");
    }
}
