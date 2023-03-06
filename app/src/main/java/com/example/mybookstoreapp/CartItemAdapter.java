package com.example.mybookstoreapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder>{


    private Context context;
    private static boolean priceBlue = false;
    private ArrayList<HashMap<String, Object>> bookList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price;
        ImageView cover;
        ImageButton remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView)  itemView.findViewById(R.id.price);
            remove = (ImageButton) itemView.findViewById(R.id.remove);
            if(priceBlue){
                price.setTextColor(0xff05aaff);
                remove.setVisibility(View.VISIBLE);
            }
            cover = (ImageView) itemView.findViewById(R.id.cover);
        }
    }

    public CartItemAdapter(Context c, ArrayList<HashMap<String, Object>> bookList, boolean blue){
        this.context = c;
        this.bookList = bookList;
        this.priceBlue = blue;

    }


    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {

        HashMap<String, Object> m = bookList.get(position);
        holder.title.setText((String)m.get("Name"));
        int p = Integer.parseInt((String)m.get("Price"));
        holder.price.setText(p + "L.E.");
        //holder.cover.setImageResource(m.getCoverId());

        try {
            Uri uri = Uri.parse((String)m.get("Cover"));
            Glide.with(context).load(uri).into(holder.cover);
        }
        catch(NullPointerException exception){

        };


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference ref = db.collection("Cart").document("Cart")
                        .collection(userId).document((String)m.get("Name"));
                ref.delete();
                Context c = view.getContext();
                ((Activity)c).recreate();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
