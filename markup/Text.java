package markup;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Text implements Markdownable {

    private String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public StringBuilder toMarkdown() {
        StringBuilder builder = new StringBuilder();
        toMarkdown(builder);
        return builder;
    }

    @Override
    public void toMarkdown(StringBuilder builder) {
        builder.append(content);
    }
}
