package com.example.mybookstoreapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<HashMap<String, Object>> items;

    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, Object> m = items.get(position);

        try {
            holder.nameView.setText((String)m.get("Name"));
        }
        catch(NullPointerException exception){
           return;
        };

        holder.authorView.setText((String)m.get("Author"));
        //holder.rateView.setText((String)m.get("Rate"));

            int p = Integer.parseInt((String) m.get("Price"));
            holder.priceView.setText(p + "L.E.");

        try {
            Uri uri = Uri.parse((String) m.get(("Cover")));
            Glide.with(context).load(uri).into(holder.imageView);
        }
        catch(NullPointerException exception){

        };


        (holder.v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Intent i = new Intent(c, BookDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // el data eli bneb3atha l class el book detail
//                i.putExtra("title", ((String) m.get("Title")));
//                i.putExtra("author", ((String) m.get("Author")));
//                i.putExtra("rate", ((String) m.get("Rate")));
//                i.putExtra("price", ((String) m.get("Price")));
//                i.putExtra("overview", ((String) m.get("OVerview")));
                i.putExtra("data", m);
                (context).startActivity(i);
                //Toast.makeText(view.getContext(), "you clicked this book", Toast.LENGTH_SHORT).show();
                //da badal ma aktebo fl main
            }
        });




    }

    @Override
    public int getItemCount() {
        return  items.size();   // list
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View v;
        ImageView imageView;
        TextView nameView,authorView,priceView,rateView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.textViewTitle);
            authorView = itemView.findViewById(R.id.textViewAuthor);
            rateView = itemView.findViewById(R.id.textViewRating);
            priceView = itemView.findViewById(R.id.textViewPrice);
            v = itemView;
        }

//        public void setOnClickListener(View.OnClickListener listener) {
//         this.setOnClickListener(listener);
//        }
//
//        public Context getContext() {
//            return this.getContext();
//        }
    }
}