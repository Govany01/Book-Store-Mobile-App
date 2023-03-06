package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

public class CategoryActivity extends BaseActivity {

    ConstraintLayout BookNovel;
    ConstraintLayout Category;
    int[] values = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        TextView welcome = (TextView) findViewById(R.id.Welcome);
        String msg = "Welcome, " + auth.getCurrentUser().getDisplayName() + "!";
        welcome.setText(msg);

        Button Horror= (Button) findViewById(R.id.Horror);
        Button Fiction=(Button) findViewById(R.id.Fiction);
        Button Fantasy=(Button) findViewById(R.id.Fantasy);
        Button Romance=(Button) findViewById(R.id.Romance);
        Button Crime=(Button) findViewById(R.id.Crime);
        Button Adventure=(Button) findViewById(R.id.Adventure);
        Button Realism=(Button) findViewById(R.id.Realism);
        Button Drama=(Button) findViewById(R.id.Drama);

//        ImageButton toCart= (ImageButton) findViewById(R.id.Cart);
//        toCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toCart=new Intent(CategoryActivity.this, CartActivity.class);
//                startActivity(toCart);
//            }
//        });

        BookNovel=(ConstraintLayout) findViewById(R.id.BooksNovels);
        Category=(ConstraintLayout) findViewById(R.id.Category);


    }

    public void toBookDisplay(View v){

        switch(v.getId()) {
            case (R.id.Adventure):
                values[2] = 0;
                break;
            case (R.id.Crime):
                values[2] = 1;
                break;
            case (R.id.Drama):
                values[2] = 2;
                break;
            case (R.id.Fantasy):
                values[2] = 3;
                break;
            case (R.id.Fiction):
                values[2] = 4;
                break;
            case (R.id.Horror):
                values[2] = 5;
                break;
            case (R.id.Realism):
                values[2] = 6;
                break;
            case (R.id.Romance):
                values[2] = 7;
                break;
        }
        Intent toBD= new Intent(CategoryActivity.this,BookDisplayActivity.class);
        toBD.putExtra("Values",values);
        startActivity(toBD);
    }

    public void BookNovelView(View v){

        switch (v.getId()) {
            case (R.id.English):
                values[0]= 0;
                BookNovel.setVisibility(View.VISIBLE);
                ((ToggleButton)findViewById(R.id.Arabic)).setChecked(false);
                //BookNovel.setFocusable(true);
                //BookNovel.setFocusableInTouchMode(true);
                //BookNovel.requestFocus();
                break;
            case (R.id.Arabic):
                values[0] = 1;
                BookNovel.setVisibility(View.VISIBLE);
                ((ToggleButton)findViewById(R.id.English)).setChecked(false);
                break;
            default:
                BookNovel.setVisibility(View.GONE);
                break;
        }
    }

    // private ConstraintLayout

    public void CategoryView(View v){

        switch (v.getId()){

            case(R.id.Books):
                values[1]=0;
                Category.setVisibility(View.VISIBLE);
                ((ToggleButton)findViewById(R.id.Novels)).setChecked(false);
                break;
            case(R.id.Novels):
                values[1]=1;
                Category.setVisibility(View.VISIBLE);
                ((ToggleButton)findViewById(R.id.Books)).setChecked(false);
                break;
            default:
                Category.setVisibility(View.GONE);
                break;

        }
    }

}