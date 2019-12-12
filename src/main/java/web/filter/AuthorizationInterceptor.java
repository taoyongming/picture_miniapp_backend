package web.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import web.entity.WechatUser;
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

    //拦截器：请求之前preHandle
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        // 执行认证
        if (token == null) {
            throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 openId
        String openId;
        try {
            // 获取 openId
            openId = JWT.decode(token).getKeyId();
            // 添加request参数，用于传递userid
            httpServletRequest.setAttribute("currentUser", openId);
            // 根据userId 查询用户信息
            WechatUser user = userService.getByOpenId(openId);
            if (user == null) {
                throw new RRException("用户不存在，请重新登录");
            }

            try {
                String session_key = JWT.decode(token).getClaim("session_key").as(String.class);
                // 添加request参数，用于传递sessionKey
                httpServletRequest.setAttribute("sessionKey", session_key);
            } catch (Exception e) {
            }

            // 验证 密码
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getOpenId())).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("401");
            }
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }

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
