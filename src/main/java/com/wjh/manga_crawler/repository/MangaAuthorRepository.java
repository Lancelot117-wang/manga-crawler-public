package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.MangaAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaAuthorRepository extends CrudRepository<MangaAuthor, Long> {

}
