package markup.elements;

import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strikeout extends MarkupElement {
    public Strikeout(List<ParagraphItem> content) {
        super(content);
    }

    @Override
    public String getMarkdownTag() {
        return "~";
    }

    @Override
    public String getTexTag() {
        return "textst";
    }
}
