package task2;

public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            for (String token : arg.split("[^\\p{Graph}]")) {
                if (!token.isBlank()) {
                    sum += Integer.parseInt(token);
                }
            }
        }
        System.out.println(sum);
    }
}