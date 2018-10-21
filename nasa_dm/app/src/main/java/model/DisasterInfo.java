package model;

public class DisasterInfo {

    public static final String IMAGE_BASE = "";
    private String shelterSteps;
    private String evacSteps;
    private String name;
    private String videoLink;
    private String disasterDesc;
    private String image;
    private String bgImage;


    public String getShelterSteps() {
        return shelterSteps;
    }

    public void setShelterSteps(String shelterSteps) {
        this.shelterSteps = shelterSteps;
    }

    public String getEvacSteps() {
        return evacSteps;
    }

    public void setEvacSteps(String evacSteps) {
        this.evacSteps = evacSteps;
    }

    public String getDisasterName() {
        return name;
    }

    public void setDisasterName(String name) {
        this.name = name;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDisasterDesc() {
        return disasterDesc;
    }

    public void setDisasterDesc(String disasterDesc) {
        this.disasterDesc = disasterDesc;
    }

    public String getDisasterImage() {
        return image;
    }

    public void setDisasterImage(String image) {
        this.image = image;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }
}
