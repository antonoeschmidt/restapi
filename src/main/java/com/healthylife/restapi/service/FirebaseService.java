package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.*;
import com.google.firestore.v1.WriteResult;
import com.healthylife.restapi.model.*;
import kong.unirest.json.JSONObject;
import org.apache.http.client.HttpResponseException;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

@Service
public class FirebaseService {

    private boolean userDeleted;

    //TODO: Kan vi slette?
    public String postData(User user) throws ExecutionException, InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(user.getUID());
//        ApiFuture<Void> apiFuture =
        ref.setValueAsync(user);
//        return apiFuture.get().toString();
        return "posted";
    }

    public String testPost(JSONObject json) throws ExecutionException, InterruptedException {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(json.getString("uid"));
        System.out.println(ref);
        System.out.println(json);
        ref.setValueAsync(JSONtoPupil(json));

        return json.toString();
    }

    public String createUser(JSONObject json) throws FirebaseAuthException {

        //Creates object in firebase with only email to get a from firebase
        //then inserts that uid into user and updates
        Pupil user = JSONtoPupil(json);
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.getUsername())
                .setPassword(user.getPassword());
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
        user.setUID(userRecord.getUid());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user.getUID());
        myRef.setValueAsync(user);
        return json.toString();
    }

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

    //Den er fin
    public boolean deleteUser(String uid) throws InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        final Semaphore awaitResponse = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(uid).exists()) {
                    System.out.println("User NOT Deleted");
                    userDeleted = false;
                } else {
                    System.out.println("User Deleted");
                    userDeleted = true;
                }
                awaitResponse.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        };
        ref.child(uid).removeValue(completionListener);
        awaitResponse.acquire();
        return userDeleted;
    }


    //TODO:Stadig finde ud af om kan gøres uden hardcode
    public Pupil JSONtoPupil(JSONObject json) {

        String s = json.toString();

        Pupil pupil = new Pupil();

        pupil.setUsername(json.getString("username"));
        pupil.setPassword(json.getString("password"));
        pupil.setUID(json.getString("uid"));
        pupil.setFirstTimeLoggedIn(json.getBoolean("firstTimeLoggedIn"));

        //Kunne ikke få ting fra nested json objekter så lavede et nyt
        Physique physique = new Physique();
        JSONObject jsonObjectPhysique = json.getJSONObject("physique");
        physique.setHeight(jsonObjectPhysique.getDouble("height"));
        physique.setWeight(jsonObjectPhysique.getDouble("weight"));
        physique.setActivityLevel(jsonObjectPhysique.getInt("activityLevel"));
        pupil.setPhysique(physique);

        PersonalInfo personalInfo = new PersonalInfo();
        JSONObject jsonObjectPersonalInfo = json.getJSONObject("personalInfo");
        personalInfo.setFirstName(jsonObjectPersonalInfo.getString("firstName"));
        personalInfo.setLastName(jsonObjectPersonalInfo.getString("lastName"));
        personalInfo.setGender(jsonObjectPersonalInfo.getString("gender"));
        personalInfo.setDateOfBirth(jsonObjectPersonalInfo.getLong("dateOfBirth"));
        personalInfo.setZipCode(jsonObjectPersonalInfo.getInt("zipCode"));
        pupil.setPersonalInfo(personalInfo);

        Experience experience = new Experience();
        JSONObject jsonObjectExperience = json.getJSONObject("experience");
        experience.setLevel(jsonObjectExperience.getInt("level"));
        experience.setNutritionXP(jsonObjectExperience.getInt("nutritionXP"));
        experience.setActivityXP(jsonObjectExperience.getInt("activityXP"));
        experience.setSocialXP(jsonObjectExperience.getInt("socialXP"));
        experience.setTicket(jsonObjectExperience.getInt("ticket"));
        experience.setXPForCalories(jsonObjectExperience.getBoolean("xpforCalories"));
        experience.setXPForProtein(jsonObjectExperience.getBoolean("xpforProtein"));
        experience.setXPForCarbs(jsonObjectExperience.getBoolean("xpforCarbs"));
        experience.setXPForFat(jsonObjectExperience.getBoolean("xpforFat"));
        pupil.setExperience(experience);

        return pupil;
    }
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
