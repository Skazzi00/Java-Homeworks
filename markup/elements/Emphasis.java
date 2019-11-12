package markup.elements;

import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Emphasis extends MarkupElement {
    public Emphasis(List<ParagraphItem> content) {
        super(content);
    }

    @Override
    public String getMarkdownTag() {
        return "*";
    }

    @Override
    public String getTexTag() {
        return "emph";
    }
}
