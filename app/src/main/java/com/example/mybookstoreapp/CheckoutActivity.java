package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class CheckoutActivity extends BaseActivity {

    private RecyclerView rv;
    private CartItemAdapter adapter;
    protected int total = 20;
    private LinearLayout cardInfo;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        rv = (RecyclerView) findViewById(R.id.myRecycler);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference colRef = db.collection("Cart").document("Cart").collection(userId);

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<HashMap<String, Object>> items = new ArrayList<>();

                    //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                    for(DocumentSnapshot doc : task.getResult().getDocuments()){
                        HashMap<String, Object> map =  (HashMap<String, Object>) doc.getData();
                        items.add(map);
                        total = total + Integer.parseInt((String)map.get("Price"));;
                    }
                    adapter = new CartItemAdapter(CheckoutActivity.this, items, false);

                    LinearLayoutManager layout = new LinearLayoutManager(CheckoutActivity.this);
                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);

                    DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), layout.getOrientation());
                    divider.setDrawable(getDrawable(R.drawable.gradient_divider));
                    rv.addItemDecoration(divider);

                    ((TextView) findViewById(R.id.totalFee)).setText(total + " L.E.");

                }
            }
        });
        cardInfo = (LinearLayout) findViewById(R.id.cardInfo);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // akid lazem yeb2a fi hena kalam ya5od el input ba2a w y7oto fl database w yfadi el cart kman
                // w tab3an yet2aked en mafi4 baynat na2sa..

                Intent i = new Intent(CheckoutActivity.this, CheckoutDoneActivity.class);
                startActivity(i);

            }
        });
    }

    public void editPayOptions(View v){

        switch(v.getId()){
            case(R.id.cash):
                cardInfo.setVisibility(View.GONE);
                break;
            case(R.id.payNow):
                cardInfo.setVisibility(View.VISIBLE);
                cardInfo.setFocusable(true);
                cardInfo.setFocusableInTouchMode(true);
                cardInfo.requestFocus();
                break;
        }

    }
}