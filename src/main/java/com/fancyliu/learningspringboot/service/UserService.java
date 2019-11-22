package com.fancyliu.learningspringboot.service;

import com.fancyliu.learningspringboot.model.User;

import java.util.List;

/**
 * 类描述:
 * 用户 service 接口
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:29
 */
public interface UserService {

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User save(User user);

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
    User getPassword(String mobile);


    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAll();
}
