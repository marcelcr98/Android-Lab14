package com.cuellar.almacenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView productosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productosList = findViewById(R.id.recyclerview);
        productosList.setLayoutManager(new LinearLayoutManager(this));

        productosList.setAdapter(new ProductosAdapter());

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        service.getProductos().enqueue(new Callback<List<Producto>>() {

            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                try {

                    if (response.isSuccessful()) {

                        List<Producto> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        ProductosAdapter adapter = (ProductosAdapter) productosList.getAdapter();
                        adapter.setProductos(productos);
                        adapter.notifyDataSetChanged();

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private static final int REQUEST_REGISTER_FORM = 100;

    public void showRegister(View view){
        startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_REGISTER_FORM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REGISTER_FORM) {
            initialize();   // refresh data from rest service
        }
    }


}
