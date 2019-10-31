package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements Markup {
    protected String MarkdownWrapper = "MARKDOWN_WRAPPER";
    protected String HtmlWrapper = "HTML_WRAPPER";

    protected List<Markup> content;

    public MarkupElement(List<Markup> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(MarkdownWrapper);
        for (Markup item : content) {
            item.toMarkdown(builder);
        }
        builder.append(MarkdownWrapper);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        builder.append("<").append(HtmlWrapper).append(">");
        for (Markup item : content) {
            item.toHtml(builder);
        }
        builder.append("</").append(HtmlWrapper).append(">");
    }
}
