package com.g104g5.appciclo4;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.g104g5.appciclo4.BD.DBFirebase;
import com.g104g5.appciclo4.BD.DBHelper;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Services.ProductServices;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProductForm extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductServices productServices;
    private Button btnFormProduct,btnGetFormProduct,btnDeleteFormProduct,btnUpdateFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imageFromProduct;
    ActivityResultLauncher<String> content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        btnGetFormProduct = (Button) findViewById(R.id.btnGetFormProduct);
        btnDeleteFormProduct = (Button) findViewById(R.id.btnDeleteFormProduct);
        btnUpdateFormProduct = (Button) findViewById(R.id.btnUpdateFormProduct);

        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        editIdFormProduct = (EditText) findViewById(R.id.editIdFormProduct);

        imageFromProduct = (ImageView) findViewById(R.id.imageFormProduct);

        try {
            productServices = new ProductServices();
            dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();

        }catch (Exception e){
            Log.e("DB",e.toString());
        }

        content = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imageFromProduct.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });

        imageFromProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });



        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbHelper.insertData(
                dbFirebase.insertData(
                        editNameFormProduct.getText().toString(),
                        editDescriptionFormProduct.getText().toString(),
                        editPriceFormProduct.getText().toString(),
                        productServices.imageViewToByte(imageFromProduct)
                );
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });

        btnGetFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if (id.compareTo("")!=0){
                    ArrayList<Product> list = productServices.cursorToArray(dbHelper.getDataById(id));
                    if (list.size() !=0){
                        Product product = list.get(0);
                        editNameFormProduct.setText(product.getName());
                        editDescriptionFormProduct.setText(product.getDescription());
                        editPriceFormProduct.setText(String.valueOf(product.getPrice()));
                        imageFromProduct.setImageBitmap(productServices.byteToBitmap(product.getImage()));
                    }else{
                        Toast.makeText(getApplicationContext(),"Not exist",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Enter id",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnDeleteFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if (id.compareTo("")!=0){
                    dbHelper.deleteDataById(id);
                    clean();
                }else{
                    Toast.makeText(getApplicationContext(),"Enter id",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdateFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if (id.compareTo("")!=0){
                    dbHelper.updateDataById(
                            id,
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            editPriceFormProduct.getText().toString(),
                            productServices.imageViewToByte(imageFromProduct)
                            );
                    clean();
                }else{
                    Toast.makeText(getApplicationContext(),"Enter id",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clean(){
        editNameFormProduct.setText("");
        editDescriptionFormProduct.setText("");
        editPriceFormProduct.setText("");
        imageFromProduct.setImageResource(R.drawable.addimage);
    }

}