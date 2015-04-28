package cn.itjh.luchat.usertoken.service;


import cn.itjh.luchat.usertoken.domain.UserToken;

/**
 * 
* 用户获取token Service.
* <br>用户获取token Service
* @Copyright itjh
* @Project
* @author 宋立君
* @date 2015年4月21日 下午4:04:27
* @Version 
* @JDK version used 8.0
* @Modification history none
* @Modified by none
 */
public interface UserTokenService {

    /**
     * 
    * redis中用户获取token.
    * <br>在redis中获取用户token
    * @Copyright itjh
    * @Project
    * @param userId
    * @param name
    * @param portraitUri
    * @return
    * @return UserToken 
    * @throws
    * @author 宋立君
    * @date 2015年4月21日 下午4:41:19
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    UserToken getUserTokenByRedis(String userId, String name, String portraitUri);
    
    /**
     * 
    * 获取用户token.
    * <br>连接融云获取用户token，添加到数据库
    * @Copyright itjh
    * @Project
    * @param userId
    * @param name
    * @param portraitUri
    * @return
    * @return UserToken 
    * @throws
    * @author 宋立君
    * @date 2015年4月21日 下午4:43:38
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    UserToken getUserToken(String userId, String name, String portraitUri);
    
    /**
     * 
    * 数据库添加用户token.
    * <br>数据库添加用户token
    * @Copyright itjh
    * @Project
    * @param userToken
    * @return
    * @return int 
    * @throws
    * @author 宋立君
    * @date 2015年4月21日 下午4:52:04
    * @Version 
    * @JDK version used 8.0
    * @Modification history none
    * @Modified by none
     */
    int addUserToken(UserToken userToken);
    
}
