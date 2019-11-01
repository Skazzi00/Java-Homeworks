package markup.list;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class AbstractList implements ItemOfList {
    private final String TexEnvironment;
    private List<ListItem> content;

    protected AbstractList(List<ListItem> content, String texEnvironment) {
        this.content = content;
        TexEnvironment = texEnvironment;
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\begin{").append(TexEnvironment).append("}");
        content.forEach(listItem -> listItem.toTex(builder));
        builder.append("\\end{").append(TexEnvironment).append("}");
    }
}
