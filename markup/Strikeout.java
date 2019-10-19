package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strikeout extends Markup {

    public Strikeout(List<Markdownable> content) {
        super(content);
    }

    @Override
    protected String getWrapper() {
        return "~";
    }
}
