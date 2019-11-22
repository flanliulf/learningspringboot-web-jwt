package com.fancyliu.learningspringboot.util;

import com.fancyliu.learningspringboot.LearningspringbootWebJwtApplicationTests;
import com.fancyliu.learningspringboot.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtilTest extends LearningspringbootWebJwtApplicationTests {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void generate() {
        User user = new User();

        user.setName("张三");
        user.setCc("+86");
        user.setMobile("18600000000");
        user.setPassword(ToolUtil.md5Hex("123456"));
        user.setIdCardNo("420100199011111234");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        Map<String, Object> claims = new HashMap<>(16);

        claims.put("gyennoId", user.getId());
        claims.put("name", user.getName());
        claims.put("mobile", user.getMobile());

        String token = jwtTokenUtil.generate("" + user.getId(), claims);

        System.out.println(token);

        Assert.assertNotNull(token);

    }
}