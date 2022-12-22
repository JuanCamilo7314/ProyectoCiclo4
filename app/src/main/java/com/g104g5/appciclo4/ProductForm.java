package com.g104g5.appciclo4;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.g104g5.appciclo4.BD.DBFirebase;
import com.g104g5.appciclo4.BD.DBHelper;
import com.g104g5.appciclo4.Entities.Product;
import com.g104g5.appciclo4.Services.ProductServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class ProductForm extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductServices productServices;
    private Button btnFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct;
    private ImageView imageFromProduct;
    private TextView textLatitudeFormProduct, textLongitudeFormProduct;
    private MapView map;
    private MapController mapController;

    private final int GALLERY_INTENT = 1;
    private String urlImage = "";
    private StorageReference storageReference;
    private ActivityResultLauncher<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        storageReference = FirebaseStorage.getInstance().getReference();


        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);

        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);


        imageFromProduct = (ImageView) findViewById(R.id.imageFormProduct);

        textLatitudeFormProduct = (TextView) findViewById(R.id.textLatitudeFormProduct);
        textLongitudeFormProduct = (TextView) findViewById(R.id.textLongitudeFormProduct);

        Intent intentIn = getIntent();
        Boolean edit = intentIn.getBooleanExtra("edit",false);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map = (MapView) findViewById(R.id.mapForm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();
        GeoPoint colombia = new GeoPoint(4.570868, -74.297333);
        mapController.setCenter(colombia);
        mapController.setZoom(8);
        map.setMultiTouchControls(true);

        if (edit){
            urlImage = intentIn.getStringExtra("image");
            Glide.with(ProductForm.this).load(intentIn.getStringExtra("image"))
                    .override(150,150)
                    .into(imageFromProduct);

            btnFormProduct.setText("Update");
            editNameFormProduct.setText(intentIn.getStringExtra("name"));
            editDescriptionFormProduct.setText(intentIn.getStringExtra("description"));
            editPriceFormProduct.setText(String.valueOf(intentIn.getIntExtra("price",0)));

            textLatitudeFormProduct.setText(String.valueOf(intentIn.getDoubleExtra("latitude",0.0)));
            textLongitudeFormProduct.setText(String.valueOf(intentIn.getDoubleExtra("longitude",0.0)));

            GeoPoint geoPoint = new GeoPoint(intentIn.getDoubleExtra("latitude",0.0),intentIn.getDoubleExtra("longitude",0.0));
            Marker marker = new Marker(map);
            marker.setId("String");
            for (int i = 0; i < map.getOverlays().size(); i++) {
                Overlay overlay = map.getOverlays().get(i);
                if (overlay instanceof Marker && ((Marker) overlay).getId().equals("String")) {
                    map.getOverlays().remove(overlay);
                }
            }
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                textLatitudeFormProduct.setText(p.getLatitude() + "");
                textLongitudeFormProduct.setText(p.getLongitude() + "");
                Marker marker = new Marker(map);
                marker.setId("String");
                for (int i = 0; i < map.getOverlays().size(); i++) {
                    Overlay overlay = map.getOverlays().get(i);
                    if (overlay instanceof Marker && ((Marker) overlay).getId().equals("String")) {
                        map.getOverlays().remove(overlay);
                    }
                }
                marker.setPosition(p);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(marker);
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);

        try {
            productServices = new ProductServices();
            dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();

        } catch (Exception e) {
            Log.e("DB", e.toString());
        }

        content = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        Uri uri = result;
                        StorageReference filepath = storageReference.child("images").child(uri.getLastPathSegment());
                        filepath.putFile(uri)
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        Toast.makeText(ProductForm.this, "Image uploaded complete", Toast.LENGTH_SHORT).show();
                                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Uri downloadurl = uri;
                                                urlImage = String.valueOf(downloadurl);
                                                Glide.with(ProductForm.this).load(downloadurl)
                                                        .override(150,150)
                                                        .into(imageFromProduct);
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProductForm.this, "Uploaded image error", Toast.LENGTH_SHORT).show();
                                    }
                                });
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

                Product product = new Product(
                        editNameFormProduct.getText().toString(),
                        editDescriptionFormProduct.getText().toString(),
                        Integer.parseInt(editPriceFormProduct.getText().toString()),
                        urlImage,
                        Double.parseDouble(textLatitudeFormProduct.getText().toString()),
                        Double.parseDouble(textLongitudeFormProduct.getText().toString())
                        //productServices.imageViewToByte(imageFromProduct)
                );
                //dbHelper.insertData(product);
                if(edit){

                    product.setImage(urlImage);
                    product.setId(intentIn.getStringExtra("id"));
                    dbFirebase.updateDataById(product);

                }else{
                    dbFirebase.insertData(product);
                }

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
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