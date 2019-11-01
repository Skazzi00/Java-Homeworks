package markup.elements;

import markup.paragraph.ParagraphItem;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strong extends MarkupElement {
    public Strong(List<ParagraphItem> content) {
        super(content, "__", "textbf");
    }
}
