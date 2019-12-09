package web.dao;


import web.entity.WechatUser;



public interface WechatUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    WechatUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);

    WechatUser selectByOpenId(String openId);

    void updateByOpenId(WechatUser user);


    WechatUser selectByToken(String token);
}