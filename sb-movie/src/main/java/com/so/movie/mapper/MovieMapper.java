package com.so.movie.mapper;

import com.so.movie.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MovieMapper {
    @Select("select id,name,director_id from movie_info where id = #{id}")
    Movie queryMovieById(int id);
}
