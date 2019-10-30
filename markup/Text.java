package markup;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Text implements Markup {
    private String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(content);
    }
}
