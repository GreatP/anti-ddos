package com.cetc.security.ddos.persistence;

import javax.persistence.*;
import java.util.*;

/**
 * Created by zhangtao on 2016/6/20.
 */
@Entity
@Table(name="user_po")
public class UserPoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;
    @OneToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="po_id")
    private ProtectObjectEntity protectObjectEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProtectObjectEntity getProtectObjectEntity() {
        return protectObjectEntity;
    }

    public void setProtectObjectEntity(ProtectObjectEntity protectObjectEntity) {
        this.protectObjectEntity = protectObjectEntity;
    }
}
