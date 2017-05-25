package profile;

/**
 * Created by Pablo on 31/3/17.
 */

public class ProfileInteractorImp implements ProfileInteractor {

    private ProfilePresenter profilePresenter;

    public ProfileInteractorImp(ProfilePresenter profilePresenter) {
        this.profilePresenter = profilePresenter;
    }


}
