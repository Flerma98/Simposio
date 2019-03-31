package com.framgentoestudio.simposio_ciencias_basicas;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btnIniciar, btnRegistrarse;
    EditText Correo, Contraseña;
    TextInputLayout tCorreo, tContraseña;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(MainActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            //Si alguno de los permisos no esta concedido lo solicita
            ActivityCompat.requestPermissions(MainActivity.this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        }


        Auth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    try {
                        if(user.getUid().equals("vhALkoN8T0X6YPD0PYWhzVGOgaK2")){
                            Intent intent = new Intent(MainActivity.this, Administrador.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(MainActivity.this, Vendedores.class);
                            startActivity(intent);
                            finish();
                        }
                                return;
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Ocurrió un error intentelo de nuevo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };

        btnIniciar = findViewById(R.id.btn_login_Iniciar_Sesion);
        btnRegistrarse = findViewById(R.id.btn_login_Registrarse);

        Correo = findViewById(R.id.txt_login_Correo);
        Contraseña = findViewById(R.id.txt_login_Contraseña);

        tCorreo = findViewById(R.id.tilCorreo);
        tContraseña = findViewById(R.id.tilContraseña);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Correo.getText().toString().trim().isEmpty() || Contraseña.getText().toString().trim().isEmpty()) {
                    if (Correo.getText().toString().trim().isEmpty()) {
                        tCorreo.setError("No debe dejar vacio este campo");
                    }
                    if (Contraseña.getText().toString().trim().isEmpty()) {
                        tContraseña.setError("No debe dejar vacio este campo");
                    }
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Correo.getWindowToken(), 0);
                    try {
                        Auth.signInWithEmailAndPassword(Correo.getText().toString().trim() + "@simposio.com", Contraseña.getText().toString().trim() + "99").addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                } else {
                                    Toast.makeText(MainActivity.this, "No existe una cuenta con esos datos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Ocurrió un error intentelo de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registrarse.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Auth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Auth.removeAuthStateListener(firebaseAuthListener);
    }
}
