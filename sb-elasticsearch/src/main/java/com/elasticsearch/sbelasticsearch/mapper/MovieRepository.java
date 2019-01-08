package com.elasticsearch.sbelasticsearch.mapper;

import com.elasticsearch.sbelasticsearch.entity.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface MovieRepository extends ElasticsearchRepository<Movie,Long> {
    Movie queryMovieById(long id);
}
