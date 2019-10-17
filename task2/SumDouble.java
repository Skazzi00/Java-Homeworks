/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class SumDouble {
    public static void main(String[] args) {
        double sum = 0;
        for (String arg : args) {
            for (String token : arg.split("\\p{javaWhitespace}")) {
                if (!token.isEmpty()) {
                    sum += Double.parseDouble(token);
                }
            }
        }
        System.out.println(sum);
    }
}