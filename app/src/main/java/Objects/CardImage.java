package Objects;

/**
 * Created by Pablo on 10/4/17.
 */

public class CardImage {

    private String username;
    private int rating;
    private int imageresource;
    private String province;



    public CardImage(String username, int rating, int imageresource, String province ) {
        this.username = username;
        this.rating = rating;
        this.imageresource = imageresource;
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
    public int getImageresource() {
        return imageresource;
    }

    public void setImageresource(int imageresource) {
        this.imageresource = imageresource;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
