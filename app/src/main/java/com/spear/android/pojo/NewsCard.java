package com.spear.android.pojo;

/**
 * Created by Pablo on 10/6/17.
 */

public class NewsCard {

    String linkurl, imageurl, tittle, subtitule, description;

    public NewsCard(String linkurl, String imageurl, String tittle, String subtitule, String description) {
        this.linkurl = linkurl;
        this.imageurl = imageurl;
        this.tittle = tittle;
        this.subtitule = subtitule;
        this.description = description;
    }

    public NewsCard() {

    }


    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getSubtitule() {
        return subtitule;
    }

    public void setSubtitule(String subtitule) {
        this.subtitule = subtitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
