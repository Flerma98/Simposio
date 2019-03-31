package com.framgentoestudio.simposio_ciencias_basicas;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Registrar_Asistencia extends AppCompatActivity {

    public static Compradores boleto;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(FireBaseReference.REFERENCIACOMPRADORES);
    TextView txtFolio, txtNombre, txtNControl, txtTalla, txtSemestre, txtCarrera, txtTaller, txtVendedora, txtEntra, txtSale, txtAsistencia, txtHoras;
    Button btnAsistir, btnBorrar;
    Asistencia este;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar__asistencia);

        txtFolio= findViewById(R.id.vz_folio);
        txtNombre= findViewById(R.id.vz_nombre);
        txtNControl= findViewById(R.id.vz_ncontrol);
        txtTalla= findViewById(R.id.vz_talla);
        txtSemestre= findViewById(R.id.vz_semestre);
        txtCarrera= findViewById(R.id.vz_carrera);
        txtTaller= findViewById(R.id.vz_taller);
        txtVendedora= findViewById(R.id.vz_vendedora);
        btnAsistir= findViewById(R.id.btnAsistencia);
        txtEntra= findViewById(R.id.txt_asistencia_entro);
        txtSale= findViewById(R.id.txt_asistencia_salio);
        txtAsistencia= findViewById(R.id.txt_aistencia_estado);
        txtHoras= findViewById(R.id.txt_asistencia_horas);
        btnBorrar= findViewById(R.id.btnasistencia_borrar);

        txtFolio.setText("Folio: #" + boleto.getFolio());
        txtNombre.setText("Nombre: " + boleto.getNombre());
        txtNControl.setText("Número de Control: " + boleto.getNumero_Control());
        txtTalla.setText("Talla: " + boleto.getTalla());
        txtSemestre.setText("Semestre: " + boleto.getSemestre());
        txtCarrera.setText("Carrera: " + boleto.getCarrera());
        txtTaller.setText("Taller: " + boleto.getTaller());
        txtVendedora.setText("Vendedor(A): " + boleto.getVendedora());

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(boleto.getUID()).child(FireBaseReference.REFERENCIAEVENTO).removeValue();
                finish();
            }
        });

        reference.child(boleto.getUID()).child(FireBaseReference.REFERENCIAEVENTO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    este = objSnaptshot.getValue(Asistencia.class);
                    btnBorrar.setVisibility(View.VISIBLE);
                    StringTokenizer st = new StringTokenizer(este.getDatos(), ",;");
                    String Entra= "";
                    String Sale = "";
                    String texEntra= "";
                    String texSale = "";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
                    long diferencia = 0;
                    while (st.hasMoreTokens()) {
                        Entra= st.nextToken();
                        try {
                            Sale = st.nextToken();
                        }catch (Exception e){Sale= "-";}
                        texEntra = texEntra + "\n" + Entra;
                        texSale = texSale + "\n" + Sale;

                        try {
                            Date horai= dateFormat.parse(Entra);
                            Date horaf= dateFormat.parse(Sale);
                            diferencia+= horaf.getTime() - horai.getTime();
                        } catch (ParseException e) {
                           e.printStackTrace();
                        }
                    }

                    long segsMilli = 1000;
                    long minsMilli = segsMilli * 60;
                    long horasMilli = minsMilli * 60;
                    long diasMilli = horasMilli * 24;

                    long diasTranscurridos = diferencia / diasMilli;
                    diferencia = diferencia % diasMilli;
                    long horasTranscurridos = diferencia / horasMilli;
                    diferencia = diferencia % horasMilli;
                    long minutosTranscurridos = diferencia / minsMilli;
                    diferencia = diferencia % minsMilli;
                    long segsTranscurridos = diferencia / segsMilli;
                    txtHoras.setText("Días: " + diasTranscurridos + "\nHoras: " + horasTranscurridos + "\nMinutos: " + minutosTranscurridos+ "\nSegundos: " + segsTranscurridos);
                    txtEntra.setText(texEntra);
                    txtSale.setText(texSale);
                    if(este.getEstado().equals("- Dentro -")) {
                        btnAsistir.setText("Registrar Salida");
                        txtAsistencia.setText("Dentro del evento");
                    txtAsistencia.setTextColor(Color.parseColor("#ffffff"));
                    txtAsistencia.setBackgroundColor(Color.parseColor("#0AAD34"));
                    }
                    if(este.getEstado().equals("- Fuera -")) {
                        btnAsistir.setText("Registrar Entrada");
                        txtAsistencia.setText("Fuera del evento");
                        txtAsistencia.setTextColor(Color.parseColor("#ffffff"));
                        txtAsistencia.setBackgroundColor(Color.parseColor("#000000"));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAsistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asistencia asistencia= new Asistencia();
                if(este!=null) {
                    if (este.getEstado().equals("- Dentro -")) {
                        asistencia.setEstado("- Fuera -");
                        asistencia.setDatos(este.getDatos() + getFechaHora() + ";");
                    } else {
                        asistencia.setEstado("- Dentro -");
                        asistencia.setDatos(este.getDatos() + getFechaHora() + ",");
                    }
                }else{
                    asistencia.setEstado("- Dentro -");
                    asistencia.setDatos(getFechaHora() + ",");
                }
                reference.child(boleto.getUID()).child(FireBaseReference.REFERENCIAEVENTO).child("Datos").setValue(asistencia);
            }
        });
    }

    public static String getHora() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        return formattedDate;
    }

    public static String getFecha(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getFechaHora(){
        return getHora() + " - " + getFecha();
    }
}
