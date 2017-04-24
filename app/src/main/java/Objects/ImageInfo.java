package Objects;

/**
 * Created by pablo.rojas on 24/4/17.
 */

public class ImageInfo {

  private String imgPath;
  private int rating;

  public ImageInfo(String imgPath, int rating) {
    this.imgPath = imgPath;
    this.rating = rating;
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
}
