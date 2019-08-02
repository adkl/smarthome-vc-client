package adkl.smarthomevc.smarthome;

public class Windows {
    private static boolean open = false;

    public static void open() {
        if (open) {
            // throw already open
        }
        else {
            open = true;
            // switch on the LED
        }
    }

    public static void close() {
        if (!open) {
            // throw already closed
        }
        else {
            open = false;
            // switch off the LED
        }
    }

    public static boolean isOpen() {
        return open;
    }
}
