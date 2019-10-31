package markup.elements;

import markup.paragraph.ParagraphItem;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Text implements ParagraphItem {

    private String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(content);
    }

    @Override
    public void toTex(StringBuilder builder) {
        builder.append(content);
    }
}
