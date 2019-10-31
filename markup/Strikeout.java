package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strikeout extends MarkupElement {

    public Strikeout(List<Markup> content) {
        super(content);
        MarkdownWrapper = "~";
        HtmlWrapper = "s";
    }
}
