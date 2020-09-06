package com.zihany.Cloudmusic.search.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class SearchHistoryBean {

    @Property
    String keyowrds;

    @Generated(hash = 1649854464)
    public SearchHistoryBean(String keyowrds) {
        this.keyowrds = keyowrds;
    }

    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }

    public String getKeyowrds() {
        return keyowrds;
    }

    public void setKeyowrds(String keyowrds) {
        this.keyowrds = keyowrds;
    }
}