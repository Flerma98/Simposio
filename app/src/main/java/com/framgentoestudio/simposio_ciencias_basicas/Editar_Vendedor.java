package com.framgentoestudio.simposio_ciencias_basicas;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editar_Vendedor extends AppCompatActivity {

    EditText txtNombre, txtNumero, txtCorreo, txtNControl, txtNIP;
    TextInputLayout tilNombre, tilNumero, tilCorreo, tilNControl, tilNIP;
    Button btnEditar;
    public static Datos_Vendedores vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__vendedor);
        setTitle("Editar vendedor");

        txtNombre= findViewById(R.id.txtEditar_Nombre);
        txtNumero= findViewById(R.id.txtEditar_Numero);
        txtCorreo= findViewById(R.id.txtEditar_Correo);
        txtNControl= findViewById(R.id.txtEditar_NControl);
        txtNIP= findViewById(R.id.txtEditar_NIP);

        tilNombre= findViewById(R.id.tilEditar_Nombre);
        tilNumero= findViewById(R.id.tilEditar_Numero);
        tilCorreo= findViewById(R.id.tilEditar_Correo);
        tilNControl= findViewById(R.id.tilEditar_NControl);
        tilNIP= findViewById(R.id.tilEditar_NIP);

        btnEditar= findViewById(R.id.btnEditar_vendedor);

        tilNombre.setHint("Nombre: " + vendedor.getNombre());
        tilNumero.setHint("Número: " + vendedor.getNumero());
        tilCorreo.setHint("Correo: " + vendedor.getCorreo());
        tilNControl.setHint("Número de control: " + vendedor.getNControl());
        tilNIP.setHint("NIP: " + vendedor.getNIP());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty()) {
                    Administrador.referenciaVendedores.child(vendedor.getUID()).child("nombre").setValue(txtNombre.getText().toString().trim());
                }
                if (!txtNumero.getText().toString().isEmpty()) {
                    Administrador.referenciaVendedores.child(vendedor.getUID()).child("numero").setValue(txtNumero.getText().toString().trim());
                }
                if (!txtCorreo.getText().toString().isEmpty()) {
                    Administrador.referenciaVendedores.child(vendedor.getUID()).child("correo").setValue(txtCorreo.getText().toString().trim());
                }
                if (!txtNControl.getText().toString().isEmpty()) {
                    Administrador.referenciaVendedores.child(vendedor.getUID()).child("ncontrol").setValue(Integer.parseInt(txtNControl.getText().toString().trim()));
                }
                if (!txtNIP.getText().toString().isEmpty()) {
                    Administrador.referenciaVendedores.child(vendedor.getUID()).child("nip").setValue(Integer.parseInt(txtNIP.getText().toString().trim()));
                }
                finish();
            }
        });
    }
}
