package com.mgt.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String full_name;

    /*
    @Column(name="phone_no")
    private String phone_no;
*/
    @Column(name="store_type")
    private String store_type;

    @Column(name="user_role")
    private String user_role;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Transient
    private String confirm_password;
}
