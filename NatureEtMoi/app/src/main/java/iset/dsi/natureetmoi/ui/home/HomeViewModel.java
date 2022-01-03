package iset.dsi.natureetmoi.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import iset.dsi.natureetmoi.FlowerAdapter;
import iset.dsi.natureetmoi.FlowerClass;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;






    public HomeViewModel() {









    }

    public LiveData<String> getText() {
        return mText;
    }




}