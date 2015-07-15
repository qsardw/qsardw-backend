package org.qsardw.datamodel.beans;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
@XmlRootElement
public class User {
    private Integer id;
    private String username;
    private String password;
    private String rol;
    private String completeName;
    private String photo;
    private Timestamp ts;

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}
