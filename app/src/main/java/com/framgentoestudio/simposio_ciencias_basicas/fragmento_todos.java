package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;

public class fragmento_todos extends Fragment {

    RecyclerView rv_Todos;
    adapter_rv_todos adapter_rv_todos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragmento_todos, container, false);

        rv_Todos= view.findViewById(R.id.rv_todos);
        rv_Todos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter_rv_todos = new adapter_rv_todos(Vendedores.lista_Compradores_Todos, getContext(), rv_Todos);
        rv_Todos.setAdapter(adapter_rv_todos);
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_from_bottom);
        rv_Todos.setLayoutAnimation(layoutAnimationController);

        setHasOptionsMenu(true);

        try {
            Vendedores.myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Vendedores.lista_Compradores_Todos.clear();
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Compradores comprador = objSnaptshot.getValue(Compradores.class);
                        Vendedores.lista_Compradores_Todos.add(comprador);
                    }
                    adapter_rv_todos.notifyDataSetChanged();
                    rv_Todos.scheduleLayoutAnimation();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        }catch (Exception e){ Toast.makeText(getContext(), "Ocurrió un error obteniendo los datos", Toast.LENGTH_SHORT).show();}

            adapter_rv_todos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Visualizar.compradora= (com.framgentoestudio.simposio_ciencias_basicas.adapter_rv_todos.compradores_filtrados.get(rv_Todos.getChildAdapterPosition(v)));
                    startActivity(new Intent(getContext(), Visualizar.class));
                }
            });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_salir, menu);

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
                adapter_rv_todos.getFilter().filter(newText);
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
                        Vendedores.Auth.signOut();
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
