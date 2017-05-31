package com.spear.android.pojo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pablo.rojas on 24/4/17.
 */
@IgnoreExtraProperties
public class ImageInfo {

  private String usermail;
  private float rating;
  private int voted;
  private long timeStamp;
  private String comentary;
  private String url;
  private String province;
  private String name;


  public ImageInfo() {

  }

  public ImageInfo(String usermail, float rating, long timeStamp, String comentary, String url, int voted, String province, String name) {
    this.usermail = usermail;
    this.rating = rating;
    this.timeStamp = timeStamp;
    this.comentary = comentary;
    this.url = url;
    this.voted = voted;
    this.province = province;
    this.name = name;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getVoted() {
    return voted;
  }

  public void setVoted(int voted) {
    this.voted = voted;
  }

  public String getComentary() {
    return comentary;
  }

  public void setComentary(String comentary) {
    this.comentary = comentary;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getUsermail() {
    return usermail;
  }

  public void setUsermail(String usermail) {
    this.usermail = usermail;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
