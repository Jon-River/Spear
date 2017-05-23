package interactors.single_image;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.activities.SingleImage.SingleImageView;

import java.util.HashMap;
import java.util.Map;

import pojo.ImageInfo;

/**
 * Created by Pablo on 21/5/17.
 */

public class SingleImageInteractorImp implements SingleImageInteractor {

    SingleImageView.OnPushRatingToFirebase onPushRating;
    private DatabaseReference databaseReference;

    public SingleImageInteractorImp(SingleImageView.OnPushRatingToFirebase onPushRating) {
        this.onPushRating = onPushRating;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override public void pushRatingToFirebase(final long timeStamp, final float rating) {
        //Toast.makeText(getContext(), "" + timeStamp, Toast.LENGTH_SHORT).show();
        databaseReference.getRoot().child("images").child(String.valueOf(timeStamp)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageInfo image = dataSnapshot.getValue(ImageInfo.class);
                //long time = image.getTimeStamp();
                float currentRating = image.getRating();
                int votes = image.getVoted();
                votes= votes+1;
                currentRating =currentRating + rating;
                image.setRating(currentRating);
                image.setVoted(votes);
                Map<String, Object> map = new HashMap<>();
                map.put(String.valueOf(timeStamp),image);
                databaseReference.child("images").updateChildren(map);
                float mediumRating =currentRating/(float)votes;
                onPushRating.OnSucces(mediumRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
