package com.example.dapenduk.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Admin {

    @Id
    private String username;

    @NotNull
    private String password;

    @Generated(hash = 1958200625)
    public Admin(String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    @Generated(hash = 1708792177)
    public Admin() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
