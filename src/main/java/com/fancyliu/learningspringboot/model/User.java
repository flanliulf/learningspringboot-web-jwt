package com.fancyliu.learningspringboot.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 类描述:
 * 用户实体类
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:19
 */
@Entity
@Table(name = "users", schema = "springboot-jwt")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cc;
    private String mobile;
    private String name;
    private String password;
    private String idCardNo;
    private Date createdAt;
    private Date updatedAt;

}
