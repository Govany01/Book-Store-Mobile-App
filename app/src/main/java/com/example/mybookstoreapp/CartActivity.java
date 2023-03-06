package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends BaseActivity {

    private RecyclerView rv;
    private CartItemAdapter adapter;
    private CollectionReference colRef;
    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        rv = (RecyclerView) findViewById(R.id.myRecycler);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        colRef = db.collection("Cart").document("Cart").collection(userId);

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<HashMap<String, Object>> items = new ArrayList<>();

                    //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    for(DocumentSnapshot doc : task.getResult().getDocuments()){
                        HashMap<String, Object> map =  (HashMap<String, Object>) doc.getData();
                        items.add(map);

                    }
                    adapter = new CartItemAdapter(CartActivity.this, items, true);

                    LinearLayoutManager layout = new LinearLayoutManager(CartActivity.this);
                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);

                    DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), layout.getOrientation());
                    divider.setDrawable(getDrawable(R.drawable.gradient_divider));
                    rv.addItemDecoration(divider);
                }
            }
        });




//        HashMap<String, Object> m = new HashMap<>();
//        m.put("title", "Maybe Someday");
//        m.put("price", "$35.17");
//        items.add(m);
//        m = new HashMap<>();
//        m.put("title", "Spinning Silver");
//        m.put("price", "$35.17");
//        items.add(m);


        checkout = (Button) findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().getDocuments().isEmpty()){
                                Toast.makeText(CartActivity.this, "Cart is empty. Put some books in your cart first!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                                startActivity(i);
                            }
                        }
                    }
                });

            }
        });

    }


}