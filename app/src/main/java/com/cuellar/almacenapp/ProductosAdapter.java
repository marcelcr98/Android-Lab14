package com.cuellar.almacenapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    private static final String TAG = ProductosAdapter.class.getSimpleName();

    private List<Producto> productos;

    public ProductosAdapter(){
        this.productos = new ArrayList<>();
    }

    public void setProductos(List<Producto> productos){
        this.productos = productos;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        TextView precioText;

        ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            precioText = itemView.findViewById(R.id.precio_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Context context = viewHolder.itemView.getContext();

        Producto producto = this.productos.get(position);

        viewHolder.nombreText.setText(producto.getNombre());
        viewHolder.precioText.setText(NumberFormat.getCurrencyInstance(new Locale("es", "PE")).format(producto.getPrecio()));

        String url = ApiService.API_BASE_URL + "/productos/images/" + producto.getImagen();
        Picasso.with(context).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

}
