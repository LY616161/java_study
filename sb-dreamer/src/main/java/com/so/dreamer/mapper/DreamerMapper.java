package com.so.dreamer.mapper;

import com.so.dreamer.entity.Dreamer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface DreamerMapper {
    @Select("select id,name from director_info where id = #{id}")
    Dreamer queryDreamerById(int id);
}
