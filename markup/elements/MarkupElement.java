package markup.elements;

import markup.exceptions.WrapperNotDefinedException;
import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements ParagraphItem {
    protected String MarkdownWrapper;
    protected String TexWrapper;

    private List<ParagraphItem> content;

    protected MarkupElement(List<ParagraphItem> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (MarkdownWrapper == null) {
            throw new WrapperNotDefinedException("Markdown wrapper is not defined");
        }
        builder.append(MarkdownWrapper);
        content.forEach(paragraphItem -> paragraphItem.toMarkdown(builder));
        builder.append(MarkdownWrapper);
    }

    @Override
    public void toTex(StringBuilder builder) {
        if (TexWrapper == null) {
            throw new WrapperNotDefinedException("Tex wrapper is not defined");
        }
        builder.append("\\").append(TexWrapper).append("{");
        content.forEach(paragraphItem -> paragraphItem.toTex(builder));
        builder.append("}");
    }

}
