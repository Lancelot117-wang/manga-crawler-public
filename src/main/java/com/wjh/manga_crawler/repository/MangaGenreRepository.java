package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.MangaGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaGenreRepository extends CrudRepository<MangaGenre, Long> {
}
