package fragments.profile;

import interactors.profile.ProfileInteractor;
import interactors.profile.ProfileInteractorImp;

/**
 * Created by Pablo on 13/5/17.
 */

public class ProfilePresenter {

    private ProfileView view;
    private ProfileInteractor profileInteractor;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
        profileInteractor = new ProfileInteractorImp(this);
    }


}
