package iset.dsi.natureetmoi.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import iset.dsi.natureetmoi.databinding.FragmentCollectionBinding;

public class CollectionFragment extends Fragment {

    private CollectionViewModel collectionViewModel;
    private FragmentCollectionBinding binding;
    RecyclerView recyclerView;
    DatabaseReference database;
    FlowerAdapter flowerAdapter;
    ArrayList<FlowerClass> list;
    ImageView fav;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        collectionViewModel =
                new ViewModelProvider(this).get(CollectionViewModel.class);

        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

      //  final TextView textView = binding.textDashboard;
       // collectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
           // @Override
          //  public void onChanged(@Nullable String s) {
               // textView.setText(s);
         //   }
       // });

        recyclerView = root.findViewById(R.id.flowerList);


        database= FirebaseDatabase.getInstance().getReference("flowers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        list= new ArrayList<>();
        // listKeys= new ArrayList<>();

        flowerAdapter= new FlowerAdapter(root.getContext(),list);
        recyclerView.setAdapter(flowerAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FlowerClass flower= dataSnapshot.getValue(FlowerClass.class);

                    if(flower.isFav()){
                        list.add(flower);

                    }
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