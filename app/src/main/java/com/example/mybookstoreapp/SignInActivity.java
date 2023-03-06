package com.example.mybookstoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {

    FirebaseAuth mAuth ;
    EditText Email;
    EditText Password ;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

//        View v = getLayoutInflater().inflate(R.layout.appbar, findViewById(R.id.myLayout));
//        setMyActionBar(v);
//
        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this,CategoryActivity.class));
            finish();
        }


        Email=(EditText) findViewById(R.id.UserName);
        Password=(EditText) findViewById(R.id.Password);
        // TextView forgotPass=(TextView) findViewById(R.id.forgotPass);
        signInBtn=(Button) findViewById(R.id.signInBtn);
        TextView SignUP=(TextView) findViewById(R.id.SignUP);

        signInBtn.setOnClickListener(view->{
            LoginUser();
//            startActivity(new Intent(SignInActivity.this,CategoryActivity.class));
        });

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });


    }


    public void signUp(){
        Intent up=new Intent(this,SignUpActivity.class);
        startActivity(up);
    }
    private void LoginUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if(TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignInActivity.this, "SignIn Successfully  ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this,CategoryActivity.class));

                    }else{
                        Toast.makeText(SignInActivity.this, "SignIn Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }
}