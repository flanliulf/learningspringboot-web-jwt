package com.fancyliu.learningspringboot.dao;

import com.fancyliu.learningspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类描述:
 * 用户 Repository 接口
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:23
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
