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
import com.google.firebase.database.ValueEventListener;

public class listaCompradores extends Fragment {

    RecyclerView rv_Compradores;
    adapter_rv_todos_admin adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_compradores, container, false);

        rv_Compradores= view.findViewById(R.id.rv_listacompradores);
        rv_Compradores.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new adapter_rv_todos_admin(Administrador.lista_Compradores, getContext(), rv_Compradores);
        rv_Compradores.setAdapter(adapter);
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_from_bottom);
        rv_Compradores.setLayoutAnimation(layoutAnimationController);

        setHasOptionsMenu(true);

        try {
            Administrador.referenciaCompradores.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Administrador.lista_Compradores.clear();
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Compradores comprador = objSnaptshot.getValue(Compradores.class);
                        Administrador.lista_Compradores.add(comprador);
                    }
                    adapter.notifyDataSetChanged();
                    rv_Compradores.scheduleLayoutAnimation();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        }catch (Exception e){ Toast.makeText(getContext(), "Ocurrió un error obteniendo los datos", Toast.LENGTH_SHORT).show();}

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visualizar.compradora= (com.framgentoestudio.simposio_ciencias_basicas.adapter_rv_todos_admin.compradores_filtrados.get(rv_Compradores.getChildAdapterPosition(v)));
                startActivity(new Intent(getContext(), Visualizar.class));
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
