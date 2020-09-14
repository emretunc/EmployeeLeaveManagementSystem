package com.example.employeeleavemanagement.utilities;


public enum Roles{
    ROLE_ADMIN(1), ROLE_MANAGER(2), ROLE_EMPLOYEE(3);
    private int value;

    Roles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
