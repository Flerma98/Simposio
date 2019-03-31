package com.framgentoestudio.simposio_ciencias_basicas;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registrarse extends AppCompatActivity {

    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference(FireBaseReference.REFERENCIAVENDEDORES);

    Button btnRegistrarse, btnCancelar;
    TextInputLayout tilNombre, tilNumero, tilCorreo, tilNControl, tilContraseña;
    EditText txtNombre, txtNumero, txtCorreo, txtNControl, txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        btnRegistrarse= findViewById(R.id.btn_registrarse_Registrar);
        btnCancelar= findViewById(R.id.btn_registrarse_Cancelar);
        tilNombre= findViewById(R.id.til_Registrarse_Nombre);
        tilNumero= findViewById(R.id.til_Registrarse_Numero);
        tilCorreo= findViewById(R.id.til_Registrarse_Correo);
        tilContraseña= findViewById(R.id.til_Registrarse_Contraseña);
        txtNombre= findViewById(R.id.txt_registrarse_Nombre);
        txtNumero= findViewById(R.id.txt_registrarse_Numero);
        txtCorreo= findViewById(R.id.txt_registrarse_Correo);
        txtContraseña= findViewById(R.id.txt_registrarse_Contraseña);
        txtNControl= findViewById(R.id.txt_registrarse_NControl);
        tilNControl= findViewById(R.id.til_Registrarse_NControl);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombre.getText().toString().isEmpty() || txtNumero.getText().toString().isEmpty() || txtCorreo.getText().toString().isEmpty() || txtContraseña.getText().toString().isEmpty() || txtNControl.getText().toString().length()<8 || txtContraseña.getText().toString().length()<4){
                    if (txtNombre.getText().toString().trim().isEmpty()) {
                        tilNombre.setError("No debe dejar vacio este campo");
                    }
                    if (txtNumero.getText().toString().trim().isEmpty()) {
                        tilNumero.setError("No debe dejar vacio este campo");
                    }
                    if (txtCorreo.getText().toString().trim().isEmpty()) {
                        tilCorreo.setError("No debe dejar vacio este campo");
                    }
                    if (txtNControl.getText().toString().length()<8) {
                        txtNControl.setError("Debe completar este campo");
                    }
                    if (txtContraseña.getText().toString().trim().isEmpty()) {
                        tilContraseña.setError("No debe dejar vacio este campo");
                    }else{
                        if (txtContraseña.getText().toString().trim().length() < 4) {
                            tilContraseña.setError("La contraseña debe llevar mas caracteres");
                        }
                    }
                }else{
                    final ProgressDialog dialog = ProgressDialog.show(Registrarse.this, "Registrando",
                            "Registrando un nuevo usuario....", true, false);
                    Auth.createUserWithEmailAndPassword(txtNControl.getText().toString().trim() + "@simposio.com", txtContraseña.getText().toString().trim() + "99").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {
                                if (task.isSuccessful()) {
                                    Datos_Vendedores datos = new Datos_Vendedores(Auth.getUid(), txtNombre.getText().toString().trim(), txtNumero.getText().toString().trim(), txtCorreo.getText().toString().trim(), Integer.parseInt(txtNControl.getText().toString()),Integer.parseInt(txtContraseña.getText().toString()));
                                    myRef.child(Auth.getUid()).setValue(datos);
                                    finish();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        try {
                                        }catch (Exception e){}
                                        Toast.makeText(getApplicationContext(), "Usuario ya en uso", Toast.LENGTH_SHORT).show();
                                    } else {
                                        try {
                                        }catch (Exception e){}
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch (Exception e){}
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
