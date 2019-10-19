package markup;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Paragraph extends Markup {

    public Paragraph(List<Markdownable> content) {
        super(content);
    }


    @Override
    protected String getWrapper() {
        return "";
    }
}
