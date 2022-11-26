package com.g104g5.appciclo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private Button btnProduct1,btnProduct2,btnProduct3;
    private TextView textProduct1, textProduct2, textProduct3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnProduct1 = (Button) findViewById(R.id.btnProduct1);
        btnProduct2 = (Button) findViewById(R.id.btnProduct2);
        btnProduct3 = (Button) findViewById(R.id.btnProduct3);

        textProduct1 = (TextView) findViewById(R.id.textProduct1);
        textProduct2 = (TextView) findViewById(R.id.textProduct2);
        textProduct3 = (TextView) findViewById(R.id.textProduct3);

        btnProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Productos.class);
                intent.putExtra("title",textProduct1.getText().toString());
                intent.putExtra("imageCode",R.drawable.product1);
                intent.putExtra("description","Blue Jeans product description");
                intent.putExtra("price","21 USD");
                startActivity(intent);
            }
        });

        btnProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Productos.class);
                intent.putExtra("title",textProduct2.getText().toString());
                intent.putExtra("imageCode",R.drawable.product2);
                intent.putExtra("description","Black Shoes product description");
                intent.putExtra("price","45 USD");
                startActivity(intent);
            }
        });

        btnProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Productos.class);
                intent.putExtra("title",textProduct3.getText().toString());
                intent.putExtra("imageCode",R.drawable.product3);
                intent.putExtra("description","White T-shirt product description");
                intent.putExtra("price","10 USD");
                startActivity(intent);
            }
        });
    }
}