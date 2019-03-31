package com.framgentoestudio.simposio_ciencias_basicas;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_rv_vendedores extends RecyclerView.Adapter<adapter_rv_vendedores.VendedoresviewHolder> implements View.OnClickListener, Filterable {
    public static ArrayList<Datos_Vendedores> vendedores_source;
    public static ArrayList<Datos_Vendedores> vendedores_filtrados;
    private View.OnClickListener listener;
    private Context mContext;
    private View view;

    public adapter_rv_vendedores(ArrayList<Datos_Vendedores> vendedores_source, Context mContext, View view) {
        this.vendedores_source = vendedores_source;
        this.vendedores_filtrados = vendedores_source;
        this.mContext = mContext;
        this.view = view;
    }


    @NonNull
    @Override
    public VendedoresviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_vendedores, viewGroup, false);
        VendedoresviewHolder holder= new VendedoresviewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VendedoresviewHolder vendedoresviewHolder, int i) {
        view= vendedoresviewHolder.itemView;
        final Datos_Vendedores comprador= vendedores_filtrados.get(i);
         vendedoresviewHolder.txtNombre.setText(comprador.getNombre());
         vendedoresviewHolder.txtNControl.setText(String.valueOf(comprador.getNControl()));
         vendedoresviewHolder.txtCelular.setText(String.valueOf(comprador.getNumero()));
        vendedoresviewHolder.ivOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, vendedoresviewHolder.ivOpciones);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_rv_compradores);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar:
                                Editar_Vendedor.vendedor= comprador;
                                mContext.startActivity(new Intent(mContext, Editar_Vendedor.class));
                                return true;
                            case R.id.eliminar:
                                Administrador.referenciaVendedores.child(comprador.getUID()).removeValue();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendedores_filtrados.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString().trim();
                if(searchString.isEmpty()){
                    vendedores_filtrados= vendedores_source;
                }else{
                    ArrayList<Datos_Vendedores> resultList= new ArrayList<>();
                    for(Datos_Vendedores item: vendedores_source){
                        if(item.getNombre().toLowerCase().contains(searchString.toLowerCase())){
                            resultList.add(item);
                        }
                    }
                    vendedores_filtrados= resultList;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values= vendedores_filtrados;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                vendedores_filtrados= (ArrayList<Datos_Vendedores>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class VendedoresviewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre, txtNControl, txtCelular;
        ImageView ivOpciones;

        public VendedoresviewHolder(View itemView){
            super(itemView);
            txtNombre= itemView.findViewById(R.id.rv_Nombre_ad);
            txtNControl= itemView.findViewById(R.id.rv_NControl_ad);
            txtCelular= itemView.findViewById(R.id.rv_Celular_Ad);
            ivOpciones= itemView.findViewById(R.id.rv_Opciones_ad);
        }
    }
}
