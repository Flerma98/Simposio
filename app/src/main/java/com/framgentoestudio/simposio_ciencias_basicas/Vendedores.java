package com.framgentoestudio.simposio_ciencias_basicas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Vendedores extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static FirebaseAuth Auth = FirebaseAuth.getInstance();
    public static ArrayList<Compradores> lista_Compradores_Todos= new ArrayList<>();
    public static ArrayList<Compradores> lista_Compradores= new ArrayList<>();

    static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static final DatabaseReference ref = database.getReference(FireBaseReference.REFERENCIAVENDEDORES);
    static final DatabaseReference myRef = database.getReference(FireBaseReference.REFERENCIACOMPRADORES);

    public static ArrayList<String> tallas= new ArrayList<>();
    public static ArrayList<String> semestres= new ArrayList<>();
    public static ArrayList<String> carrera= new ArrayList<>();
    public static ArrayList<String> taller= new ArrayList<>();


    public static Datos_Vendedores vendedora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_vendedores);

        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Datos_Vendedores vendedor = objSnaptshot.getValue(Datos_Vendedores.class);
                        if(Auth.getUid().equals(vendedor.getUID())) {
                            vendedora = new Datos_Vendedores(vendedor.getUID(), vendedor.getNombre(), vendedor.getNumero(), vendedor.getCorreo(), vendedor.getNControl(), vendedor.getNIP());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                    tallas.clear();
                    tallas.add("XS");
                    tallas.add("S");
                    tallas.add("M");
                    tallas.add("L");
                    tallas.add("XL");
                    tallas.add("XXL");

                    semestres.clear();
                    semestres.add("1");
                    semestres.add("2");
                    semestres.add("3");
                    semestres.add("4");
                    semestres.add("5");
                    semestres.add("6");
                    semestres.add("7");
                    semestres.add("8");
                    semestres.add("9");

                    carrera.clear();
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

                    taller.clear();
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

                    FloatingActionButton fabAgregar= findViewById(R.id.fab_agregar_comprador);
                    fabAgregar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Vendedores.vendedora==null){

                            }else {
                                startActivity(new Intent(getApplicationContext(), AgregarCompradores.class));
                            }
                        }
                    });
                    return;
        } catch (Exception e) {
            Toast.makeText(Vendedores.this, "Ocurrió un error intentelo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragmento_vendedor tab1 = new fragmento_vendedor();
                    return tab1;
                case 1:
                     fragmento_todos tab2 = new fragmento_todos();
                    return tab2;
                case 2:
                    Evento tab3 = new Evento();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount () {
            // Show 4 total pages.
            return 3;
        }
    }

    public static int getIndexByname(String pName)
    {
        for(Compradores _item : com.framgentoestudio.simposio_ciencias_basicas.adapter_rv_compradores.compradores_filtrados)
        {
            if(_item.getUID().equals(pName))
                return com.framgentoestudio.simposio_ciencias_basicas.adapter_rv_compradores.compradores_filtrados.indexOf(_item);
        }
        return -1;
    }
}
