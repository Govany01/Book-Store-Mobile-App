package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckoutDoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_done);

        Button Browse=(Button) findViewById(R.id.Browse);
        Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Backhome= new Intent(CheckoutDoneActivity.this, CategoryActivity.class);
                startActivity(Backhome);
            }
        });
    }
}