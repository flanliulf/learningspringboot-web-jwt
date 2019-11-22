package com.fancyliu.learningspringboot.service.impl;

import com.fancyliu.learningspringboot.dao.UserRepository;
import com.fancyliu.learningspringboot.model.User;
import com.fancyliu.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
