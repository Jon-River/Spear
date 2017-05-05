package Objects;

/**
 * Created by Pablo on 10/4/17.
 */

public class CardImage {

  private String username;
  private int rating;
  private String urlString;
  private String province;
  private int imgRes;


  public CardImage(String username, int rating, String urlString, String province ) {
    this.username = username;
    this.rating = rating;
    this.urlString = urlString;
    this.province = province;

  }
  public CardImage(String username, int rating, int imgres, String province ) {
    this.username = username;
    this.rating = rating;
    this.imgRes = imgres;
    this.province = province;

  }




  public CardImage() {

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
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
