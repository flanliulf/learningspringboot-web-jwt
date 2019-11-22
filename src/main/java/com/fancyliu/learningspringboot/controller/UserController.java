package com.fancyliu.learningspringboot.controller;

import com.fancyliu.learningspringboot.model.User;
import com.fancyliu.learningspringboot.response.ResponseData;
import com.fancyliu.learningspringboot.service.UserService;
import com.fancyliu.learningspringboot.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 类描述:
 * 用户 controller
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:34
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseData save() {
        User user = new User();

        user.setName("张三");
        user.setCc("+86");
        user.setMobile("18600000000");
        user.setPassword(ToolUtil.md5Hex("123456"));
        user.setIdCardNo("420100199011111234");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        User save = userService.save(user);

        ResponseData result = new ResponseData(true, 200, "保存成功", save);

        return result;
    }
}
