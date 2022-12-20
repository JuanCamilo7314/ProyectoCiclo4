package com.g104g5.appciclo4.BD;

import static android.content.ContentValues.TAG;

import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase(){
        this.db=FirebaseFirestore.getInstance();
    }

    public void insertData(String name, String description, String price, byte[] image){

        Map<String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("description", description);
        product.put("price", price);

        // Add a new document with a generated ID
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
   }

   public void getData(){
       db.collection("products")
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d(TAG, document.getId() + " => " + document.getData());
                           }
                       } else {
                           Log.w(TAG, "Error getting documents.", task.getException());
                       }
                   }
               });
   }
}
