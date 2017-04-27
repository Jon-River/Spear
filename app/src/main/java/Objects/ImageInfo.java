package Objects;

/**
 * Created by pablo.rojas on 24/4/17.
 */

public class ImageInfo {

  private String imgPath;
  private int rating;
  private int hidden;
  private String comentary;


  public ImageInfo(String imgPath, int rating, int hidden,String comentrary) {
    this.imgPath = imgPath;
    this.rating = rating;
    this.hidden = hidden;
    this.comentary = comentrary;
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
}
