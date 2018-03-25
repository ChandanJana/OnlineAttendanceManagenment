package com.example.chandan.theprofessional.Model;

/**
 * Created by SUBHADIP on 20-09-2017.
 */

public class Student {
    private String name, mob, class1, batch, picpath, email;
    private int id;
    private Boolean chkItem;

    public Student(String name, String mob, String class1, String batch, String picpath, int id, Boolean chkItem, String email) {
        this.name = name;
        this.mob = mob;
        this.class1 = class1;
        this.batch = batch;
        this.picpath = picpath;
        this.id = id;
        this.chkItem = chkItem;
        this.email = email;
//        Log.i("Student: Call ", "Okay");
//        Log.i("Student: Name ", this.name);
//        Log.i("Student: Mob ", this.mob);
//        Log.i("Student: class ", this.class1);
//        Log.i("Student: batch ", this.batch);
//        Log.i("Student: Image ", this.picpath);
//        Log.i("Student: id ", String.valueOf(this.id));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return this.mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getClass1() {
        return this.class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPicpath() {
        return this.picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getChkItem() {
        return this.chkItem;
    }

    public void setChkItem(Boolean chkItem) {
        this.chkItem = chkItem;
    }

    public boolean ischkItem() {
        return this.ischkItem();
    }
}
