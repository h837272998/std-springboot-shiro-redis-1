package com.hjh.springbootshiro2.pojo;

public class User {
	private Long id;

	private String name;

	private String password;

	private String salt;

    public User(User user) {
    	this.setId(user.id);
    	this.setName(user.name);
    	this.setPassword(user.password);
    	this.setSalt(user.salt);
    }

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}
}