package md2html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
class Helper {
    static final Map<String, String> tagSymbols = Map.of(
            "*", "em",
            "_", "em",
            "**", "strong",
            "__", "strong",
            "--", "s",
            "`", "code",
            "++", "u"
    );

    static final List<String> specialTags = List.of("[", "]");

    static final List<String> mdTags = new ArrayList<>();
    static final Map<String, String> escapeSymbols = Map.of(
            "<", "&lt;",
            ">", "&gt;"
    );

    static {
        mdTags.addAll(tagSymbols.keySet());
        mdTags.addAll(specialTags);
        mdTags.sort(((o1, o2) -> -Integer.compare(o1.length(), o2.length())));
    }
}
