package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl = "https://manganelo.com/chapter/{id}/{chapter}",pipelines = "mangaImagePipeline")
public class MangaImageBean implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter(value = "id")
    private String id;

    @RequestParameter(value = "chapter")
    private String chapter;

    @Image(download = "src/manga/")
    @HtmlField(cssPath = "body > div.body-site > div.container-chapter-reader > img:nth-child(n)")
    private List<String> allImageURL;

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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public List<String> getAllImageURL() {
        return allImageURL;
    }

    public void setAllImageURL(List<String> allImageURL) {
        this.allImageURL = allImageURL;
    }
}