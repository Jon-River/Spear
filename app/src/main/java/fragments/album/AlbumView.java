package fragments.album;

import android.graphics.Bitmap;


public interface AlbumView {
    void setImageBitmap(Bitmap imageBitmap);
    void hideLoading();
    void notifyAdapter();
}
