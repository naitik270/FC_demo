package com.ftouchcustomer.Classes;

public class ClsSettingNameVal {

    private int Images;
    private int SettingName = 0;
    private int Description = 0;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position = 0;

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public int getSettingName() {
        return SettingName;
    }

    public void setSettingName(int settingName) {
        SettingName = settingName;
    }

    public int getDescription() {
        return Description;
    }

    public void setDescription(int description) {
        Description = description;
    }

    public ClsSettingNameVal(int ic_profile, int setting_profile,
                             int setting_profile_description,int position) {
        this.Images = ic_profile;
        this.SettingName = setting_profile;
        this.Description = setting_profile_description;
        this.position = position;
    }


}
