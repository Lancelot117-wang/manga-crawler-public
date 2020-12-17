package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;

@PipelineName("defaultPipeline")
public class HomePagePipeline implements Pipeline<DefaultBean> {

    public void process(DefaultBean defaultBean) {
        for(String url: defaultBean.getAllHref()){
            System.out.println(url);
        }
        HttpRequest request = defaultBean.getRequest();
    }
}
