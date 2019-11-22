package com.fancyliu.learningspringboot.dao;

import com.fancyliu.learningspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 类描述:
 * 用户 Repository 接口
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:23
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据手机号查询是否存在该用户
     *
     * @param mobile
     * @return
     */
    int countByMobile(String mobile);

    /**
     * 根据手机号查询密码
     *
     * @param mobile
     * @return
     */
    @Query(value = "select * from users where mobile = ?1", nativeQuery = true)
    User getPassword(String mobile);
}
