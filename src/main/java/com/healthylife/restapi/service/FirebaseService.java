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
        Pupil pupil = JSONtoPupil(json);

        String username = json.getString("_username");
        System.out.println(username);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(json.getString("_uid"));
        ref.setValueAsync(pupil);

        return json.toString();
    }

    public Pupil JSONtoPupil(JSONObject json){
        Pupil pupil = new Pupil();
        pupil.setUsername(json.getString("_username"));
        pupil.setPassword(json.getString("_password"));
        pupil.setUID(json.getString("_uid"));

        return pupil;
    }



}
