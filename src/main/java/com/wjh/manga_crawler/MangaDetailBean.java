package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl = "https://manganelo.com/manga/{id}", pipelines = "mangaDetailPipeline")
public class MangaDetailBean implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter
    private String id;

    @Image(download = "src/mangaCover/")
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-left > span.info-image > img")
    private List<String> mangaCover;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > h1")
    private String mangaName;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(1) > td.table-label")
    private String mangaDetailFirstField;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(1) > td.table-value > h2")
    private String mangaAlternativeName;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(1) > td.table-value > a:nth-child(n)")
    private List<String> authors1;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(2) > td.table-value > a:nth-child(n)")
    private List<String> authors2;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(2) > td.table-value")
    private String status1;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(3) > td.table-value")
    private String status2;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(3) > td.table-value > a:nth-child(n)")
    private List<String> genres1;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-info > div.story-info-right > table > tbody > tr:nth-child(4) > td.table-value > a:nth-child(n)")
    private List<String> genres2;

    @Text
    @HtmlField(cssPath = "#panel-story-info-description")
    private String description;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-chapter-list > ul > li:nth-child(n) > a")
    private List<String> allChapter;

    @Href
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.container-main-left > " +
            "div.panel-story-chapter-list > ul > li:nth-child(n) > a")
    private List<String> allChapterHref;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMangaCover() {
        return mangaCover;
    }

    public void setMangaCover(List<String> mangaCover) {
        this.mangaCover = mangaCover;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public String getMangaDetailFirstField() {
        return mangaDetailFirstField;
    }

    public void setMangaDetailFirstField(String mangaDetailFirstField) {
        this.mangaDetailFirstField = mangaDetailFirstField;
    }

    public String getMangaAlternativeName() {
        return mangaAlternativeName;
    }

    public void setMangaAlternativeName(String mangaAlternativeName) {
        this.mangaAlternativeName = mangaAlternativeName;
    }

    public List<String> getAuthors1() {
        return authors1;
    }

    public void setAuthors1(List<String> authors1) {
        this.authors1 = authors1;
    }

    public List<String> getAuthors2() {
        return authors2;
    }

    public void setAuthors2(List<String> authors2) {
        this.authors2 = authors2;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public List<String> getGenres1() {
        return genres1;
    }

    public void setGenres1(List<String> genres1) {
        this.genres1 = genres1;
    }

    public List<String> getGenres2() {
        return genres2;
    }

    public void setGenres2(List<String> genres2) {
        this.genres2 = genres2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAllChapter() {
        return allChapter;
    }

    public void setAllChapter(List<String> allChapter) {
        this.allChapter = allChapter;
    }

    public List<String> getAllChapterHref() {
        return allChapterHref;
    }

    public void setAllChapterHref(List<String> allChapterHref) {
        this.allChapterHref = allChapterHref;
    }

    @Override
    public String toString() {
        return "MangaDetailBean{" +
                ", id='" + id + '\'' +
                ", mangaCover=" + mangaCover +
                ", mangaName='" + mangaName + '\'' +
                ", mangaDetailFirstField='" + mangaDetailFirstField + '\'' +
                ", mangaAlternativeName='" + mangaAlternativeName + '\'' +
                ", authors1=" + authors1 +
                ", authors2=" + authors2 +
                ", status1='" + status1 + '\'' +
                ", status2='" + status2 + '\'' +
                ", genres1=" + genres1 +
                ", genres2=" + genres2 +
                ", description='" + description + '\'' +
                ", allChapter=" + allChapter +
                ", allChapterHref=" + allChapterHref +
                '}';
    }
}
