package com.wjh.manga_crawler;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.wjh.manga_crawler.entity.ImagePath;
import com.wjh.manga_crawler.entity.Visited;
import com.wjh.manga_crawler.repository.ImagePathRepository;
import com.wjh.manga_crawler.repository.VisitedRepository;
import com.wjh.manga_crawler.utils.s3connector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("mangaImagePipeline")
public class MangaImagePipeline implements Pipeline<MangaImageBean> {

    @Autowired
    VisitedRepository visitedRepository;

    @Autowired
    ImagePathRepository imagePathRepository;

    public void moveImage(String mangaId, String mangaChapter, String imageUrl) {
        try {
            String fromPath="src/manga/";
            String toPath="src/manga/"+mangaId+"/"+mangaChapter+"/";
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
                //System.out.println("Move Image Success");
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

    public void process(MangaImageBean mangaImageBean) {
        HttpRequest request = mangaImageBean.getRequest();
        System.out.println(request.getUrl() + " has been downloaded, start processing.");

        String fullPath = mangaImageBean.getId() + "/" + mangaImageBean.getChapter();
        if(visitedRepository.findByUrl(fullPath) == null){
            int index = 0;
            for (String imageURL : mangaImageBean.getAllImageURL()) {
                String fileName = getFileName(imageURL);
                //moveImage(mangaImageBean.getId(), mangaImageBean.getChapter(), imageURL);
                s3connector.uploadFile("src/manga/" + fileName, mangaImageBean.getId() + "/"
                        + mangaImageBean.getChapter() + "/");
                ImagePath imagePath = new ImagePath();
                imagePath.setUrl(mangaImageBean.getId());
                imagePath.setChapter(mangaImageBean.getChapter());
                imagePath.setImageIndex(index);
                imagePath.setFileName(getFileName(imageURL));
                imagePathRepository.save(imagePath);

                index++;
            }

            Visited visited = new Visited();
            visited.setUrl(fullPath);
            visitedRepository.save(visited);
        }

        System.out.println(request.getUrl() + " has been processed.");
    }
}