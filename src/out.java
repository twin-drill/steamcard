import java.util.function.Consumer;

public class out {

    private static String a = "";

    private static final Consumer<String> menuPrint = new Consumer<String>() {
        @Override
        public void accept(String s) {
            String[] arr = s.split(" ", s.startsWith(".") ? 3 : 2);
            if (arr.length == 3) {
                a += "\n" + arr[0].substring(1) + " ".repeat(6 - arr[0].length()) +
                        "\\ " + arr[1] + " \\ " + arr[2];
            }
            else if (s.startsWith(">")) {
                a += "\n>\t \\ ";
            }
            else {
                a += "\n\t \\ " + arr[0] + " \\ " + arr[1];
            }
        }
    };

    private static final Consumer<String> indentPrint = new Consumer<String>() {
        @Override
        public void accept(String s) {
            a += "\n\t \\   \\ " + s + "\n";
        }
    };

    private static final Consumer<String> basePrint = new Consumer<String>() {
        @Override
        public void accept(String s) {
            a += "\\ " + s + "\n";
        }
    };

    private static void o(String s, Consumer<? super String> action) {
        a = "";
        s.lines().forEachOrdered(action);
        System.out.print(a.replaceFirst("\\n", ""));
    }

    public static void outl(String s) {
        o(s + "\n", indentPrint);
        System.out.println();
    }

    public static void outi(String s) {
        o(s + "\n>\t \\ ", menuPrint);
    }

    public static void outb(String s) {
        o(s, basePrint);
        System.out.println();
    }

    public static void outr(String s) {
        System.out.print("\\ " + s);
        System.out.flush();
    }
}
