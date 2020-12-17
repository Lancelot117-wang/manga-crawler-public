package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.Visited;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedRepository extends CrudRepository<Visited, Long> {

    Visited findByUrl(String url);
}
