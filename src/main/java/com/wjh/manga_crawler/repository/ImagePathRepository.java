package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.ImagePath;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePathRepository extends CrudRepository<ImagePath, Long> {
}
