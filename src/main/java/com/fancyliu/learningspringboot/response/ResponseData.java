package com.fancyliu.learningspringboot.response;

import lombok.Data;

/**
 * 类描述:
 * 统一响应返回对象封装
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:47
 */
@Data
public class ResponseData {
    
    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
