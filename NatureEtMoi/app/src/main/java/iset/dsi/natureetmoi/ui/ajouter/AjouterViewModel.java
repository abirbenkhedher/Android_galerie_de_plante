package iset.dsi.natureetmoi.ui.ajouter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AjouterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AjouterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ajouter fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}