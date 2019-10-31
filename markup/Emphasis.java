package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Emphasis extends MarkupElement {

    public Emphasis(List<Markup> content) {
        super(content);
        MarkdownWrapper = "*";
        HtmlWrapper = "em";
    }

}
