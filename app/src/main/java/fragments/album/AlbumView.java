package fragments.album;

import android.graphics.Bitmap;

import objects.CardImage;


public interface AlbumView {
    void setImageBitmap(Bitmap imageBitmap);
    void hideLoading();
    void notifyAdapter();

    interface OnImageClick {
        void onSuccess(CardImage card);
        void onError();

    }
}
