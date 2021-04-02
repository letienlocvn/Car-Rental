/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.authenticate;

import java.io.Serializable;

/**
 *
 * @author WIN
 */
public class AuthenticateDTO implements Serializable {

    private String id;
    private String email;
    private String code;
    private String username;

    public AuthenticateDTO(String id, String email, String code, String username) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
