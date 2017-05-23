package fragments.album;

import android.graphics.Bitmap;

import pojo.CardImage;


public interface AlbumView {
    void setImageBitmap(Bitmap imageBitmap);
    void hideLoading();
    void notifyAdapter();

    interface OnImageClick {
        void onSuccess(CardImage card);
        void onError();

    }
}
