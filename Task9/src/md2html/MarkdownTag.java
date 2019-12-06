package md2html;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
class MarkdownTag {
    final String tag;
    final int index;

    MarkdownTag(String tag, int index) {
        this.tag = tag;
        this.index = index;
    }
}
