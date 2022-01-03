package iset.dsi.natureetmoi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {
    Context context;
    ArrayList<FlowerClass> list;
    DataManager dataManager = new DataManager();


    public FlowerAdapter(Context context, ArrayList<FlowerClass> list ) {
        this.list= new ArrayList<>();
        this.context = context;
        this.list.clear();
        this.list = list;

    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return new FlowerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        FlowerClass  flowerClass=list.get(position);
        holder.nameView.setText(flowerClass.getNom());
        holder.descView.setText(flowerClass.getDesc());
        holder.consView.setText(flowerClass.getCons());
        holder.croiView.setText(flowerClass.getCroi());
        holder.favView.setText(String.valueOf(flowerClass.isFav()));
        holder.uriView.setText(String.valueOf(flowerClass.getUri()));
        String u = String.valueOf(flowerClass.getUri());
        if (holder.favView.getText()=="true") {
            holder.fav.setImageResource(R.drawable.ic_star);
        }
        else {
            holder.fav.setImageResource(R.drawable.ic_unstar);
        }

        Glide.with(holder.imageView).load(u).into(holder.imageView);
       //
        holder.del.setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View v) {

             new AlertDialog.Builder(context)
                     .setTitle("Supprimer Plante")
                     .setMessage("Vouler vous vraiment supprimer cette plante ")
                     .setCancelable(false)
                     .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                             dataManager.DeleteData(flowerClass);
                             list.clear();
                             dataManager.notificaton(holder.imageView, "Supprimée");
                             //  Intent i= new Intent (context, context.getClass());
                             // context.startActivity(i);
                             notifyDataSetChanged();
                         }
                     })//set negative button
                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {

                         }
                     })
                     .show();




          }
   });
           holder.fav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
               DatabaseReference mDatabaseRef = database.getReference("flowers");
                int f = list.indexOf(flowerClass);
                if (flowerClass.isFav()){
                    mDatabaseRef.child(flowerClass.getNom()).child("fav").setValue(false);
                    flowerClass.setFav(false);
                    holder.fav.setImageResource(R.drawable.ic_star);
                   dataManager.notificaton(holder.imageView, "Supprimée du Collection ");
                  // Activity.class.getClass();


                }else {
                    mDatabaseRef.child(flowerClass.getNom()).child("fav").setValue(true);
                    flowerClass.setFav(true);
                    holder.fav.setImageResource(R.drawable.ic_unstar);
                    dataManager.notificaton(holder.imageView, "Ajouté au Collection ");

                }
                notifyDataSetChanged();
             }

           });

           holder.imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if ((holder.del.getVisibility())== View.INVISIBLE){



                    holder.l1.setVisibility(View.VISIBLE);
                    holder.l2.setVisibility(View.VISIBLE);
                    holder.l3.setVisibility(View.VISIBLE);
                    holder.l4.setVisibility(View.VISIBLE);
                    holder.del.setVisibility(View.VISIBLE);
                    holder.update.setVisibility(View.VISIBLE);}
                   else {
                       holder.l1.setVisibility(View.INVISIBLE);
                       holder.l2.setVisibility(View.INVISIBLE);
                       holder.l3.setVisibility(View.INVISIBLE);
                       holder.l4.setVisibility(View.INVISIBLE);
                       holder.del.setVisibility(View.INVISIBLE);
                       holder.update.setVisibility(View.INVISIBLE);

                   }

               }
           });
holder.photoview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Dialog builder = new Dialog(v.getContext());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });
        BitmapDrawable drawable = (BitmapDrawable) holder.imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap( bitmap, 1000, 1000, false);
        ImageView im = new ImageView(context);

        im.setImageBitmap(resizedBitmap);
        builder.addContentView(im, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        builder.show();





    }
});


           holder.update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Bundle bundle = new Bundle();
                   bundle.putString("nom", flowerClass.getNom());
                   bundle.putString("desc", flowerClass.getDesc());
                   bundle.putString("crois", flowerClass.getCroi());
                   bundle.putString("cons", flowerClass.getCons());
                   bundle.putString("uri", flowerClass.getUri().toString());
                  // holder.imageView.setImageURI(Uri.parse(flowerClass.getUri()));
                   //TextView tv = (View).findViewById(R.id.textViewAmount);
                  // tv.setText(getArguments().getString("amount"));
                   //Navigation.findNavController(view).navigate(R.id.confirmationAction, bundle);
                   Navigation.findNavController(holder.imageView).navigate(R.id.navigation_ajouter,bundle);              }
           });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder{
        TextView nameView,descView,consView,croiView,favView,uriView ,l1,l2,l3,l4;
        ImageView imageView ,fav ,del,update,photoview;
        LinearLayout up;
        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView=itemView.findViewById(R.id.nomItem);
            descView=itemView.findViewById(R.id.descItem);
            consView=itemView.findViewById(R.id.consItem);
            croiView=itemView.findViewById(R.id.croiItem);
            favView=itemView.findViewById(R.id.favItem);
            uriView=itemView.findViewById(R.id.urlItem);
            imageView=itemView.findViewById(R.id.flower_photo);
            fav=itemView.findViewById(R.id.fav);
            del=itemView.findViewById(R.id.del);
            update=itemView.findViewById(R.id.modif);
            //up=itemView.findViewById(R.id.up);
            l1=itemView.findViewById(R.id.consItem);
            l2=itemView.findViewById(R.id.conslabel);
            l3=itemView.findViewById(R.id.croiItem);
            l4=itemView.findViewById(R.id.croilabel);
            photoview =itemView.findViewById(R.id.view_photo);


        }
    }
}
