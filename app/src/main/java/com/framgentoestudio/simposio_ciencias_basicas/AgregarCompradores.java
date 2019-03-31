package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class AgregarCompradores extends AppCompatActivity {

    Button btnRegistrar, btnCancelar;
    Spinner spnTalla, spnSemestre, spnCarrera, spnTaller;
    EditText txtNombre, txtNControl, txtImporte;
    TextView txtDisponible;
    int Disponibles= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_agregar_compradores);

        spnTalla= findViewById(R.id.spn_Registrar_Comprador_Talla);
        spnSemestre= findViewById(R.id.spn_Registrar_Comprador_Semestre);
        spnCarrera= findViewById(R.id.spn_Registrar_Comprador_Carrera);
        spnTaller= findViewById(R.id.spn_Registrar_Comprador_Taller);

        txtNombre= findViewById(R.id.txt_Registrar_Comprador_Nombre);
        txtNControl= findViewById(R.id.txt_Registrar_Comprador_Numero_Control);
        txtImporte= findViewById(R.id.txt_Registrar_Comprador_Importe);

        txtDisponible= findViewById(R.id.txtDisponibles);

        setTitle("Agregar Comprador");

        spnTalla.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, Vendedores.tallas));

        spnSemestre.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, Vendedores.semestres));

        spnCarrera.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, Vendedores.carrera));

        spnTaller.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, Vendedores.taller));

        spnTaller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                        if (spn.getItemAtPosition(posicion).toString().equals("-Pago pendiente-")) {
                            txtDisponible.setText("-");
                        } else {
                            int encontrados = 0;
                            for (int i = 0; i < Vendedores.lista_Compradores_Todos.size(); i++) {
                                if (Vendedores.lista_Compradores_Todos.get(i).getTaller().equals(spn.getItemAtPosition(posicion).toString())) {
                                    encontrados++;
                                }
                            }
                            switch (spn.getItemAtPosition(posicion).toString()) {
                                case "Tecnología en la Educación":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Aprenda usar Calculadora Cientifica":
                                    Disponibles = 58 - encontrados;
                                    break;
                                case "GeoGebra":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Autodesk Inventor":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Programación Básica con Matlab":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Muestreo y procesamiento de datos de Excel":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Apps de Matemáticas para Android":
                                    Disponibles = 48 - encontrados;
                                    break;
                                case "Sensores automotrices con osciloscopio":
                                    Disponibles = 48 - encontrados;
                                    break;
                                case "Estadistica Aplicada con Excel":
                                    Disponibles = 38 - encontrados;
                                    break;
                                case "Estereoquimica y aromas":
                                    Disponibles = 20 - encontrados;
                                    break;
                            }
                            txtDisponible.setText(Disponibles + "");
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });

        btnRegistrar= findViewById(R.id.btn_registrar_comprador);

        btnCancelar= findViewById(R.id.btn_cancelar_comprador);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombre.getText().toString().isEmpty() || txtImporte.getText().toString().isEmpty() || Integer.parseInt(txtImporte.getText().toString())>600){

                }else {
                    if(Disponibles>0 || spnTaller.getSelectedItemPosition()==0) {
                        boolean si = true;
                        Compradores comprador = new Compradores();
                        comprador.setUID(UUID.randomUUID().toString());
                        comprador.setNombre(txtNombre.getText().toString().trim());
                        if (txtNControl.getText().toString().isEmpty()) {
                            comprador.setNumero_Control("Aun por confirmar");
                        } else {
                            comprador.setNumero_Control(txtNControl.getText().toString());
                        }
                        comprador.setTalla(spnTalla.getSelectedItem().toString());
                        comprador.setSemestre(spnSemestre.getSelectedItem().toString());
                        comprador.setCarrera(spnCarrera.getSelectedItem().toString());
                        comprador.setUIDVendedores(Vendedores.Auth.getUid());
                        if (Integer.parseInt(txtImporte.getText().toString()) < 600) {
                            comprador.setTaller(spnTaller.getItemAtPosition(0).toString());
                        } else {
                            if (spnTaller.getSelectedItemPosition() == 0) {
                                si = false;
                            }
                            comprador.setTaller(spnTaller.getSelectedItem().toString());
                        }
                        if (si) {
                            comprador.setImportes(txtImporte.getText().toString().trim());
                            comprador.setFechas(getFechaHora());
                            comprador.setTotal("600");
                            comprador.setVendedora(Vendedores.vendedora.getNombre());
                            if (adapter_rv_todos.compradores_source.isEmpty()) {
                                comprador.setFolio("1");
                            } else {
                                ArrayList<Compradores> compradores = adapter_rv_todos.compradores_source;
                                Collections.sort(compradores, new Comparator<Compradores>() {
                                    @Override
                                    public int compare(Compradores p1, Compradores p2) {
                                        return new Integer(p1.getFolio()).compareTo(new Integer(p2.getFolio()));
                                    }
                                });
                                int folio = Integer.parseInt(compradores.get(compradores.size() - 1).getFolio()) + 1;
                                comprador.setFolio(String.valueOf(folio));
                            }
                            Vendedores.myRef.child(comprador.getUID()).setValue(comprador);
                            finish();
                        }
                    }
                }
            }
        });
    }

    public static String getHora() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        String formattedDate=dateFormat.format(date);
        return formattedDate;
    }

    public static String getFecha(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getFechaHora(){
        return getHora() + " - " + getFecha();
    }
}
