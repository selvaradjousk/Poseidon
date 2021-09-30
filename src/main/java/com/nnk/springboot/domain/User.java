package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private static final int VARIABLE_LENGTH_125 = 125;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

	@Column(name = "username", length = VARIABLE_LENGTH_125, nullable = false)
	@NotBlank(message = "Username is mandatory")
    private String username;

	@Column(name = "password", length = VARIABLE_LENGTH_125, nullable = false)
	@NotBlank(message = "Password is mandatory")
    private String password;

	@Column(name = "fullname", length = VARIABLE_LENGTH_125, nullable = false)
	@NotBlank(message = "FullName is mandatory")
    private String fullname;

	@Column(name = "role", length = VARIABLE_LENGTH_125, nullable = false)
	@NotBlank(message = "Role is mandatory")
    private String role;

    public User(final String username, final String password, final String fullname, final String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}
