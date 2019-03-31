package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Evento extends Fragment {

    DatabaseReference referenciaEvento = Administrador.database.getReference(FireBaseReference.REFERENCIACOMPRADORES);
    RecyclerView rv_Evento;
    adapter_rv_evento adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenciaCompradores = database.getReference(FireBaseReference.REFERENCIACOMPRADORES);
    public static ArrayList<Compradores> lista= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_evento, container, false);
        rv_Evento= view.findViewById(R.id.rv_asistencia);
        rv_Evento.setLayoutManager(new GridLayoutManager(getContext(), 1));
            adapter = new adapter_rv_evento(lista, getContext(), rv_Evento);
        rv_Evento.setAdapter(adapter);
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_from_bottom);
        rv_Evento.setLayoutAnimation(layoutAnimationController);
        setHasOptionsMenu(true);

        try {
            referenciaCompradores.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista.clear();
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Compradores comprador = objSnaptshot.getValue(Compradores.class);
                        lista.add(comprador);
                    }
                    adapter.notifyDataSetChanged();
                    rv_Evento.scheduleLayoutAnimation();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        }catch (Exception e){}


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar_Asistencia.boleto= (com.framgentoestudio.simposio_ciencias_basicas.adapter_rv_evento.compradores_filtrados.get(rv_Evento.getChildAdapterPosition(v)));
                startActivity(new Intent(getContext(), Registrar_Asistencia.class));
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_administrador, menu);

        MenuItem searchItem= menu.findItem(R.id.busqueda);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir :
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Cerrar Sesión");
                dialogo1.setMessage("¿ Desea cerrar sesión ?");
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Administrador.Auth.signOut();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
