package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.*;
import com.google.firestore.v1.WriteResult;
import com.healthylife.restapi.model.Pupil;
import com.healthylife.restapi.model.TestObject;
import com.healthylife.restapi.model.User;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

@Service
public class FirebaseService {

    public String postData(User user) throws ExecutionException, InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUID());
//        ApiFuture<Void> apiFuture =
        ref.setValueAsync(user);
//        return apiFuture.get().toString();
        return "posted";
    }

    public String testPost(JSONObject json) throws ExecutionException, InterruptedException {
        String username = json.getString("_username");
        System.out.println(username);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(json.getString("_uid"));
        System.out.println(ref);
        ref.setValueAsync(json);

        return json.toString();
    }

// virker med at få alle strings ned her:
//    public List<String> getAllUsers() throws InterruptedException {
//        List<String> users = new ArrayList<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference();
//
//        final Semaphore awaitResponse = new Semaphore(0);
//
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot d : dataSnapshot.getChildren()) {
//                        users.add(d.getKey());
//                    }
//                }
//                System.out.println(users.toString());
//                awaitResponse.release();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                awaitResponse.release();
//            }
//        });
//        awaitResponse.acquire();
//
//        return users;
//    }

    public List<Pupil> getAllUsers() throws InterruptedException {
        List<Pupil> users = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        final Semaphore awaitResponse = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        users.add(d.getValue(Pupil.class));
                    }
                }
                System.out.println(users.toString());
                awaitResponse.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                awaitResponse.release();
                System.out.println("Error");
            }
        });
        awaitResponse.acquire();

        return users;
    }
}
