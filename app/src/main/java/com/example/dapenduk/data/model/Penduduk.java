package com.example.dapenduk.data.model;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Penduduk {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private  String name;
    @NotNull
    private boolean isMale;
    @NotNull
    private String address;
    @NotNull
    private String bornAt;
    @Nullable
    private String profession;
    @Generated(hash = 2100155708)
    public Penduduk(Long id, @NotNull String name, boolean isMale,
            @NotNull String address, @NotNull String bornAt, String profession) {
        this.id = id;
        this.name = name;
        this.isMale = isMale;
        this.address = address;
        this.bornAt = bornAt;
        this.profession = profession;
    }
    @Generated(hash = 807829749)
    public Penduduk() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getIsMale() {
        return this.isMale;
    }
    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBornAt() {
        return this.bornAt;
    }
    public void setBornAt(String bornAt) {
        this.bornAt = bornAt;
    }
    public String getProfession() {
        return this.profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }

    }