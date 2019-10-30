package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strong extends MarkupElement {

    private static final String MARKDOWN_WRAPPER = "__";

    public Strong(List<Markup> content) {
        super(content);
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(MARKDOWN_WRAPPER);
        super.toMarkdown(builder);
        builder.append(MARKDOWN_WRAPPER);
    }
}
