package iset.dsi.natureetmoi;

import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DataManager {

    public void AddData(FlowerClass flowerClass, FirebaseStorage storage, DatabaseReference db, View notifView) {
        StorageReference storageReference = storage.getReference();

        storage = FirebaseStorage.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("flowers");
        //addNewFlower(flowerClass ,database );
        String id = database.push().getKey();
        SetPhoto(flowerClass, storageReference, database, notifView);
        // database.child(id).setValue(flowerClass);
    }

    private void SetPhoto(FlowerClass flowerClass, StorageReference storageReference, DatabaseReference database, View view) {
        notificaton(view, "Uploading Photo ");

            try {


                Uri photoUri = Uri.parse(flowerClass.getUri());
                StorageReference ref = storageReference.child("flowers_photo/" + flowerClass.getNom());
                UploadTask uploadTask = ref.putFile(photoUri);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            flowerClass.setUri(downloadUri.toString());
                            String id = database.push().getKey();
                            database.child(flowerClass.getNom()).setValue(flowerClass);
                            //snackbar=  Snackbar.make(findViewById(R.id.addview),"Fleur Ajoutee ",Snackbar.LENGTH_SHORT);
                            // snackbar.show();
                            notificaton(view, "Fleur Ajoutée " + flowerClass.getNom());
                        } else {
                            notificaton(view, "Erreur ");
                        }
                    }
                });

            }catch (Exception exception){
                notificaton(view, "Erreur ");}



    }

    public void notificaton(View view, String msg) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void UpdateData(FlowerClass flowerClass, StorageReference storageReference, DatabaseReference database, View view) {
        notificaton(view, "Uploading Photo ");
        Uri photoUri = Uri.parse(flowerClass.getUri());
        StorageReference ref = storageReference.child("flowers_photo/" + flowerClass.getNom());
        UploadTask uploadTask = ref.putFile(photoUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    flowerClass.setUri(downloadUri.toString());
                    String id = database.push().getKey();
                    database.child(flowerClass.getNom()).setValue(flowerClass);
                    //snackbar=  Snackbar.make(findViewById(R.id.addview),"Fleur Ajoutee ",Snackbar.LENGTH_SHORT);
                    // snackbar.show();
                    notificaton(view, "Fleur"+ flowerClass.getNom()+" Ajoutée ");
                } else {
                    notificaton(view, "Erreur ");
                }
            }
        });
    }

    public void DeleteData(FlowerClass flowerClass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference("flowers");
        mDatabaseRef.child(flowerClass.getNom()).removeValue();



        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(flowerClass.getUri());
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });





    }
}



