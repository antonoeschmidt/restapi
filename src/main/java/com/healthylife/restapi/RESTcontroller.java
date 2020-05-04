package com.healthylife.restapi;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import com.google.firebase.auth.FirebaseAuthException;
import com.healthylife.restapi.model.Pupil;
import com.healthylife.restapi.service.FirebaseService;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.Naming;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @PutMapping("/saveuser")
    public String newTestpost(@RequestBody String testObject) throws ExecutionException, InterruptedException {
        testObject = testObject.replace("_","");
        JSONObject json = new JSONObject(testObject);
        System.out.println(json);
        return firebaseService.updateUser(json);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody String user) throws FirebaseAuthException {
        user = user.replace("_","");
        JSONObject json = new JSONObject(user);
        System.out.println(json);
        return firebaseService.createUser(json);
    }

    //TODO: Samme logic som delete, easy implimentation
    @GetMapping("/getuser")
    public String getUser(String userName) {
        //TODO: implement this
        return "get user";
    }

    @GetMapping("/getallusers")
    public List<Pupil> getAllUsers() throws InterruptedException {
        return firebaseService.getAllUsers();
    }

    @DeleteMapping("/deleteuser")
    @ResponseBody
    public boolean deleteUser(@RequestParam(name = "uid") String uid) throws InterruptedException{
        System.out.println(uid);
        return firebaseService.deleteUser(uid);
    }


}
