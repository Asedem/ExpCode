package tk.expcode.expcode.utils;

public class Cryptor {

    private static final String crypt = "";

    private Cryptor() {
    }

    public static String crypt(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.toCharArray().length; i++) {
            char character = text.toCharArray()[i];
            builder.append((char) (character - ((i + 1) * 2)));
        }
        return builder.toString();
    }

    public static String decrypt(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.toCharArray().length; i++) {
            char character = text.toCharArray()[i];
            builder.append((char) (character + ((i + 1) * 2)));
        }
        return builder.toString();
    }
}
