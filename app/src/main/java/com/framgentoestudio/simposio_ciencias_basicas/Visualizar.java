package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Visualizar extends AppCompatActivity {

    TextView txtFolio, txtNombre, txtNControl, txtTalla, txtSemestre, txtCarrera, txtTaller, txtVendedora, txtImporte, txtFecha, txtFaltante;
    LinearLayout lyPagar;
    Spinner spnTaller;
    Button btnPagar;
    EditText txtPagar;
    TextInputLayout tilPagar;
    int importe;

    public static Compradores compradora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_visualizar);
        setTitle("Datos de boleto");

        txtFolio= findViewById(R.id.vz_folio);
        txtNombre= findViewById(R.id.vz_nombre);
        txtNControl= findViewById(R.id.vz_ncontrol);
        txtTalla= findViewById(R.id.vz_talla);
        txtSemestre= findViewById(R.id.vz_semestre);
        txtCarrera= findViewById(R.id.vz_carrera);
        txtTaller= findViewById(R.id.vz_taller);
        txtVendedora= findViewById(R.id.vz_vendedora);
        txtImporte= findViewById(R.id.vz_importe);
        txtFecha= findViewById(R.id.vz_fecha);
        lyPagar= findViewById(R.id.lyt_Pago);
        txtFaltante= findViewById(R.id.vz_faltante);
        spnTaller= findViewById(R.id.vz_spn_taller);
        btnPagar= findViewById(R.id.btnPagar);
        txtPagar= findViewById(R.id.txt_Pagar);
        tilPagar= findViewById(R.id.til_Pagar);

        txtFolio.setText("Folio: #" + compradora.getFolio());
        txtNombre.setText("Nombre: " + compradora.getNombre());
        txtNControl.setText("Número de Control: " + compradora.getNumero_Control());
        txtTalla.setText("Talla: " + compradora.getTalla());
        txtSemestre.setText("Semestre: " + compradora.getSemestre());
        txtCarrera.setText("Carrera: " + compradora.getCarrera());
        txtTaller.setText("Taller: " + compradora.getTaller());
        txtVendedora.setText("Vendedor(A): " + compradora.getVendedora());

        StringTokenizer importes = new StringTokenizer(compradora.getImportes(), ";");
        importe = 0;
        String importesI= "-Importes-";
        while (importes.hasMoreTokens()) {
            String Importe = importes.nextToken();
            importesI+= "\n\n$" + Importe;
            importe+= Integer.parseInt(Importe);
        }
        txtImporte.setText(importesI);
        StringTokenizer fechas = new StringTokenizer(compradora.getFechas(), ";");
        String fecha= "-Fechas-";
        while (fechas.hasMoreTokens()) {
            String Fecha = fechas.nextToken();
                fecha+= "\n\n" + Fecha;
        }
        txtFecha.setText(fecha);
        if(importe==600){
            lyPagar.setVisibility(View.INVISIBLE);
        }
        txtFaltante.setText("Cantidad faltante a pagar: $" + String.valueOf(600 - importe));

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
        spnTaller.setSelection(taller.indexOf(compradora.getTaller()));

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPagar.getText().toString().isEmpty()){
                    tilPagar.setError("Llena este campo");
                }else{
                    int gg= (importe) + Integer.parseInt(txtPagar.getText().toString());
                    if (gg>600){
                        Toast.makeText(Visualizar.this, "Monto No Permitido", Toast.LENGTH_SHORT).show();
                    }else{
                        String Importes= compradora.getImportes() + ";" + txtPagar.getText().toString();
                        String Fechas= compradora.getFechas() + ";" + AgregarCompradores.getFechaHora();
                        String Taller= "";
                        if(gg==600){
                            if(spnTaller.getSelectedItemPosition()==0){
                                Toast.makeText(Visualizar.this, "Selecciona un taller", Toast.LENGTH_SHORT).show();
                            }else{
                                Taller= spnTaller.getSelectedItem().toString();
                                Vendedores.myRef.child(compradora.getUID()).child("importes").setValue(Importes);
                                Vendedores.myRef.child(compradora.getUID()).child("fechas").setValue(Fechas);
                                Vendedores.myRef.child(compradora.getUID()).child("taller").setValue(Taller);
                                finish();
                            }
                        }else{
                            Taller= spnTaller.getItemAtPosition(0).toString();
                            Vendedores.myRef.child(compradora.getUID()).child("importes").setValue(Importes);
                            Vendedores.myRef.child(compradora.getUID()).child("fechas").setValue(Fechas);
                            Vendedores.myRef.child(compradora.getUID()).child("taller").setValue(Taller);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
