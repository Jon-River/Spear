package interactors.profile;

import fragments.ProfileFragment;

/**
 * Created by Pablo on 31/3/17.
 */

public class ProfileInteractorImp implements ProfileInteractor {

    private ProfileFragment profileFragment;

    public ProfileInteractorImp(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }


}
