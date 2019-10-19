package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Strong extends Markup {

    public Strong(List<Markdownable> content) {
        super(content);
    }

    @Override
    protected String getWrapper() {
        return "__";
    }
}
