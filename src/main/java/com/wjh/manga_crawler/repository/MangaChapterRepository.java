package com.wjh.manga_crawler.repository;

import com.wjh.manga_crawler.entity.MangaChapter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaChapterRepository extends CrudRepository<MangaChapter, Long> {

    MangaChapter findByUrlAndChapter(String url, String chapter);
}
