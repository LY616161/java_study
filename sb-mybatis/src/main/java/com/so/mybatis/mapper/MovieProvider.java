package com.so.mybatis.mapper;

import com.so.mybatis.entity.Movie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class MovieProvider {
    public String insertMovie(@Param("movie") Movie movie){
        return new SQL(){
            {
                INSERT_INTO("movie_info");
                if (movie.getId() != -1) {
                    VALUES("id", "#{movie.id}");
                }
                if (movie.getMovieName() != null) {
                    VALUES("movie_name", "#{movie.movieName}");
                }
                if (movie.getBoxoffice() != -1) {
                    VALUES("boxoffice", "#{movie.boxoffice}");
                }
            }
        }.toString();
    }
}
