/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.users;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WIN
 */
public class UsersDTO implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private int role;
    private boolean status;
    private Date dateOfCreate;
    private String address;
    private int phone;

    public UsersDTO() {
    }

    
    public UsersDTO(String username, String password, String fullName, int role, boolean status) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
    }

    public UsersDTO(String username, String password, String fullName, int role, boolean status, Date dateOfCreate) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.dateOfCreate = dateOfCreate;
    }

    public UsersDTO(String username, String password, String fullName, int role, boolean status, Date dateOfCreate, String address, int phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.dateOfCreate = dateOfCreate;
        this.address = address;
        this.phone = phone;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

}
