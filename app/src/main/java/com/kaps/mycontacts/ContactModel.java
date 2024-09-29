package com.kaps.mycontacts;

public class ContactModel {
    int userImg;
    int id;
    String name ,phone;

    public ContactModel(int userImg) {
        this.userImg = userImg;
    }

    public ContactModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactModel(int userImg, String name, String phone) {
        this.userImg = userImg;
        this.name = name;
        this.phone = phone;
    }
    public ContactModel(int userImg,int id, String name, String phone) {

        this.userImg = userImg;
        this.id=id;
        this.name = name;
        this.phone = phone;
    }

}

