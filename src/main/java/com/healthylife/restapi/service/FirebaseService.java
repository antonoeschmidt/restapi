package com.healthylife.restapi.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.WriteResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.healthylife.restapi.model.*;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.concurrent.ExecutionException;

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
        Pupil pupil = JSONtoPupil(json);

        String username = json.getString("_username");
        System.out.println(username);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(json.getString("_uid"));
        ref.setValueAsync(pupil);

        return json.toString();
    }

    /**
     * Kan ikke få json objekt ind på firebase for some reason so we are doing it like this, i feel
     * like it is mega besværligt but untill we find a better solution thats just the way it is
     *
     * @param json
     * @return
     */


    public Pupil JSONtoPupil(JSONObject json) {

        String s = json.toString();

       // Gson gson = new Gson();
        Pupil pupil = new Pupil();
       //Pupil pupil = gson.fromJson(s, Pupil.class);

        pupil.setUsername(json.getString("_username"));
        pupil.setPassword(json.getString("_password"));
        pupil.setUID(json.getString("_uid"));
        pupil.setFirstTimeLoggedIn(json.getBoolean("_first_time_loggedin"));

        //Kunne ikke få ting fra nested json objekter så lavede et nyt
        Physique physique = new Physique();
        JSONObject jsonObjectPhysique = json.getJSONObject("_physique");
        physique.setHeight(jsonObjectPhysique.getDouble("_height"));
        physique.setWeight(jsonObjectPhysique.getDouble("_weight"));
        physique.setActivityLevel(jsonObjectPhysique.getInt("_activity_level"));
        pupil.setPhysique(physique);

        PersonalInfo personalInfo = new PersonalInfo();
        JSONObject jsonObjectPersonalInfo = json.getJSONObject("_personalinfo");
        personalInfo.setFirstName(jsonObjectPersonalInfo.getString("_firstName"));
        personalInfo.setLastName(jsonObjectPersonalInfo.getString("_lastName"));
        personalInfo.setGender(jsonObjectPersonalInfo.getString("_gender"));
        personalInfo.setDateOfBirth(jsonObjectPersonalInfo.getLong("_dateOfBirth"));
        personalInfo.setZipCode(jsonObjectPersonalInfo.getInt("_zipCode"));
        pupil.setPersonalInfo(personalInfo);

        Experience experience = new Experience();
        JSONObject jsonObjectExperience = json.getJSONObject("_experience");
        experience.setLevel(jsonObjectExperience.getInt("_level"));
        experience.setNutritionXP(jsonObjectExperience.getInt("_nutritionXP"));
        experience.setActivityXP(jsonObjectExperience.getInt("_activityXP"));
        experience.setSocialXP(jsonObjectExperience.getInt("_socialXP"));
        experience.setTicket(jsonObjectExperience.getInt("_ticket"));
        experience.setXPForCalories(jsonObjectExperience.getBoolean("_XPForCalories"));
        experience.setXPForProtein(jsonObjectExperience.getBoolean("_XPForProtein"));
        experience.setXPForCarbs(jsonObjectExperience.getBoolean("_XPForCarbs"));
        experience.setXPForFat(jsonObjectExperience.getBoolean("_XPForFat"));
        pupil.setExperience(experience);


        return pupil;
    }


}
