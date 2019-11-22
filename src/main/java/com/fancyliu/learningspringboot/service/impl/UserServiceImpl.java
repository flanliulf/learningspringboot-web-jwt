package com.fancyliu.learningspringboot.service.impl;

import com.fancyliu.learningspringboot.dao.UserRepository;
import com.fancyliu.learningspringboot.model.User;
import com.fancyliu.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述:
 * 用户 service 实现类
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 根据手机号查询是否存在该用户
     *
     * @param mobile
     * @return
     */
    @Override
    public int countByMobile(String mobile) {
        return this.userRepository.countByMobile(mobile);
    }

    /**
     * 根据手机号查询密码
     *
     * @param mobile
     * @return
     */
    @Override
    public User getPassword(String mobile) {
        return this.userRepository.getPassword(mobile);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
