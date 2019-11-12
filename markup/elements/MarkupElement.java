package markup.elements;

import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements ParagraphItem {
    private List<ParagraphItem> content;

    MarkupElement(List<ParagraphItem> content) {
        this.content = content;
    }

    abstract String getMarkdownTag();

    abstract String getTexTag();

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(getMarkdownTag());
        content.forEach(paragraphItem -> paragraphItem.toMarkdown(builder));
        builder.append(getMarkdownTag());
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\").append(getTexTag()).append("{");
        content.forEach(paragraphItem -> paragraphItem.toTex(builder));
        builder.append("}");
    }
}
