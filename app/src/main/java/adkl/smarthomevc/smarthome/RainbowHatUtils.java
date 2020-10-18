package adkl.smarthomevc.smarthome;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

public class RainbowHatUtils {
    private static AlphanumericDisplay segment;

    public static void initializeComponents() {
        try {
            segment = RainbowHat.openDisplay();
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            segment.setEnabled(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void display(Object text) {
        try {
            segment.display(text.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disposeComponents() {
        try {
            segment.setEnabled(false);
            segment.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
