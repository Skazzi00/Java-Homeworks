package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strong extends MarkupElement {

    public Strong(List<Markup> content) {
        super(content);
        MarkdownWrapper = "__";
        HtmlWrapper = "strong";
    }
}
