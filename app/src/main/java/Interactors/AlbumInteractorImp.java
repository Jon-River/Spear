package Interactors;

import Fragments.TabAlbumFragment;
import Interfaces.AlbumInteractor;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumInteractorImp implements AlbumInteractor {

    TabAlbumFragment TabAlbumFragment;

    public AlbumInteractorImp(TabAlbumFragment tabAlbumFragment) {
        this.TabAlbumFragment = tabAlbumFragment;
    }
}
