package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class Markup implements Markdownable {
    private List<Markdownable> content;

    public Markup(List<Markdownable> content) {
        this.content = content;
    }

    protected abstract String getWrapper();

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(getWrapper());
        for (Markdownable item : content) {
            builder.append(item.toMarkdown());
        }
        builder.append(getWrapper());
    }

    @Override
    public final StringBuilder toMarkdown() {
        StringBuilder builder = new StringBuilder();
        toMarkdown(builder);
        return builder;
    }

}
