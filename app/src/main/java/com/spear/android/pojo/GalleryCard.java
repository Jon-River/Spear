package com.spear.android.pojo;

import java.io.Serializable;

/**
 * Created by Pablo on 10/4/17.
 */

public class GalleryCard implements Serializable {

  private String username;
  private float rating;
  private String urlString;
  private String province;
  private int imgRes;
  private long timeStamp;
  private int votes;


  public GalleryCard(String username, float rating, String urlString, String province, long timeStamp, int votes) {
    this.username = username;
    this.rating = rating;
    this.urlString = urlString;
    this.province = province;
    this.timeStamp =  timeStamp;
    this.votes = votes;

  }
  public GalleryCard(String username, float rating, int imgres, String province) {
    this.username = username;
    this.rating = rating;
    this.imgRes = imgres;
    this.province = province;

  }




  public GalleryCard() {

  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getUrlString() {
    return urlString;
  }

  public void setUrlString(String urlString) {
    this.urlString = urlString;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public int getImgRes() {
    return imgRes;
  }

  public void setImgRes(int imgRes) {
    this.imgRes = imgRes;
  }
}
