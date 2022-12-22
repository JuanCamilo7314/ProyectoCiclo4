package com.g104g5.appciclo4.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.g104g5.appciclo4.BD.DBFirebase;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Home;
import com.g104g5.appciclo4.ProductForm;
import com.g104g5.appciclo4.Products;
import com.g104g5.appciclo4.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> arrayProducts;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }

    @Override
    public int getCount() {
        return this.arrayProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return this.arrayProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template, null);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imageProductTemplate);
        TextView textNameProduct = (TextView) view.findViewById(R.id.textNameTemplate);
        TextView textDescriptionProduct = (TextView) view.findViewById(R.id.textDescriptionTemplate);
        TextView textPriceProduct = (TextView) view.findViewById(R.id.textPriceTemplate);

        Button btnEditTemplate = (Button) view.findViewById(R.id.btnEditTemplate);
        Button btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteTemplate);

        Product product = arrayProducts.get(i);

        Glide.with(context).load(product.getImage())
                .override(150,150)
                .into(imgProduct);

        textNameProduct.setText(product.getName());
        textDescriptionProduct.setText(product.getDescription());
        int Col = product.getPrice() * 5000;
        int Usd = product.getPrice();
        String prices = "COP: "+Col+" - "+ "USD: "+Usd;
        textPriceProduct.setText(prices);
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Products.class);
                intent.putExtra("id",product.getId());
                intent.putExtra("name",product.getName());
                intent.putExtra("description",product.getDescription());
                intent.putExtra("price",String.valueOf(product.getPrice()));
                intent.putExtra("image",product.getImage());
                intent.putExtra("latitude",product.getLatitude());
                intent.putExtra("longitude",product.getLongitude());
                context.startActivity(intent);
            }
        });

        btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Delete Product?")
                        .setTitle("Confirm")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBFirebase dbFirebase = new DBFirebase();
                                dbFirebase.deleteById(product.getId());
                                Intent intent = new Intent(context.getApplicationContext(), Home.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        btnEditTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductForm.class);
                intent.putExtra("edit",true);
                intent.putExtra("id",product.getId());
                intent.putExtra("name",product.getName());
                intent.putExtra("description",product.getDescription());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("image",product.getImage());
                intent.putExtra("latitude",product.getLatitude());
                intent.putExtra("longitude",product.getLongitude());
                context.startActivity(intent);
            }
        });

        return view;
    }
}

