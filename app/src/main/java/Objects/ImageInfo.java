package Objects;

/**
 * Created by pablo.rojas on 24/4/17.
 */

public class ImageInfo {

  private String imgPath;
  private int rating;
  private long timeStamp;
  private String comentary;
  private String uri;

  public ImageInfo(String imgPath, int rating, long timeStamp, String comentary, String uri) {
    this.imgPath = imgPath;
    this.rating = rating;
    this.timeStamp = timeStamp;
    this.comentary = comentary;
    this.uri = uri;
  }

  public ImageInfo(String imgPath, int rating, long timeStamp, String comentary) {
    this.imgPath = imgPath;
    this.rating = rating;
    this.timeStamp = timeStamp;
    this.comentary = comentary;
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
