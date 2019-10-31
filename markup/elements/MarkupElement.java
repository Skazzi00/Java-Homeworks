package markup.elements;

import markup.Markdownable;
import markup.Texable;
import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public abstract class MarkupElement implements ParagraphItem {
    protected String MarkdownWrapper;
    protected String TexWrapper;

    protected List<ParagraphItem> content;

    public MarkupElement(List<ParagraphItem> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        if (MarkdownWrapper == null) {
            throw new NullPointerException("Markdown wrapper is not defined");
        }
        builder.append(MarkdownWrapper);
        for (Markdownable item : content) {
            item.toMarkdown(builder);
        }
        builder.append(MarkdownWrapper);
    }

    @Override
    public void toTex(StringBuilder builder) {
        if (TexWrapper == null) {
            throw new NullPointerException("Tex wrapper is not defined");
        }
        builder.append("\\").append(TexWrapper).append("{");
        for (Texable item : content) {
            item.toTex(builder);
        }
        builder.append("}");
    }

}
