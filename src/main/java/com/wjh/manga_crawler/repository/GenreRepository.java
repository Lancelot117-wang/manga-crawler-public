package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre findByGenre(String genre);
}
