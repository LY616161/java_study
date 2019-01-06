package com.so.mybatisMulti.mapper.primary;

import com.so.mybatisMulti.entity.primary.Movie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
    Movie selectMovieById(long id);
}
