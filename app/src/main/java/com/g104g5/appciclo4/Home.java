package com.g104g5.appciclo4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.g104g5.appciclo4.Adapters.ProductAdapter;
import com.g104g5.appciclo4.BD.DBHelper;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Services.ProductServices;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private DBHelper dbHelper;
    private ProductServices productServices;
    private ListView listViewProducts;
    private ArrayList<Product> arrayProducts;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {
            //byte[] img = "".getBytes();
            dbHelper = new DBHelper(this);
            //dbHelper.insertData("Blue Jean","Blue Jean Description","30",img);
            //dbHelper.insertData("Black Shoes","Black Shoes Description","34",img);
            //dbHelper.insertData("White T-Shirt","White T-Shirt Description","14",img);
            //dbHelper.insertData("Product 4","Description Product 4","31",img);
            //dbHelper.insertData("Product 5","Description Product 5","31",img);
            //dbHelper.insertData("Product 6","Description Product 6","31",img);
            productServices = new ProductServices();
            Cursor cursor = dbHelper.getData();
            arrayProducts = productServices.cursorToArray(cursor);
            //Toast.makeText(this, "Insert OK", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this, "Error Lectura Base de Datos", Toast.LENGTH_SHORT).show();
        }
        productAdapter = new ProductAdapter(this,arrayProducts);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        listViewProducts.setAdapter(productAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}