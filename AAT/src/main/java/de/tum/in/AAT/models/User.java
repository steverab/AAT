package de.tum.in.AAT.models;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

@Entity
public class User {

    @Id
    private Long id;

    @Index
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Index
    private String token;

    /* ---------------------------------- */

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = hashPassword(password);
    }

    public static String hashPassword(String submittedPassword) {
        String password = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(submittedPassword.getBytes("UTF-8"));
            password = DatatypeConverter.printHexBinary(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /* ---------------------------------- */

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    /* ---------------------------------- */

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

}
