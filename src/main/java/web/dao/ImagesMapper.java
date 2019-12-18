package web.dao;

import web.entity.Images;


public interface ImagesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}