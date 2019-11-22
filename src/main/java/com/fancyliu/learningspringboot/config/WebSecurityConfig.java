package com.fancyliu.learningspringboot.config;

import com.fancyliu.learningspringboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:
 * 自定义拦截配置类
 *
 * @author : Liu Fan
 * @date : 2019/11/22 17:13
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurationSupport {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 注入Bean 让 Spring 扫描 SecurityInterceptor
     * 不然过滤器不起作用
     *
     * @return
     */
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    /**
     * 配置自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        List<String> list = new ArrayList<>();

        // 放行新增用户接口地址
        list.add("/api/user");

        // 放行登陆接口地址
        list.add("/api/login");
        addInterceptor.excludePathPatterns(list);

        // 拦截所有请求
        addInterceptor.addPathPatterns("/**");
    }


    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        /**
         * 在业务处理器处理请求之前被调用
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            // 创建一个输出流
            ServletOutputStream out = response.getOutputStream();

            // 设置编码格式,防止汉字乱码
            OutputStreamWriter ow = new OutputStreamWriter(out, "utf-8");

            // 获取 Token
            String token = request.getHeader("token");

            // 判断 Token 是否为空
            if (token != null) {

                //判断 Token 是否存在
                if (redisUtil.hasKey(token)) {

                    // 如果 Token 存在 重新赋予过期时间 并放行
                    redisUtil.expire(token, 60);
                    return true;
                }

                // 要返回的信息
                ow.write("token错误，请重新登录");
                // 将所有缓冲的数据发送到目的地
                ow.flush();
                ow.close();
                // 拦截
                return false;
            }
            ow.write("token为空，请重新登录");
            ow.flush();
            ow.close();
            return false;
        }
    }
}
