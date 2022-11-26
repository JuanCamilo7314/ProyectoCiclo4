package com.g104g5.appciclo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Productos extends AppCompatActivity {
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

        Intent intentIn = getIntent();
        textProductTittle.setText(intentIn.getStringExtra("title"));
        int codeImage = intentIn.getIntExtra("imageCode",0);
        imageProduct.setImageResource(codeImage);
        textProductDescription.setText(intentIn.getStringExtra("description"));
        textPrice.setText(intentIn.getStringExtra("price"));

        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });
    }
}