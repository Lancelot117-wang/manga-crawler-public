package com.wjh.manga_crawler.entity;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private String mangaName;

    @Column
    private String mangaCoverFileName;

    @Column
    private int mangaAlternativeCount;

    @Column
    private String status;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column
    private String description;

    @Column
    private Date updatedTime;

    @Column
    private Long viewCount;

    @Column
    private double rating;

    @Column
    private int chapterCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public String getMangaCoverFileName() {
        return mangaCoverFileName;
    }

    public void setMangaCoverFileName(String mangaCoverFileName) {
        this.mangaCoverFileName = mangaCoverFileName;
    }

    public int getMangaAlternativeCount() {
        return mangaAlternativeCount;
    }

    public void setMangaAlternativeCount(int mangaAlternativeCount) {
        this.mangaAlternativeCount = mangaAlternativeCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "url='" + url + '\'' +
                ", mangaName='" + mangaName + '\'' +
                ", mangaCoverFileName='" + mangaCoverFileName + '\'' +
                ", mangaAlternativeName='" + mangaAlternativeCount + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", updatedTime=" + updatedTime +
                ", viewCount=" + viewCount +
                ", rating=" + rating +
                ", chapterCount=" + chapterCount +
                '}';
    }
}
