package com.so.sbmytatis.mapper;


import com.so.sbmytatis.entity.Movie;
import org.apache.ibatis.annotations.*;

public interface MovieMapper {
    @Select("select id,movie_name,boxoffice from movie_info where id = #{id}")
    @Results({
            @Result(property = "movieName", column = "movie_name")
    })
    Movie selectMovieById(long id);


    @InsertProvider(type = MovieProvider.class,method = "insertMovie")
    void insertMovie(@Param("movie") Movie movie);

}
