package iset.dsi.natureetmoi.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavArgument;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import iset.dsi.natureetmoi.FlowerAdapter;
import iset.dsi.natureetmoi.FlowerClass;
import iset.dsi.natureetmoi.R;
import iset.dsi.natureetmoi.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    DatabaseReference database;
    FlowerAdapter flowerAdapter;
    ArrayList<FlowerClass> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.flowerList);
        database= FirebaseDatabase.getInstance().getReference("flowers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        list= new ArrayList<>();

        flowerAdapter= new FlowerAdapter(root.getContext(),list);
        recyclerView.setAdapter(flowerAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FlowerClass flower= dataSnapshot.getValue(FlowerClass.class);
                    String key = snapshot.getKey();
                    list.add(flower);
                }
                flowerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       return root;
   }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}