package web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.WechatUserMapper;
import web.entity.WechatUser;
import web.service.WechatUserService;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/14
 **/
@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserMapper  wechatUserMapper;

    @Override
    public WechatUser getByOpenId(String openId) {
        return wechatUserMapper.selectByOpenId(openId);
    }

    @Override
    public void save(WechatUser wechatUser) {
        wechatUserMapper.insertSelective(wechatUser);
    }

    @Override
    public void updateByOpenId(WechatUser user) {
        wechatUserMapper.updateByOpenId(user);
    }

    @Override
    public WechatUser getByToken(String token) {
        return wechatUserMapper.selectByToken(token);
    }
}
