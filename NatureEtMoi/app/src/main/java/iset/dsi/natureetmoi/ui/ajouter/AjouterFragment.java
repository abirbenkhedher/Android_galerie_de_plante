package iset.dsi.natureetmoi.ui.ajouter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import iset.dsi.natureetmoi.DataManager;
import iset.dsi.natureetmoi.FlowerClass;
import iset.dsi.natureetmoi.R;
import iset.dsi.natureetmoi.databinding.FragmentAjouterBinding;

public class AjouterFragment extends Fragment {

    DataManager dataManager;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference database ;
    Button addFlower;
    EditText nom;
    EditText desc;
    EditText croi;
    EditText cons;
    EditText fav;
    EditText uri;
    ImageView imageView,photo;
    Uri photo_to_upload;
    // Uri newuri;
    FlowerClass flowerClass;
    View root;



    private AjouterViewModel notificationsViewModel;
    private FragmentAjouterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(AjouterViewModel.class);

        binding = FragmentAjouterBinding.inflate(inflater, container, false);
         root = binding.getRoot();



        dataManager = new DataManager();
        addFlower = (Button) root.findViewById(R.id.newflowerbutton);
        nom = (EditText) root.findViewById(R.id.newname);
        desc = (EditText) root.findViewById(R.id.newdesc);
        croi = (EditText) root.findViewById(R.id.newcroi);
        cons = (EditText) root.findViewById(R.id.newcons);
        //fav=(EditText) findViewById(R.id.newfav);
        uri = (EditText) root.findViewById(R.id.newuri);
        imageView = (ImageView) root.findViewById(R.id.flower_add_photo);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        flowerClass = new FlowerClass();
        //notifier = new Notifier();
        photo=(ImageView)root.findViewById(R.id.flower_photo) ;



        nom.setText(getArguments().getString("nom"));
        desc.setText(getArguments().getString("desc"));
        croi.setText(getArguments().getString("crois"));
        cons.setText(getArguments().getString("cons"));
        if ((getArguments().getString("uri"))==null){

            imageView.setImageURI(Uri.parse((getArguments().getString("uri"))));
        }else {
            imageView.setImageResource(R.drawable.samplephoto);

        }


        addFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newnom = nom.getText().toString();
                String newdesc = desc.getText().toString();
                String newcroi = croi.getText().toString();
                String newcons = cons.getText().toString();
                Boolean newfav = false;
                // String newuri=uri.getText().toString();
                flowerClass.setNom(newnom);
                flowerClass.setDesc(newdesc);
                flowerClass.setCons(newcons);
                flowerClass.setCroi(newcroi);
                flowerClass.setFav(newfav);

                dataManager.AddData(flowerClass,storage,database,root.findViewById(R.id.addview));
                clearForm();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    galleryActivityLauncher.launch(new String[]{"image/*"});
                }
               catch (Exception exception){
                   dataManager.notificaton(v, "Erreur ");

               }
            }
        });


















        return root;
    }
    public void clearForm() {

        ((TextInputEditText) root.findViewById(R.id.newname)).getText().clear();
        ((TextInputEditText) root.findViewById(R.id.newcons)).getText().clear();
        ((TextInputEditText) root.findViewById(R.id.newcroi)).getText().clear();
        ((TextInputEditText) root.findViewById(R.id.newdesc)).getText().clear();
        ((ImageView) root.findViewById(R.id.flower_add_photo)).setImageURI(null);


    }
    ActivityResultLauncher<String[]> galleryActivityLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                // perform desired operations using the result Uri
                imageView.setImageURI(result);
                photo_to_upload = result;
                flowerClass.setUri(photo_to_upload.toString());
            } else {

            }
        }
    });




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}