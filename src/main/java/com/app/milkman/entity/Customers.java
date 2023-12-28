package com.app.milkman.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "customers", schema = "milkman")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customerid")
    private String customerid;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "pphone")
    private String pphone;

    @Column(name = "sphone")
    private String sphone;

    @Column(name = "emailid")
    private String emailid;

    @Column(name = "dob")
    private String dob;

    @Column(name = "auth_pin")
    private String authPin;

    @Column(name = "address")
    private String address;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "createdby")
    private String createdby;

    @Column(name = "createdtime")
    private LocalDateTime createdtime;

    @Column(name = "updatedby")
    private String updatedby;

    @Column(name = "updatedtime")
    private LocalDateTime updatedtime;

    @Column(name = "status")
    private String status;

}
