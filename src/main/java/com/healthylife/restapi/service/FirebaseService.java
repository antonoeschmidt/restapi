package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.WriteResult;
import com.healthylife.restapi.model.Pupil;
import com.healthylife.restapi.model.TestObject;
import com.healthylife.restapi.model.User;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
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

    public String testPost(JSONObject json) throws  ExecutionException, InterruptedException {
        String username = json.getString("_username");
        System.out.println(username);


        // Pupil pupil = new Pupil()

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(json.getString("_uid"));
        ref.setValueAsync(json);

        return json.toString();
    }



}
