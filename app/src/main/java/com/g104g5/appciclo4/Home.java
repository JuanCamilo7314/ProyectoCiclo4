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
import com.g104g5.appciclo4.BD.DBFirebase;
import com.g104g5.appciclo4.BD.DBHelper;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Services.ProductServices;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductServices productServices;
    private ListView listViewProducts;
    private ArrayList<Product> arrayProducts;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        arrayProducts = new ArrayList<>();
        try {
            //dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
            productServices = new ProductServices();
            Cursor cursor = dbHelper.getData();
            arrayProducts = productServices.cursorToArray(cursor);
            //if(arrayProducts.size()==0){
                //dbFirebase.syncData(dbHelper);
            //}
        }catch (Exception ex){

        }
        productAdapter = new ProductAdapter(this,arrayProducts);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        listViewProducts.setAdapter(productAdapter);

        dbFirebase.getData(productAdapter,arrayProducts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.actionAdd:
                intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
                return true;
            case R.id.actionMap:
                intent = new Intent(getApplicationContext(), Maps.class);
                ArrayList<String> latitudes =new ArrayList<>();
                ArrayList<String> longitudes =new ArrayList<>();

                for (int k=0; k<arrayProducts.size();k++){
                    latitudes.add(String.valueOf(arrayProducts.get(k).getLatitude()));
                    longitudes.add(String.valueOf(arrayProducts.get(k).getLongitude()));

                }
                intent.putStringArrayListExtra("latitudes",latitudes);
                intent.putStringArrayListExtra("longitudes",longitudes);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}