package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Paragraph extends MarkupElement {

    public Paragraph(List<Markup> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        for (Markup item : content) {
            item.toMarkdown(builder);
        }
    }

    @Override
    public void toHtml(StringBuilder builder) {
        for (Markup item : content) {
            item.toHtml(builder);
        }
    }
}
