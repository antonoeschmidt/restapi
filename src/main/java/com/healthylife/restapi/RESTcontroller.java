package com.healthylife.restapi;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.client.json.Json;
import com.healthylife.restapi.model.Pupil;
import com.healthylife.restapi.model.TestObject;
import com.healthylife.restapi.model.User;
import com.healthylife.restapi.service.FirebaseService;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.Naming;
import java.util.List;
import java.util.concurrent.ExecutionException;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201",
        "http://localhost:4204", "https://antonoeschmidt.github.io"})
@RestController
public class RESTcontroller {

    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/login")
    public boolean login(@RequestBody String login) throws Exception {
        System.out.println(login);
        JSONObject json = new JSONObject(login);
        System.out.println(json);
        System.out.println(json.getString("user"));
        System.out.println(json.getString("pass"));
        Brugeradmin brugeradmin = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        try {
            Bruger nytLogin = brugeradmin.hentBruger(json.getString("user"), json.getString("pass"));
            System.out.println(nytLogin.email);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Login not authorized");
        }
        return false;
    }

    @PostMapping("/testpost")
    public String testpost(@RequestBody User user) throws ExecutionException, InterruptedException {
        return firebaseService.postData(user);
    }

    @PostMapping("/saveuser")
    public String newTestpost(@RequestBody String testObject) throws ExecutionException, InterruptedException {
        JSONObject json = new JSONObject(testObject);
        System.out.println(json);
       return firebaseService.testPost(json);
    }

    @GetMapping("/getuser")
    public String getUser() {
        //TODO: implement this
        return "get user";
    }

    @GetMapping("/getallusers")
    public List<Pupil> getAllUsers() throws InterruptedException {
        return firebaseService.getAllUsers();
    }


}
