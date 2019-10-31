package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements Markup {
    protected String MarkdownWrapper;
    protected String HtmlWrapper;

    protected List<Markup> content;

    public MarkupElement(List<Markup> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (MarkdownWrapper == null) {
            throw new NullPointerException("Markdown wrapper is not defined");
        }
        builder.append(MarkdownWrapper);
        for (Markup item : content) {
            item.toMarkdown(builder);
        }
        builder.append(MarkdownWrapper);
    }

    @Override
    public void toHtml(StringBuilder builder) {
        if (HtmlWrapper == null) {
            throw new NullPointerException("Html wrapper is not defined");
        }
        builder.append("<").append(HtmlWrapper).append(">");
        for (Markup item : content) {
            item.toHtml(builder);
        }
        builder.append("</").append(HtmlWrapper).append(">");
    }
}
