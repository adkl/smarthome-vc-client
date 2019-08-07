package adkl.smarthomevc.smarthome;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

public class Windows {
    private static boolean open = false;

    public static void open() {
        if (open) {
            // throw already open
        }
        else {
            open = true;
            RainbowHatUtils.redLEDOn();
        }
    }

    public static void close() {
        if (!open) {
            // throw already closed
        }
        else {
            open = false;
            RainbowHatUtils.redLEDOff();
        }
    }

    public static boolean isOpen() {
        return open;
    }
}
