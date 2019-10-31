package markup.paragraph;

import markup.Markdownable;
import markup.Markup;
import markup.Texable;
import markup.list.ItemOfList;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Paragraph implements Markup, ItemOfList {
    private List<ParagraphItem> content;

    public Paragraph(List<ParagraphItem> content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        for (Markdownable item : content) {
            item.toMarkdown(builder);
        }
    }

    @Override
    public void toTex(StringBuilder builder) {
        for (Texable item : content) {
            item.toTex(builder);
        }
    }
}
