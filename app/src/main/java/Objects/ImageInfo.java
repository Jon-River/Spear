package Objects;

/**
 * Created by pablo.rojas on 24/4/17.
 */

public class ImageInfo {

  private String imgPath;
  private int rating;
  private int hidden;
  private String comentary;
  private String uri;

  public ImageInfo(String imgPath, int rating, int hidden, String comentary, String uri) {
    this.imgPath = imgPath;
    this.rating = rating;
    this.hidden = hidden;
    this.comentary = comentary;
    this.uri = uri;
  }

  public ImageInfo(String imgPath, int rating, int hidden, String comentary) {
    this.imgPath = imgPath;
    this.rating = rating;
    this.hidden = hidden;
    this.comentary = comentary;
  }

  public String getComentary() {
    return comentary;
  }

  public void setComentary(String comentary) {
    this.comentary = comentary;
  }

  public int getHidden() {
    return hidden;
  }

  public void setHidden(int hidden) {
    this.hidden = hidden;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
