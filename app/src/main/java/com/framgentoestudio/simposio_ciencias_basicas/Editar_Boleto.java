package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Editar_Boleto extends AppCompatActivity {

    public static Compradores boleto;

    EditText txtNombre, txtNControl;
    TextInputLayout tilNombre, tilNControl;
    Spinner spnTalla, spnSemestre, spnCarrera, spnTaller;
    Button btnEditar;
    TextView txtDisponible;
    int Disponibles= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_editar);
        setTitle("Editar boleto");

        txtNombre= findViewById(R.id.txt_EditarBoleto_Nombre);
        txtNControl= findViewById(R.id.txt_EditarBoleto_NControl);
        tilNombre= findViewById(R.id.til_EditarBoleto_Nombre);
        tilNControl= findViewById(R.id.til_EditarBoleto_NControl);
        spnTalla= findViewById(R.id.spm_EditarBoleto_Talla);
        spnSemestre= findViewById(R.id.spm_EditarBoleto_Semestre);
        spnCarrera= findViewById(R.id.spm_EditarBoleto_Carrera);
        spnTaller= findViewById(R.id.spm_EditarBoleto_Taller);
        btnEditar= findViewById(R.id.btnEditar_boleto);
        txtDisponible= findViewById(R.id.txtEditar_Disponibles);

        ArrayList<String> taller= new ArrayList<>();
        taller.add("-Pago pendiente-");
        taller.add("Tecnología en la Educación");
        taller.add("Aprenda usar Calculadora Cientifica");
        taller.add("GeoGebra");
        taller.add("Autodesk Inventor");
        taller.add("Programación Básica con Matlab");
        taller.add("Muestreo y procesamiento de datos de Excel");
        taller.add("Apps de Matemáticas para Android");
        taller.add("Sensores automotrices con osciloscopio");
        taller.add("Estadistica Aplicada con Excel");
        taller.add("Estereoquimica y aromas");

        spnTaller.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, taller));

        ArrayList<String> tallas= new ArrayList<>();
        tallas.add("XS");
        tallas.add("S");
        tallas.add("M");
        tallas.add("L");
        tallas.add("XL");
        tallas.add("XXL");
        spnTalla.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, tallas));

        ArrayList<String> semestres= new ArrayList<>();
        semestres.add("1");
        semestres.add("2");
        semestres.add("3");
        semestres.add("4");
        semestres.add("5");
        semestres.add("6");
        semestres.add("7");
        semestres.add("8");
        semestres.add("9");
        spnSemestre.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, semestres));

        ArrayList<String> carrera= new ArrayList<>();
        carrera.add("Licenciatura en Administración");
        carrera.add("Contador Público");
        carrera.add("Ingeniería Industrial");
        carrera.add("Ingeniería Informática");
        carrera.add("Licenciatura en Biología");
        carrera.add("Ingeniería Bioquímica");
        carrera.add("Ingeniería Química");
        carrera.add("Ingeniería en Gestión Empresarial");
        carrera.add("Ingeniería en Innovación Agrícola Sustentable");
        carrera.add("Ingeniería Mecatrónica");
        carrera.add("Ingeniería Electrónica");
        carrera.add("Ingeniería Electromecánica");
        carrera.add("Arquitectura");
        carrera.add("Ingeniería en Industrias Alimentarias");
        spnCarrera.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, carrera));

        spnTaller.setSelection(taller.indexOf(boleto.getTaller()));
        spnTalla.setSelection(tallas.indexOf(boleto.getTalla()));
        spnSemestre.setSelection(semestres.indexOf(boleto.getSemestre()));
        spnCarrera.setSelection(carrera.indexOf(boleto.getCarrera()));

        spnTaller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                if (spn.getItemAtPosition(posicion).toString().equals("-Pago pendiente-")) {
                    txtDisponible.setText("-");
                } else {
                    int encontrados = 0;
                    for (int i = 0; i < Administrador.lista_Compradores.size(); i++) {
                        if (Administrador.lista_Compradores.get(i).getTaller().equals(spn.getItemAtPosition(posicion).toString())) {
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

        tilNombre.setHint("Nombre: " + boleto.getNombre());
        tilNControl.setHint("Número de control: " + String.valueOf(boleto.getNumero_Control()));

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!txtNombre.getText().toString().isEmpty()) {
                        Administrador.referenciaCompradores.child(boleto.getUID()).child("nombre").setValue(txtNombre.getText().toString().trim());
                    }
                    if (!txtNControl.getText().toString().isEmpty()) {
                        Administrador.referenciaCompradores.child(boleto.getUID()).child("numero_Control").setValue(txtNControl.getText().toString().trim());
                    }
                    if (!spnTalla.getSelectedItem().toString().equals(boleto.getTalla())) {
                        Administrador.referenciaCompradores.child(boleto.getUID()).child("talla").setValue(spnTalla.getSelectedItem().toString());
                    }
                    if (!spnSemestre.getSelectedItem().toString().equals(boleto.getSemestre())) {
                        Administrador.referenciaCompradores.child(boleto.getUID()).child("semestre").setValue(spnSemestre.getSelectedItem().toString());
                    }
                    if (!spnCarrera.getSelectedItem().toString().equals(boleto.getCarrera())) {
                        Administrador.referenciaCompradores.child(boleto.getUID()).child("carrera").setValue(spnCarrera.getSelectedItem().toString());
                    }
                    if (!spnTaller.getSelectedItem().toString().equals(boleto.getTaller())) {
                        if (Disponibles > 0 || !spnTaller.getSelectedItem().toString().equals("-Pago pendiente-")) {
                            Administrador.referenciaCompradores.child(boleto.getUID()).child("taller").setValue(spnTaller.getSelectedItem().toString());
                        }
                    }
                    finish();
            }
        });
    }
}
