package com.so.movie.mapper;

import com.so.movie.common.utils.StringUtils;
import com.so.movie.entity.Movie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

public class MovieProvider {
    public String insertMovieSQL(@Param("movie") Movie movie){
        return new SQL(){
            {
                INSERT_INTO("movie_info");
                VALUES("name","#{movie.name}");
                VALUES("director_id","#{movie.directorId}");
                VALUES("release_date","#{movie.releaseDate}");
                VALUES("create_time","now()");
            }
        }.toString();
    }

    public String updateMovieSQL(@Param("movie") Movie movie){
        return new SQL(){
            {
                UPDATE("movie_info");
                if (!StringUtils.isEmpty(movie.getName()))
                    SET("name = #{movie.name}");
                if (movie.getDirectorId()!=-1)
                    SET("director_id = #{movie.directorId}");
                if (!movie.getReleaseDate().equals(null))
                    SET("release_date = #{movie.releaseDate}");
                SET("update_time = now()");
                WHERE("id = #{movie.id}");
            }
        }.toString();
    }
}
