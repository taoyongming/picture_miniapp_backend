package web.filter;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.LoggerFactory;
import web.util.Constant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;



public class KickoutSessionControlFilter extends AccessControlFilter{
    org.slf4j.Logger logger = LoggerFactory.getLogger("KickoutSessionControlFilter");

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }


    /**
     ** 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了,不会再继续执行拦截器链。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        logger.debug("sessionId:" +subject.getSession().getId().toString());
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            this.responseError(response,"未登录", Constant.ERROR_CODE_loginError);
            return true;
        }
        String token = getRequestToken((HttpServletRequest) request);
        if(token == null) {
            this.responseError(response,"没有token",Constant.ERROR_CODE_loginError);
            return false;
        }else {
          String  session_token =   subject.getSession().getId().toString();
          if(!token.equals(session_token)) {
              this.responseError(response,"token验证错误！",Constant.ERROR_CODE_loginError);
          }
        }

        return true;
    }

    private String getRequestToken(HttpServletRequest request){
        //默认从请求头中获得token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }
    /**
     * 请求异常则需要重新登录
     */
    private void responseError(ServletResponse response, String message,String errorCode) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        map.put("errorCode",errorCode);
        String jsonString = JSONObject.toJSONString(map);

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(jsonString.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
