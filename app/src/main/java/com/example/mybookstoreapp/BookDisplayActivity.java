package com.example.mybookstoreapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class BookDisplayActivity extends BaseActivity {
    RecyclerView recyclerView;
    DocumentReference Docref;
    CollectionReference colRef;
    int[]value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);

//        ActionBar ab=getSupportActionBar();
//        ab.setDisplayShowCustomEnabled(true);
//        ab.setCustomView(R.layout.appbar);
        value=getIntent().getIntArrayExtra("Values");

        recyclerView = findViewById(R.id.recyclerview);

        sequence();
        Log.d("Path: ", colRef.getPath());
        //CollectionReference colRef = FirebaseFirestore.getInstance().collection("English");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    Log.d("Path: ", "Success");
                    ArrayList<HashMap<String, Object>> items = new ArrayList<>();

                    //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        HashMap<String, Object> map = (HashMap<String, Object>) doc.getData();
                        Log.d("Path: ", map.toString());
                        items.add(map);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(BookDisplayActivity.this));
                    recyclerView.setAdapter(new MyAdapter(BookDisplayActivity.this, items));

                }
                else{
                    Log.d("verina:", "ffmmmllll");
                }
            }
        });


    }

    public void sequence(){
        switch(value[0]){
            case(0):
                Docref = FirebaseFirestore.getInstance().collection("English").document("fgjKQbYSuG67Mt6lUfJo");
                break;
            case(1):
                Docref=FirebaseFirestore.getInstance().collection("Arabic").document("3tGzmSjNIFh6ykzoC1ON");
                break;

        }
        switch(value[1]){
            case(0):
                if(value[0]==0) {
                    Docref = Docref.collection("Books").document("Xr4rLrzK2x7E7QVWLJ3o");
                }else{
                    Docref = Docref.collection("Books").document("uxr0SZeQt7I0C5nYwGEa");}
                break;
            case(1):
                if(value[0]==0) {
                    Docref = Docref.collection("Novels").document("UNbPNxAmFBjSJyT06Mrq");
                }else{
                    Docref = Docref.collection("Novels").document("pPK8BWyklu3PYkeTHUKt");}
                break;
        }
        switch (value[2]){
            case(0):
                colRef = Docref.collection("Adventure");
                break;
            case(1):
                colRef = Docref.collection("Crime");
                break;
            case(2):
                colRef = Docref.collection("Drama");
                break;
            case(3):
                colRef = Docref.collection("Fantasy");
                break;
            case(4):
                colRef = Docref.collection("Fiction");
                break;
            case(5):
                colRef = Docref.collection("Horror");
                break;
            case(6):
                colRef = Docref.collection("Realism");
                break;
            case(7):
                colRef = Docref.collection("Romance");
                break;

        }
    }
}