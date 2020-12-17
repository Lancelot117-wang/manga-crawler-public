package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl = "www.baidu.com", pipelines = "defaultPipeline")
public class DefaultBean implements HtmlBean {

    @Request
    private HttpRequest request;

    @Href
    @HtmlField(cssPath = "")
    private List<String> allHref;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public List<String> getAllHref() {
        return allHref;
    }

    public void setAllHref(List<String> allHref) {
        this.allHref = allHref;
    }
}
