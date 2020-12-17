package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author findByAuthor(String author);
}
