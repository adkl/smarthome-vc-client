package adkl.smarthomevc.tasks;

import java.io.Serializable;

public class OnOffTask implements Serializable {
    private String whatToToggle;
    private Boolean toggle;

    public OnOffTask() { }

    public OnOffTask(String whatToToggle, Boolean toggle) {
        this.whatToToggle = whatToToggle;
        this.toggle = toggle;
    }

    public String getWhatToToggle() {
        return whatToToggle;
    }

    public void setWhatToToggle(String whatToToggle) {
        this.whatToToggle = whatToToggle;
    }

    public Boolean getToggle() {
        return toggle;
    }

    public void setToggle(Boolean toggle) {
        this.toggle = toggle;
    }
}
