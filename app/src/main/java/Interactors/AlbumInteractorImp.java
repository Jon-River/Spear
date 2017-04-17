package Interactors;

import Interfaces.AlbumInteractor;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumInteractorImp implements AlbumInteractor {

    Fragments.TabAlbumFragment TabAlbumFragment;

    public AlbumInteractorImp(Fragments.TabAlbumFragment tabAlbumFragment) {
        this.TabAlbumFragment = tabAlbumFragment;
    }
}
