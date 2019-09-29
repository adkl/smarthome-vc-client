package adkl.smarthomevc.smarthome;

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
        RainbowHatUtils.display("OFF");
    }

    public static void set(float value) {
        currentSet = value;
        if (AirConditioner.isOn()) {
            RainbowHatUtils.display(value);
        }
    }
}
