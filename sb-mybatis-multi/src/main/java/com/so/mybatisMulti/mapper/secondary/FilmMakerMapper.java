package com.so.mybatisMulti.mapper.secondary;

import com.so.mybatisMulti.entity.secondary.FilmMaker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilmMakerMapper {
    FilmMaker selectMakerById(long id);
}
