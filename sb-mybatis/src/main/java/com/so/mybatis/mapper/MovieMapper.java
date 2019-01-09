package com.so.mybatis.mapper;


import com.so.mybatis.commom.Enum.MovieTypeHandler;
import com.so.mybatis.entity.Movie;
import org.apache.ibatis.annotations.*;

public interface MovieMapper {
    @Select("select id,movie_name,boxoffice,movie_type from movie_info where id = #{id}")
    @Results({
            @Result(property = "movieName", column = "movie_name"),
            @Result(property = "type", column = "movie_type", typeHandler = MovieTypeHandler.class)
    })
    Movie selectMovieById(long id);


    @InsertProvider(type = MovieProvider.class,method = "insertMovie")
    void insertMovie(@Param("movie") Movie movie);

}
