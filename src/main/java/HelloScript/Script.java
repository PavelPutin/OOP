package HelloScript;

import java.util.ArrayList;
import java.util.List;

public class Script {
    private final List<Printer> printers;

    public Script() {
        printers = new ArrayList<>();
    }

    public void add(Printer printer) {
        printers.add(printer);
    }

    public void run() {
        for (Printer p : printers) {
            p.print();
        }
    }
}
