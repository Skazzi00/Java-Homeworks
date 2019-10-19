package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Emphasis extends Markup {

    public Emphasis(List<Markdownable> content) {
        super(content);
    }

    @Override
    protected String getWrapper() {
        return "*";
    }
}
