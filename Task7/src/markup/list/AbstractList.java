package markup.list;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class AbstractList implements ItemOfList {
    AbstractList(List<ListItem> content) {
        this.content = content;
    }

    private List<ListItem> content;

    abstract String getTexEnvironment();

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\begin{").append(getTexEnvironment()).append("}");
        content.forEach(listItem -> listItem.toTex(builder));
        builder.append("\\end{").append(getTexEnvironment()).append("}");
    }
}
