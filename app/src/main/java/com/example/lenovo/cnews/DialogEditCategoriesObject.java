package com.example.lenovo.cnews;

/**
 * Created by Lenovo on 01-09-2016.
 */
public class DialogEditCategoriesObject {

    String Name ;
    Boolean checkboxValue ;

    public DialogEditCategoriesObject(String name, Boolean checkboxValue) {
        Name = name;
        this.checkboxValue = checkboxValue;
    }

    public DialogEditCategoriesObject() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getCheckboxValue() {
        return checkboxValue;
    }

    public void setCheckboxValue(Boolean checkboxValue) {
        this.checkboxValue = checkboxValue;
    }
}
