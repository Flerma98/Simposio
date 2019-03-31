package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Visualizar_Vendedor extends AppCompatActivity {

    public static Datos_Vendedores vendedor;
    TextView txtNombre, txtNumero, txtCorreo, txtNControl, txtNIP, txtBoletos;
    RecyclerView rv_boletos;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(FireBaseReference.REFERENCIACOMPRADORES);
    ArrayList<Compradores> lista_Compradores= new ArrayList<>();
    adapter_rv_compradores adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_visualizar__vendedor);
        setTitle("Datos de " + vendedor.getNombre());

        txtNombre= findViewById(R.id.vzv_nombre);
        txtNumero= findViewById(R.id.vzv_numero);
        txtCorreo= findViewById(R.id.vzv_correo);
        txtNControl= findViewById(R.id.vzv_ncontrol);
        txtNIP= findViewById(R.id.vzv_nip);
        txtBoletos= findViewById(R.id.vzv_boletos_vendidos);
        rv_boletos= findViewById(R.id.vzv_rv);

        txtNombre.setText("Nombre: " + vendedor.getNombre());
        txtNumero.setText("Número: " + String.valueOf(vendedor.getNumero()));
        txtCorreo.setText("Correo: " + vendedor.getCorreo());
        txtNControl.setText("Número de Control: " + String.valueOf(vendedor.getNControl()));
        txtNIP.setText("NIP: " + String.valueOf(vendedor.getNIP()));
        txtBoletos.setText("");

        rv_boletos.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapter = new adapter_rv_compradores(lista_Compradores, getApplicationContext(), rv_boletos);
        rv_boletos.setAdapter(adapter);
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_slide_from_bottom);
        rv_boletos.setLayoutAnimation(layoutAnimationController);

        try {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lista_Compradores.clear();
                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                            Compradores comprador = objSnaptshot.getValue(Compradores.class);
                            if (vendedor.getNombre().equals(comprador.getVendedora())) {
                                lista_Compradores.add(comprador);
                            }
                            txtBoletos.setText("Boletos vendidos: " + lista_Compradores.size());
                        adapter.notifyDataSetChanged();
                        rv_boletos.scheduleLayoutAnimation();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        } catch (Exception e) {}

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visualizar.compradora= (adapter_rv_compradores.compradores_filtrados.get(rv_boletos.getChildAdapterPosition(v)));
                startActivity(new Intent(getApplicationContext(), Visualizar.class));
            }
        });
    }
}
