package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.wjh.manga_crawler.entity.*;
import com.wjh.manga_crawler.repository.*;
import com.wjh.manga_crawler.utils.s3connector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service("mangaDetailPipeline")
public class MangaDetailPipeline implements Pipeline<MangaDetailBean> {

    @Autowired
    MangaDetailRepository mangaDetailRepository;

    @Autowired
    MangaAlternativeNameRepository mangaAlternativeNameRepository;

    @Autowired
    MangaAuthorRepository mangaAuthorRepository;

    @Autowired
    MangaGenreRepository mangaGenreRepository;

    @Autowired
    MangaChapterRepository mangaChapterRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    VisitedRepository visitedRepository;

    public void moveImage(String mangaId, String imageUrl) {
        try {
            String fromPath="src/mangaCover/";
            String toPath="src/manga/"+mangaId+"/";
            String fileName;
            fileName = StringUtils.substringBefore(imageUrl, "?");
            fileName = StringUtils.substringAfterLast(fileName, "/");
            File startFile = new File(fromPath + fileName);
            File tmpFile = new File(toPath);//get Dir path
            if (!tmpFile.exists()) {//new Dir
                tmpFile.mkdirs();
            }
            //Files.copy(startFile, new File(toPath + fileName));
            //System.out.println("Move Image Completed");
            if (startFile.renameTo(new File(toPath + fileName))) {
                System.out.println("Move Image Success");
            } else {
                System.out.println("Move Image Fail");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getFileName(String URL){
        String fileName;
        fileName = StringUtils.substringBefore(URL, "?");
        fileName = StringUtils.substringAfterLast(fileName, "/");
        return fileName;
    }

    @Transactional
    public void process(MangaDetailBean mangaDetailBean) throws RuntimeException {
        HttpRequest request = mangaDetailBean.getRequest();
        System.out.println(request.getUrl() + " has been downloaded, start processing.");

        Detail detail = new Detail();

        Visited visited = visitedRepository.findByUrl(mangaDetailBean.getId());
        if(visited != null){
            Detail fetchedDetail = mangaDetailRepository.findByUrl(mangaDetailBean.getId());
            if(fetchedDetail != null){
                detail.setId(fetchedDetail.getId());
                if (fetchedDetail.getChapterCount() != mangaDetailBean.getAllChapter().size()) {
                    detail.setUpdatedTime(new Date());
                    detail.setChapterCount(mangaDetailBean.getAllChapter().size());
                } else {
                    detail.setUpdatedTime(fetchedDetail.getUpdatedTime());
                    detail.setChapterCount(fetchedDetail.getChapterCount());
                }
                detail.setViewCount(fetchedDetail.getViewCount());
                detail.setRating(fetchedDetail.getRating());

                detail.setUrl(fetchedDetail.getUrl());
                detail.setMangaName(fetchedDetail.getMangaName());
                detail.setMangaCoverFileName(fetchedDetail.getMangaCoverFileName());
                detail.setDescription(fetchedDetail.getDescription());

                if(mangaDetailBean.getMangaDetailFirstField().contains("Alternative")) {
                    detail.setStatus(mangaDetailBean.getStatus2());
                } else {
                    detail.setStatus(mangaDetailBean.getStatus1());
                }
                detail.setMangaAlternativeCount(fetchedDetail.getMangaAlternativeCount());
            } else {
                throw new RuntimeException("Page had been visited but detail file was not created.");
            }
        } else {
            String fileName = null;
            for (String path : mangaDetailBean.getMangaCover()) {
                fileName = getFileName(path);
                //moveImage(mangaDetailBean.getId(), path);
                s3connector.uploadFile("src/mangaCover/" + fileName, mangaDetailBean.getId()+"/");
            }
            detail.setUrl(mangaDetailBean.getId());
            detail.setMangaName(mangaDetailBean.getMangaName());
            detail.setMangaCoverFileName(fileName);
            detail.setDescription(mangaDetailBean.getDescription());

            if (mangaDetailBean.getMangaAlternativeName() != null) {
                String[] alternativeNames = mangaDetailBean.getMangaAlternativeName().split(";");
                detail.setMangaAlternativeCount(alternativeNames.length);
                for (String alternativeName : alternativeNames) {
                    MangaAlternativeName mangaAlternativeName = new MangaAlternativeName();
                    mangaAlternativeName.setUrl(mangaDetailBean.getId());
                    mangaAlternativeName.setAlternativeName(alternativeName);
                    mangaAlternativeNameRepository.save(mangaAlternativeName);
                }
            } else {
                detail.setMangaAlternativeCount(0);
            }

            if (mangaDetailBean.getMangaDetailFirstField().contains("Alternative")) {
                //System.out.println("Authors:"+mangaDetailBean.getAuthors2());
                for (String author : mangaDetailBean.getAuthors2()) {
                    MangaAuthor mangaAuthor = new MangaAuthor();
                    mangaAuthor.setUrl(mangaDetailBean.getId());
                    mangaAuthor.setAuthor(author);
                    mangaAuthorRepository.save(mangaAuthor);

                    if(authorRepository.findByAuthor(author) == null){
                        Author newAuthor = new Author();
                        newAuthor.setAuthor(author);
                        authorRepository.save(newAuthor);
                    }
                }

                //System.out.println("Genres:"+mangaDetailBean.getGenres2());
                for (String genre : mangaDetailBean.getGenres2()) {
                    MangaGenre mangaGenre = new MangaGenre();
                    mangaGenre.setUrl(mangaDetailBean.getId());
                    mangaGenre.setGenre(genre);
                    mangaGenreRepository.save(mangaGenre);

                    if(genreRepository.findByGenre(genre) == null){
                        Genre newGenre = new Genre();
                        newGenre.setGenre(genre);
                        genreRepository.save(newGenre);
                    }
                }

                //System.out.println("Status:"+mangaDetailBean.getStatus2());
                detail.setStatus(mangaDetailBean.getStatus2());
            } else {
                //System.out.println("Authors:"+mangaDetailBean.getAuthors1());
                for (String author : mangaDetailBean.getAuthors1()) {
                    MangaAuthor mangaAuthor = new MangaAuthor();
                    mangaAuthor.setUrl(mangaDetailBean.getId());
                    mangaAuthor.setAuthor(author);
                    mangaAuthorRepository.save(mangaAuthor);

                    if(authorRepository.findByAuthor(author) == null){
                        Author newAuthor = new Author();
                        newAuthor.setAuthor(author);
                        authorRepository.save(newAuthor);
                    }
                }

                //System.out.println("Genres:"+mangaDetailBean.getGenres1());
                for (String genre : mangaDetailBean.getGenres1()) {
                    MangaGenre mangaGenre = new MangaGenre();
                    mangaGenre.setUrl(mangaDetailBean.getId());
                    mangaGenre.setGenre(genre);
                    mangaGenreRepository.save(mangaGenre);

                    if(genreRepository.findByGenre(genre) == null){
                        Genre newGenre = new Genre();
                        newGenre.setGenre(genre);
                        genreRepository.save(newGenre);
                    }
                }

                //System.out.println("Status:"+mangaDetailBean.getStatus1());
                detail.setStatus(mangaDetailBean.getStatus1());
            }

            detail.setUpdatedTime(new Date());
            detail.setChapterCount(mangaDetailBean.getAllChapter().size());
            detail.setViewCount(0L);
            detail.setRating(5.0f);

            mangaDetailRepository.save(detail);

            visited = new Visited();
            visited.setUrl(mangaDetailBean.getId());
            visitedRepository.save(visited);
        }

        for(String chapter : mangaDetailBean.getAllChapter()){
            if(mangaChapterRepository.findByUrlAndChapter(mangaDetailBean.getId(), chapter) == null){
                MangaChapter mangaChapter = new MangaChapter();
                mangaChapter.setUrl(mangaDetailBean.getId());
                mangaChapter.setChapter(chapter);
                mangaChapterRepository.save(mangaChapter);
            }
        }

        for(String chapterHref : mangaDetailBean.getAllChapterHref()){
            if(visitedRepository.findByUrl(StringUtils.substringAfterLast(chapterHref,"chapter/")) == null) {
                //System.out.println(StringUtils.substringAfterLast(chapterHref,"chapter/"));
                SchedulerContext.into(request.subRequest(chapterHref));
            }
        }

        System.out.println(request.getUrl() + " has been processed.");
    }
}
