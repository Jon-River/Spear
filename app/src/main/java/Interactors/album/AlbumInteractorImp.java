package Interactors.album;

import Fragments.album.AlbumFragment;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumInteractorImp implements AlbumInteractor {

    AlbumFragment AlbumFragment;

    public AlbumInteractorImp(AlbumFragment albumFragment) {
        this.AlbumFragment = albumFragment;
    }
}
