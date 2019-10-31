package markup.list;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class UnorderedList extends AbstractList {

    public UnorderedList(List<ListItem> content) {
        super(content);
        TexEnvironment = "itemize";
    }
}
