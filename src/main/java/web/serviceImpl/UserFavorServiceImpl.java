package web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserFavorMapper;
import web.entity.UserFavor;
import web.service.UserFavorService;

/**
 * DESCRIPTION
 *
 * @author tym
 * @ceeate 2019/12/12
 **/
@Service
public class UserFavorServiceImpl implements UserFavorService {

    @Autowired
    private UserFavorMapper userFavorMapper;

    @Override
    public void save(UserFavor userFavor) {
        userFavorMapper.insert(userFavor);
    }
}
