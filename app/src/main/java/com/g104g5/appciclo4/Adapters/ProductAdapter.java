package com.g104g5.appciclo4.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Products;
import com.g104g5.appciclo4.R;

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

        Product product = arrayProducts.get(i);

        byte[] image = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

        imgProduct.setImageBitmap(bitmap);
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
                intent.putExtra("id", String.valueOf(product.getId()));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
