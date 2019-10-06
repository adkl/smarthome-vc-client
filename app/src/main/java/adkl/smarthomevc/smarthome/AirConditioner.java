package adkl.smarthomevc.smarthome;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adkl.smarthomevc.UserService;

public class AirConditioner {
    private static float currentSet = 20f;
    private static boolean isOn = false;

    public static boolean isOn() {
        return isOn;
    }

    public static void switchOn() {
        if (isOn) {
            // throw already on
        }
        else {
            isOn = true;
        }
        RainbowHatUtils.display(currentSet);
        Windows.close();

    }

    public static void switchOff() {
        if (!isOn) {
            // throw already off
        }
        else {
            isOn = false;
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference climateRef = db.getReference(String.format("users/%s/air-condition-status", UserService.getUserId()));
        climateRef.setValue(false);
        RainbowHatUtils.display("OFF");
    }

    public static void set(float value) {
        currentSet = value;
        if (AirConditioner.isOn()) {
            RainbowHatUtils.display(value);
        }
    }
}
