package interactors.single_image;

/**
 * Created by Pablo on 21/5/17.
 */

public interface SingleImageInteractor {

    void pushRatingToFirebase(long timeStamp, float rating);
}
