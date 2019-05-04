package com.tayara.abdulrahman.telephoneguide;

import android.database.Cursor;

import java.io.Serializable;


public class Contact implements Serializable, Comparable<Contact> {
    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String mobileNumber;
    private String birthDay;
    private String address;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String telephoneNumber,
                   String mobileNumber, String birthDay, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.mobileNumber = mobileNumber;
        this.birthDay = birthDay;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setData(Cursor cursor) {
        id = cursor.getInt(0);
        firstName = cursor.getString(1);
        lastName = cursor.getString(2);
        telephoneNumber = cursor.getString(3);
        mobileNumber = cursor.getString(4);
        birthDay = cursor.getString(5);
        address = cursor.getString(6);
    }

    public String getFullName() {
        if (firstName.length() > 0) {
            return (firstName + " " + lastName);
        } else if (lastName.length() > 0) {
            return lastName;
        } else if (mobileNumber.length() > 0) {
            return mobileNumber;
        } else if (telephoneNumber.length() > 0) {
            return telephoneNumber;
        }
        return "";
    }

    @Override
    public int compareTo(Contact o) {
        return (this.getFullName().compareToIgnoreCase(o.getFullName()));
    }
}
