package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.Detail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaDetailRepository extends CrudRepository<Detail, Long> {

    Detail findByUrl(String url);
}
