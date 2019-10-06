package adkl.smarthomevc.smarthome;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adkl.smarthomevc.UserService;

public class Windows {
    private static boolean open = false;

    public static void open() {
        if (open) {
            // throw already open
        }
        else {
            open = true;
            RainbowHatUtils.redLEDOn();
            AirConditioner.switchOff();

        }
    }

    public static void close() {
        if (!open) {
            // throw already closed
        }
        else {
            open = false;
            RainbowHatUtils.redLEDOff();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference windowsRef = db.getReference(String.format("users/%s/windows", UserService.getUserId()));
            windowsRef.setValue(false);
        }
    }

    public static boolean isOpen() {
        return open;
    }
}
