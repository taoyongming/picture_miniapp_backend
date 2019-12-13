package web.dao;

import web.entity.UserFavor;

public interface UserFavorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserFavor record);

    int insertSelective(UserFavor record);

    UserFavor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFavor record);

    int updateByPrimaryKey(UserFavor record);
}