package cn.syl.mapper;

import cn.syl.pojo.TCategory;
import cn.syl.pojo.TCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCategoryMapper {
    int countByExample(TCategoryExample example);

    int deleteByExample(TCategoryExample example);

    int deleteByPrimaryKey(Long categoryId);

    int insert(TCategory record);

    int insertSelective(TCategory record);

    List<TCategory> selectByExample(TCategoryExample example);

    TCategory selectByPrimaryKey(Long categoryId);

    int updateByExampleSelective(@Param("record") TCategory record, @Param("example") TCategoryExample example);

    int updateByExample(@Param("record") TCategory record, @Param("example") TCategoryExample example);

    int updateByPrimaryKeySelective(TCategory record);

    int updateByPrimaryKey(TCategory record);
}