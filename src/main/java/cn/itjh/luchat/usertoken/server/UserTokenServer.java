package cn.itjh.luchat.usertoken.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import cn.itjh.luchat.usertoken.domain.UserToken;
import cn.itjh.luchat.usertoken.service.UserTokenService;
import cn.itjh.luchat.usertoken.util.io.rong.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;



import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * 
 * 用户获取token 接口管理. <br>
 * 用户获取token 接口管理
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2015年4月21日 下午4:04:52
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Api(value = "/userToken", description = "管理用户token接口")
@Path("/userToken")
@Repository
public class UserTokenServer {

    private static final Logger logger = Logger.getLogger(UserTokenServer.class.getName());

    @Resource
    private UserTokenService userTokenService;

    /**
     * 
     * 用户获取token. <br>
     * 客户端通用户id，name，portraitUri获取token
     * 
     * @Copyright itjh
     * @Project
     * @param servletRequest
     * @param servletResponse
     * @return
     * @return String
     * @throws
     * @author 宋立君
     * @date 2015年4月21日 下午4:22:50
     * @Version
     * @JDK version used 8.0
     * @Modification history none
     * @Modified by none
     */
    @GET
    @Path("/getUserToken/{userId}/{name}/{portraitUri}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "获取用户token", notes = "连接融云获取用户token", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of user detail", response = String.class),
            @ApiResponse(code = 404, message = "User with given username does not exist"),
            @ApiResponse(code = 500, message = "Internal server error") })
    public String getUserToken(
            @ApiParam(name = "userId", value = "用户 Id，最大长度 32 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户.", required = true) @PathParam("userId") String userId,
            @ApiParam(name = "name", value = "用户名称，最大长度 128 字节。用来在 Push 推送时显示用户的名称。", required = true) @PathParam("name") String name,
            @ApiParam(name = "portraitUri", value = "用户头像 URI，最大长度 1024 字节。用来在 Push 推送时显示用户的名称", required = true) @PathParam("portraitUri") String portraitUri,
            @Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        
        logger.info("用户id: " + userId + "获取token");
        // 返回参数的map
        Map<String, Object> result = new HashMap<String, Object>();
        String resultJson = "";
        UserToken userToken = new UserToken();
        try {
            if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(portraitUri)) {
                //获取用户token
                userToken = userTokenService.getUserTokenByRedis(userId,name,portraitUri);
                if (null != userToken && null != userToken.getToken()) {
                    result.put("error_code", 0);
                    result.put("description", "用户token获取成功");
                    result.put("content", userToken);
                }else{
                    result.put("error_code", 1001);
                    result.put("description", "用户token获取失败,请重试!");
                }
            }else{
                result.put("error_code", 9998);
                result.put("description", "信息传入不能为空,请重新输入!");
            }
        } catch (Exception e) {
            result.put("error_code", 9999);
            result.put("description", "服务器在开小差,请稍等重试!");
            e.printStackTrace();
        }
        resultJson = GsonUtil.toJson(result);
        return resultJson;
    }
}
