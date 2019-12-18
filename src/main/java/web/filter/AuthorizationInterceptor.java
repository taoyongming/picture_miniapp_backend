package web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import web.entity.WechatUser;
import web.redis.RedisUtil;
import web.service.WechatUserService;
import web.util.RRException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 获取token并验证token
 **/
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    WechatUserService userService;

    @Autowired
    RedisUtil redisUtil;
    //拦截器：请求之前preHandle
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

//        // 执行认证
//        if (token == null) {
//            throw new RuntimeException("无token，请重新登录");
//        }
//            // 获取 openId
//            String  openId = redisUtil.hget("token",token) == null ? "" : redisUtil.hget("token",token).toString();
//        if (openId.equals("")) {
//            throw new RRException("用户不存在，请重新登录");
//        }
//            // 添加request参数，用于传递userid
//            httpServletRequest.getSession().setAttribute("openid",openId);
//            // 根据userId 查询用户信息
//            WechatUser user = userService.getByOpenId(openId);
//            if (user == null) {
//                throw new RRException("用户不存在，请重新登录");
//            }

        return true;

    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    //拦截器：请求之后：afterCompletion
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
