package com.fancyliu.learningspringboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fancyliu.learningspringboot.model.User;
import com.fancyliu.learningspringboot.response.ResponseData;
import com.fancyliu.learningspringboot.service.UserService;
import com.fancyliu.learningspringboot.util.JwtTokenUtil;
import com.fancyliu.learningspringboot.util.RedisUtil;
import com.fancyliu.learningspringboot.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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


    /**
     * 登录接口
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody String request) {
        //转 JSON 对象
        JSONObject jsonObject = JSON.parseObject(request);

        if (jsonObject.get("mobile") == null || "".equals(jsonObject.get("mobile").toString())) {
            return new ResponseData(false, 400, "登录手机号不能为空", null);
        }
        if (jsonObject.get("password") == null || "".equals(jsonObject.get("password").toString())) {
            return new ResponseData(false, 400, "登录密码不能为空", null);
        }

        String mobile = jsonObject.get("mobile").toString();
        String password = jsonObject.get("password").toString();

        // 判断用户是否存在
        if (userService.countByMobile(mobile) > 0) {
            // 数据库中的密码
            User user = userService.getPassword(mobile);

            // 校验密码是否一致
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {

                // 得到 Token
                Map<String, Object> claims = new HashMap<>(16);

                claims.put("gyennoId", user.getId());
                claims.put("name", user.getName());
                claims.put("mobile", user.getMobile());

                String token = jwtTokenUtil.generate("" + user.getId(), claims);

                // 登录成功后 把token放到Redis Key 存 token ，value 存用户真实姓名
                redisUtil.set(token, user.getName(), 60);

                // 登陆成功后 把token和真实姓名返回
                Map<String, Object> map = new HashMap<>();
                map.put("name", user.getName());
                map.put("token", token);
                return new ResponseData(true, 200, "登录成功", map);
            }
            return new ResponseData(false, 500, "密码错误，请重新输入", null);
        } else {
            return new ResponseData(false, 500, "用户名不存在", null);
        }
    }


    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/user/findAll")
    public ResponseData findAll() {
        List<User> all = this.userService.findAll();

        all.forEach(user -> {
            user.setPassword("******");
        });

        ResponseData result = new ResponseData(true, 200, "查询成功", all);
        return result;
    }

}
