package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl = {"https://manganelo.com/genre-all", "https://manganelo.com/genre-all/{index}"},pipelines = "mangaListPipeline")
public class MangaListBean implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter
    private int index = -1;

    @Text
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.panel-page-number > " +
            "div.group-page > a.page-blue.page-last")
    private String maxIndex;

    @Href
    @HtmlField(cssPath = "body > div.body-site > div.container.container-main > div.panel-content-genres > " +
            "div:nth-child(n) > div > h3 > a")
    private List<String> allMangaHref;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(String maxIndex) {
        this.maxIndex = maxIndex;
    }

    public List<String> getAllMangaHref() {
        return allMangaHref;
    }

    public void setAllMangaHref(List<String> allMangaHref) {
        this.allMangaHref = allMangaHref;
    }
}
