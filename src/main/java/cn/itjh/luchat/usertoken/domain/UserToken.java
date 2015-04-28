package cn.itjh.luchat.usertoken.domain;

import java.io.Serializable;

/**
 * 
* 用户token 实体类.
* <br>用户token 实体类
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2015年4月21日 下午4:06:00
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public class UserToken implements Serializable {

//    private static final long serialVersionUID = 3729462256429865093L;
    private String userId;
    private int code;
    private String token;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserToken(String userId, int code, String token) {
        this.userId = userId;
        this.code = code;
        this.token = token;
    }

    public String toString() {
        return "UserToken [userId=" + this.userId + ", code=" + this.code + ", token=" + this.token + "]";
    }

    public UserToken() {
    }
}
