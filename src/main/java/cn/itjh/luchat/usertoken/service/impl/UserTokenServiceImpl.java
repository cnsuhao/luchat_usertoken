package cn.itjh.luchat.usertoken.service.impl;

import javax.annotation.Resource;

import cn.itjh.luchat.usertoken.mapper.UserTokenMapper;
import cn.itjh.luchat.usertoken.domain.UserToken;
import cn.itjh.luchat.usertoken.service.UserTokenService;
import cn.itjh.luchat.usertoken.util.io.rong.ApiHttpClient;
import cn.itjh.luchat.usertoken.util.io.rong.models.FormatType;
import cn.itjh.luchat.usertoken.util.io.rong.models.SdkHttpResult;
import cn.itjh.luchat.usertoken.util.io.rong.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 管理用户token Service. <br>
 * 管理用户token Service
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2015年4月21日 下午4:14:23
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {
    
    private static final Logger logger = Logger.getLogger(UserTokenServiceImpl.class.getName());


    @Value("${rong.key}")
    String key = "mgb7ka1nb54xg";
    @Value("${rong.secret}")
    String secret = "oMF4bLMlWp";

    @Resource
    private UserTokenMapper userTokenMapper;

    @Resource
    private RedisTemplate<String, UserToken> redisTemplate;

    @Override
    public UserToken getUserTokenByRedis(String userId, String name, String portraitUri) {

        UserToken userToken = redisTemplate.opsForValue().get("userId_"+userId);
        if (null == userToken) {
            userToken = getUserToken(userId, name, portraitUri);
        }
        return userToken;
    }

    @Override
    public UserToken getUserToken(String userId, String name, String portraitUri) {
        SdkHttpResult result = null;
        UserToken userToken = new UserToken();
        try {
            result = ApiHttpClient.getToken(key, secret, userId, name, portraitUri, FormatType.json);
            if (!StringUtils.isEmpty(result.toString())) {// 获取成功
                userToken = (UserToken) GsonUtil.fromJson(GsonUtil.jsonGetElementByKey(result.toString(), "result"), UserToken.class);
                if (null != userToken) {
                    // 存入数据库
                    int count = addUserToken(userToken);
                    if (count > 0) {
                        logger.info("用户id: "+userId + "存入数据库成功");
                    }
                }
                logger.info("用户id: "+userId + "存入Redis");
                //存入Redis
                redisTemplate.opsForValue().set("userId_"+userId, userToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userToken;
    }

    @Override
    public int addUserToken(UserToken userToken) {
        return userTokenMapper.addUserToken(userToken);
    }
}
