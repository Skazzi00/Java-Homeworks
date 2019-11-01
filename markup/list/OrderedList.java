package markup.list;

import java.util.List;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> content) {
        super(content, "enumerate");
    }
}
