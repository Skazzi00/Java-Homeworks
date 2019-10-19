package markup;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public interface Markdownable {
    StringBuilder toMarkdown();

    void toMarkdown(StringBuilder builder);
}
