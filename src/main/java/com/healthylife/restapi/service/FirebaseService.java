package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.WriteResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public String postData (String data) throws ExecutionException, InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(data);
        //ApiFuture<Void> apiFuture =
        ref.setValueAsync("qwe");
        //return apiFuture.get().toString();
        return "posted";
    }



}
