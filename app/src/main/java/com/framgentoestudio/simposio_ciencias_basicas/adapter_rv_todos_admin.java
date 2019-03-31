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
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class adapter_rv_todos_admin extends RecyclerView.Adapter<adapter_rv_todos_admin.CompradoresviewHolder> implements View.OnClickListener, Filterable {
    public static ArrayList<Compradores> compradores_source;
    public static ArrayList<Compradores> compradores_filtrados;
    private View.OnClickListener listener;
    private Context mContext;
    private View view;

    public adapter_rv_todos_admin(ArrayList<Compradores> compradores_source, Context mContext, View view) {
        this.compradores_source = compradores_source;
        this.compradores_filtrados = compradores_source;
        this.mContext = mContext;
        this.view = view;
    }


    @NonNull
    @Override
    public CompradoresviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Collections.sort(compradores_source, new Comparator<Compradores>() {
            @Override
            public int compare(Compradores p1, Compradores p2) {
                return new Integer(p1.getFolio()).compareTo(new Integer(p2.getFolio()));
            }
        });
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_todos_admin, viewGroup, false);
        CompradoresviewHolder holder= new CompradoresviewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CompradoresviewHolder compradoresviewHolder, int i) {
        view= compradoresviewHolder.itemView;
        final Compradores comprador= compradores_filtrados.get(i);
        compradoresviewHolder.txtNombre.setText(comprador.getNombre());
        compradoresviewHolder.txtNControl.setText(comprador.getNumero_Control());
        compradoresviewHolder.txtFolio.setText("N# " + comprador.getFolio());

        compradoresviewHolder.ivOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, compradoresviewHolder.ivOpciones);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_rv_compradores);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar:
                                Editar_Boleto.boleto= comprador;
                                mContext.startActivity(new Intent(mContext, Editar_Boleto.class));
                                return true;
                            case R.id.eliminar:
                                Administrador.referenciaCompradores.child(comprador.getUID()).removeValue();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
        compradoresviewHolder.txtVendedora.setText(comprador.getVendedora());
        StringTokenizer st = new StringTokenizer(comprador.getImportes(), ";");
        int importe= 0;
        while (st.hasMoreTokens()) {
            String Importe = st.nextToken();
            importe+= Integer.parseInt(Importe);
        }
        compradoresviewHolder.txtImporte.setText("$ " + importe);
    }

    @Override
    public int getItemCount() {
        return compradores_filtrados.size();
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
                    compradores_filtrados= compradores_source;
                }else{
                    ArrayList<Compradores> resultList= new ArrayList<>();
                    for(Compradores item: compradores_source){
                        if(item.getNombre().toLowerCase().contains(searchString.toLowerCase())){
                            resultList.add(item);
                        }
                    }
                    compradores_filtrados= resultList;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values= compradores_filtrados;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                compradores_filtrados= (ArrayList<Compradores>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class CompradoresviewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre, txtNControl, txtFolio, txtVendedora,txtImporte;
        ImageView ivOpciones;

        public CompradoresviewHolder(View itemView){
            super(itemView);
            txtNombre= itemView.findViewById(R.id.rv_Nombre);
            txtNControl= itemView.findViewById(R.id.rv_NControl);
            txtFolio= itemView.findViewById(R.id.rv_Folio);
            ivOpciones= itemView.findViewById(R.id.rv_Opciones);
            txtVendedora= itemView.findViewById(R.id.rv_Vendedora);
            txtImporte= itemView.findViewById(R.id.rv_Importe);
        }
    }
}
