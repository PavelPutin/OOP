package HelloScript;

public class App {
    public static void main(String[] args) {
        Script s = new Script();
        s.add(new HelloPrinter());
        s.add(new DatePrinter());
        s.add(new DatePrinter());
        s.run();
    }
}
