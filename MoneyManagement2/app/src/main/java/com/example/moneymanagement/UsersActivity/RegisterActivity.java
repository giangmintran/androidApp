package com.example.moneymanagement.UsersActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanagement.MainActivity;
import com.example.moneymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText etFullname, etEmail, etPassword;
    Button registerBtn;

    TextView loginBtn;
    ProgressBar progressBar;


    FirebaseFirestore fstore;
    String userId;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullname = findViewById(R.id.fullname);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String fullName = etFullname.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email không được bỏ trống !");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    etEmail.setError("Vui lòng nhập mật khẩu !");
                    return;
                }

                if (password.length() < 6){
                    etEmail.setError("Mật khẩu phải lớn hơn hoặc bằng 6 ký tự !");
                    return;
                }

                if (TextUtils.isEmpty(fullName)){
                    etEmail.setError("Tên không được bỏ trống !");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Register Successfull", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure: Email Not Sent" + e.getMessage());
                                }
                            });


                            Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"on success: user profile is created" + userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure: " + e.getMessage());
                                }
                            });

                            String Fullname = etFullname.getText().toString().trim();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("key",Fullname);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Register Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });



    }
}