package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("mangaListPipeline")
public class MangaListPipeline implements Pipeline<MangaListBean> {

    public void process(MangaListBean mangaListBean) {
        HttpRequest request = mangaListBean.getRequest();
        System.out.println(request.getUrl() + " has been downloaded, start processing.");

        String last = mangaListBean.getMaxIndex();
        last = StringUtils.substringAfterLast(last, "(");
        last = StringUtils.substringBefore(last, ")");
        int maxIndex = Integer.parseInt(last);

        int newIndex = 2;
        if(mangaListBean.getIndex() != -1){
            newIndex = mangaListBean.getIndex() + 1;
        }

        if(newIndex - 1 > maxIndex){
            return;
        }

        for(String url: mangaListBean.getAllMangaHref()) {
            SchedulerContext.into(request.subRequest(url));
        }

        if(newIndex > maxIndex){
            return;
        }
        String nextUrl = "https://manganelo.com/genre-all/"+newIndex;
        SchedulerContext.into(request.subRequest(nextUrl));

        System.out.println(request.getUrl() + " has been processed.");
    }
}
