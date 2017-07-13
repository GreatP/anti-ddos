package com.cetc.backend.view;

import java.util.Date;

public class UserForm {
	private int id;
	private String username;
	private String password;
	private Date createTime;
	private String nickName;
	private String role;
	private boolean changePasswd;
	private String newPasswd;
    /**************for clean dev*****************/
    private String cleanInport;
    private String cleanOutport;
    private int cleanDevId;
	/********************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getCleanInport() {
        return cleanInport;
    }

    public void setCleanInport(String cleanInport) {
        this.cleanInport = cleanInport;
    }

    public String getCleanOutport() {
        return cleanOutport;
    }

    public void setCleanOutport(String cleanOutport) {
        this.cleanOutport = cleanOutport;
    }

    public int getCleanDevId() {
        return cleanDevId;
    }

    public void setCleanDevId(int cleanDevId) {
        this.cleanDevId = cleanDevId;
    }

    public boolean isChangePasswd() {
		return changePasswd;
	}
	public void setChangePasswd(boolean changePasswd) {
		this.changePasswd = changePasswd;
	}
	public String getNewPasswd() {
		return newPasswd;
	}
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
