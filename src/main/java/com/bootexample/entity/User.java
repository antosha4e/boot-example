package com.bootexample.entity;

import com.bootexample.util.PasswordEncryptor;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String password; // transient
    @Column(unique = true)
    private String email;

    @ElementCollection//(targetClass = Module.class)
    @CollectionTable(name="user_modules", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="module", nullable = false)
//    @Enumerated(EnumType.STRING)
    private List<String> modules;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.setPassword(password);
    }

    public User(String name, String password, List<String> modules) {
        this.name = name;
        this.setPassword(password);
        this.modules = modules;
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
        this.password = PasswordEncryptor.encryptPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}