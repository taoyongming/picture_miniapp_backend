package web.service;


import web.entity.WechatUser;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/14
 **/
public interface WechatUserService {
    WechatUser getByOpenId(String openId);

    void save(WechatUser wechatUser);

    void updateByOpenId(WechatUser user);

    WechatUser getByToken(String token);
}
