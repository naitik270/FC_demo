package com.ftouchcustomer.Orders;

import android.os.Build;

import java.util.Objects;

public class ClsSelectionModel {

    private String _character = "";

    private boolean isSelected;

    public ClsSelectionModel(String _character, boolean isSelected) {
        this._character = _character;
        this.isSelected = isSelected;
    }

    public String get_character() {
        return _character;
    }

    public void set_character(String _character) {
        this._character = _character;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClsSelectionModel that = (ClsSelectionModel) o;
        return _character.equals(that._character);
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(_character);
        } else {
            return Objects.hash(_character);
        }
    }
}
