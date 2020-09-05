package com.ftouchcustomer.Language;

public class ClsLanguageGetSet {

    String languageName = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code = "";
    boolean isSelected = false;

    public ClsLanguageGetSet(String languageName, String code, boolean isSelected) {
        this.languageName = languageName;
        this.isSelected = isSelected;
        this.code = code;
    }

    public ClsLanguageGetSet() {

    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
