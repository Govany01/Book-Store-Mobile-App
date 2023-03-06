package com.example.mybookstoreapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BookDetailActivity extends BaseActivity {

    TextView title;
    TextView author;
    TextView price;
    TextView published;
    TextView rate;
    TextView overview;
    ImageView cover;
    HashMap<String, Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        cover = (ImageView) findViewById(R.id.imageView2);
        title= (TextView) findViewById(R.id.textView);
        author= (TextView) findViewById(R.id.textView3);
        price= (TextView) findViewById(R.id.textView6);
        overview= (TextView) findViewById(R.id.textView2);

        data = (HashMap<String, Object>) getIntent().getSerializableExtra("data");

        title.setText((String)data.get("Name"));
        author.setText("Author:\n" + (String)data.get("Author"));
        int p = Integer.parseInt((String)data.get("Price"));
        price.setText(p + "L.E.");
        overview.setText((String)data.get("Overview"));

        try {
            Uri uri = Uri.parse((String)data.get("Cover"));
            Glide.with(this).load(uri).into(cover);
        }
        catch(NullPointerException exception){

        };


        Button ToCart=(Button) findViewById(R.id.AddToCart);
        ToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference ref = db.collection("Cart").document("Cart").collection(userId).document((String)data.get("Name"));
                ref.set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent Tocart= new Intent(BookDetailActivity.this, CartActivity.class);
                                startActivity(Tocart);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookDetailActivity.this, "Error Occured. Couldn't add to cart.", Toast.LENGTH_SHORT);
                            }
                        });


//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Intent Tocart= new Intent(BookDetailActivity.this, CartActivity.class);
//                                startActivity(Tocart);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(BookDetailActivity.this, "Error Occured. Couldn't add to cart.", Toast.LENGTH_SHORT);
//                            }
//                        });

            }
        });

    }
}