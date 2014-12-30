package com.djk_shop.modules;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/12/29.
 */

public class User implements Serializable{

    private static final long serialVersionUID = -8405074855042580853L;


    @DatabaseField( generatedId = true ,columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "user_name")
    private String username;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "id_number")
    private String idNumber;

    @DatabaseField(defaultValue = "male",columnName = "gender")
    private String gender;

    @DatabaseField(defaultValue = "false",columnName = "married")
    private String married;

    @DatabaseField(columnName = "portal_image")
    private String portalImage;

    @DatabaseField(defaultValue = "0",columnName = "status")
    private Integer status;

    public User(Integer id, String username, String password, String gender, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        gender = gender;
        this.status = status;
    }

    public User(Integer id, String username, String password, String idNumber, String gender, String married, String portalImage, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idNumber = idNumber;
        gender = gender;
        this.married = married;
        this.portalImage = portalImage;
        this.status = status;
    }

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getPortalImage() {
        return portalImage;
    }

    public void setPortalImage(String portalImage) {
        this.portalImage = portalImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
