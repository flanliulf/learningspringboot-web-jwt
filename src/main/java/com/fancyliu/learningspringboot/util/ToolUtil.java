package com.fancyliu.learningspringboot.util;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * 类描述:
 * 常用工具类
 *
 * @author : Liu Fan
 * @date : 2019/11/22 14:59
 */
public class ToolUtil {
    /**
     * 默认密码盐长度
     */
    public static final int SALT_LENGTH = 6;

    /**
     * md5加密(加盐)
     *
     * @param password 需要加密的密码
     * @param salt     盐值
     * @return
     */
    public static String md5Hex(String password, String salt) {
        return md5Hex(password + salt);
    }

    /**
     * md5加密(不加盐)
     *
     * @param str 需要加密的字符串
     * @return
     */
    public static String md5Hex(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < bs.length; i++) {
                if (Integer.toHexString(0xFF & bs[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & bs[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & bs[i]));
                }
            }
            return md5StrBuff.toString();
        } catch (Exception e) {
            throw new RuntimeException("加密错误");
        }
    }

    public static void main(String[] args) {
        String s1 = ToolUtil.md5Hex("123456");
        String s2 = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(s1);
        System.out.println(s2);
    }
}
