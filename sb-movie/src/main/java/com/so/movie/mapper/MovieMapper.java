package com.so.movie.mapper;

import com.so.movie.entity.Movie;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MovieMapper {
    @Select("select id,name,director_id from movie_info where id = #{id}")
    Movie queryMovieById(int id);

    @InsertProvider(type = MovieProvider.class,method = "insertMovieSQL")
    void insertMovie(@Param("movie") Movie movie);

    @UpdateProvider(type = MovieProvider.class,method = "updateMovieSQL")
    void updateMovie(@Param("movie") Movie movie);
}
