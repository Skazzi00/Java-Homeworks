package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements Markup {
    private List<Markup> content;

    public MarkupElement(List<Markup> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        for (Markup item : content) {
            item.toMarkdown(builder);
        }
    }
}
