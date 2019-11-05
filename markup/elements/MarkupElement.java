package markup.elements;

import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements ParagraphItem {
    private final String markdownWrapper;
    private final String texWrapper;

    private List<ParagraphItem> content;

    MarkupElement(List<ParagraphItem> content, String markdownWrapper, String texWrapper) {
        this.markdownWrapper = markdownWrapper;
        this.texWrapper = texWrapper;
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(markdownWrapper);
        content.forEach(paragraphItem -> paragraphItem.toMarkdown(builder));
        builder.append(markdownWrapper);
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append("\\").append(texWrapper).append("{");
        content.forEach(paragraphItem -> paragraphItem.toTex(builder));
        builder.append("}");
    }
}
