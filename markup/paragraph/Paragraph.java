package markup.paragraph;

import markup.language.Markdownable;
import markup.list.ItemOfList;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Paragraph implements Markdownable, ItemOfList {
    private List<ParagraphItem> content;

    public Paragraph(List<ParagraphItem> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        content.forEach(paragraphItem -> paragraphItem.toMarkdown(builder));
    }

    @Override
    public void toTex(StringBuilder builder) {
        content.forEach(paragraphItem -> paragraphItem.toTex(builder));
    }
}
