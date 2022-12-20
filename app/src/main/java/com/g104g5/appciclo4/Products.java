package com.g104g5.appciclo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.g104g5.appciclo4.BD.DBHelper;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Services.ProductServices;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    private DBHelper dbHelper;
    private ProductServices productServices;
    private Button btnToHome;
    private TextView textProductTittle, textProductDescription, textPrice;
    private ImageView imageProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        btnToHome = (Button) findViewById(R.id.btnToHome);
        textProductTittle = (TextView) findViewById(R.id.textProductTitle);
        textProductDescription = (TextView) findViewById(R.id.textProductDescription);
        imageProduct = (ImageView) findViewById(R.id.imageProduct);
        textPrice = (TextView) findViewById(R.id.textPrice);
        dbHelper = new DBHelper(this);
        productServices = new ProductServices();

        Intent intentIn = getIntent();
        String id = intentIn.getStringExtra("id");
        ArrayList<Product> list = productServices.cursorToArray(dbHelper.getDataById(id));
        Product product = list.get(0);

        textProductTittle.setText(product.getName());
        textProductDescription.setText(product.getDescription());
        textPrice.setText(String.valueOf(product.getPrice()));
        imageProduct.setImageBitmap(productServices.byteToBitmap(product.getImage()));

        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });
    }
}