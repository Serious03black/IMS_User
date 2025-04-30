package com.mgt.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "name")
	private String full_name;

	@Column(name = "store_type")
	private String store_type;

	@Column(name = "email" )
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> products;

	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;

	@Transient
	private String confirm_password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getStore_type() {
		return store_type;
	}

	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User() {
		this.status = Status.PENDING;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", full_name=" + full_name + ", store_type=" + store_type + ", email=" + email
				+ ", password=" + password + ", products=" + products + ", status=" + status + ", confirm_password="
				+ confirm_password + ", getId()=" + getId() + ", getFull_name()=" + getFull_name()
				+ ", getStore_type()=" + getStore_type() + ", getEmail()=" + getEmail() + ", getPassword()="
				+ getPassword() + ", getConfirm_password()=" + getConfirm_password() + ", getClass()=" + getClass()
				+ ", getStatus()=" + getStatus() + ", getProducts()=" + getProducts() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public User(Long id, String full_name, String store_type, String email, String password, List<Product> products,
			Status status, String confirm_password) {
		this.id = id;
		this.full_name = full_name;
		this.store_type = store_type;
		this.email = email;
		this.password = password;
		this.products = products;
		this.status = status;
		this.confirm_password = confirm_password;
	}

	
	
}
