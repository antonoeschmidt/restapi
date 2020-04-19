package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.WriteResult;
import com.healthylife.restapi.model.TestObject;
import com.healthylife.restapi.model.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public String postData (User user) throws ExecutionException, InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUID());
//        ApiFuture<Void> apiFuture =
        ref.setValueAsync(user);
//        return apiFuture.get().toString();
        return "posted";
    }

    public String testPost(TestObject testObject) throws  ExecutionException, InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(testObject.getTestName());
        ref.setValueAsync(testObject);


        return "posted";
    }



}
