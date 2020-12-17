package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.MangaAlternativeName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaAlternativeNameRepository extends CrudRepository<MangaAlternativeName, Long> {

}
