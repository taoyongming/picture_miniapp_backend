package web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import web.entity.WechatUser;

import java.util.Date;

/**
 * DESCRIPTION
 *
 * @author tym
 * @ceeate 2019/12/12
 **/
public abstract  class TokenUtil {
    protected String getToken(WechatUser user) {
        String token="";
        token= JWT.create()
                .withKeyId(user.getOpenId())
                .withIssuer("www.taoyongming.com")
                .withIssuedAt(new Date())
                .withJWTId("www.taoyongming.com")
                .withClaim("session_key", user.getSessionKey())
                .withAudience(user.getOpenId())
                .sign(Algorithm.HMAC256(user.getOpenId()));
        return token;
    }

}
