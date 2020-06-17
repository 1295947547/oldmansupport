package com.example.oldmansupport.maninfo;

import java.lang.ref.SoftReference;

/**
 * Created by littlecurl 2018/6/24
 */
public class User {
    private int _id;
    private String phonenumber;
    private String password;
    private String name;
    private String sex;
    private String birth;
    private String healthstate;
    private String address;
    private String idcard;



    //密码
    public User(int _id,String phonenumber,String password,String name, String sex,String birth,String healthstate,String address,String idcard) {
        this._id=_id;
        this.phonenumber = phonenumber;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.healthstate = healthstate;
        this.address = address;
        this.idcard = idcard;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHealthstate() {
        return healthstate;
    }

    public void setHealthstate(String healthstate) {
        this.healthstate = healthstate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


