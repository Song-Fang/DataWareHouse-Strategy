package edu.pitt.api.Mongo.controllers;

import edu.pitt.api.Mongo.config.AppKeys;
import edu.pitt.api.Mongo.models.MongoAccidents;
import edu.pitt.api.Mongo.models.MongoUsers;
import edu.pitt.api.Mongo.repository.MongoAccidentsRepository;
import edu.pitt.api.Mongo.repository.MongoAdminRepository;
import edu.pitt.api.Mongo.repository.MongoUsersRepository;
import edu.pitt.api.Postgres.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(AppKeys.Mongo_API_PATH + "/admin")
public class MongoAdminController {
    @Autowired
    MongoUsersRepository userRepo;
    @Autowired
    MongoAdminRepository adminRepo;
    @Autowired
    MongoAccidentsRepository accidentRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /*delete reports by reports' Id*/
    @DeleteMapping("/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccidentsById(@PathVariable String reportId) {
        try {
            MongoAccidents acc = accidentRepo.findBy_id(reportId);
            if (acc == null) {
                throw new RuntimeException("query report null");
            } else {
                accidentRepo.deleteById(reportId);
            }
        } catch (Exception e) {
            throw new RuntimeException("No report is found");
        }
    }

    /*delete users by username*/
    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUsersbyUsername(@PathVariable String username) {
        try {
            boolean exists =
                    userRepo.existsUsersByUsername(username);
            if (exists) {
                MongoUsers u=userRepo.findUsersByUsernameIs(username);
                userRepo.delete(u);

            }else{
                throw new RuntimeException("user is not found");
            }
        } catch (Exception er) {
            throw er;
        }
    }

    @PutMapping("updateUser/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MongoUsers updateUser(@PathVariable String username, @RequestBody MongoUsers user) {
        return MongoUsersController.updateUser(username, user, userRepo);
    }


    static Object getObject(Optional<MongoUsers> admin, JwtTokenProvider jwtTokenProvider) {
        try {
            String token = jwtTokenProvider.createMongoToken(admin.get());
            HashMap<String, Object> result = new HashMap<>();
            result.put("user", admin);
            result.put("token", token);
            System.out.println(token);
            return result;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username/password supplied");
        }
    }


    // admin login
    @PostMapping(value = "/login")
    public Object login(@RequestBody MongoUsersController.LoginBody body) {
        MongoUsers u = userRepo.findUsersByUsernameIsAndPasswordIs(body.username, body.password);
        if (u == null) {
            return ResponseEntity.badRequest().body("User username and password mismatch");
        }
        if (!u.isAdmin()) {
            return ResponseEntity.badRequest().body("You are not an administrator, please log-in as a user");
        }
        return MongoAdminController.getObject(Optional.of(u), jwtTokenProvider);
    }

    // get all users
    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MongoUsers> getAllUsers() {
        return userRepo.findAll();
    }

    // get all accidents
    @GetMapping("/allAccidents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MongoAccidents> getRecent100Reports() {
        return accidentRepo.getRecent100Reports();
    }

    // change role of a user , assign admin
    @PutMapping("/changeRole/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MongoUsers changeRole(@PathVariable String username) {
        MongoUsers user = userRepo.findUsersByUsernameIs(username);
        user.setAdmin(!user.isAdmin());
        return userRepo.save(user);
    }

    // handel reports by commments
    @PutMapping("/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MongoAccidents updateByReportId(@PathVariable String reportId, @RequestBody MongoAccidents accidents) {
        MongoAccidents oldAccident = accidentRepo.findBy_id(reportId);
        if (oldAccident == null) {
            throw new RuntimeException("No report is found");
        } else {
            oldAccident.setCity(accidents.getCity());
            oldAccident.setHumidity(accidents.getHumidity());
            oldAccident.setLat(accidents.getLat());
            oldAccident.setLng(accidents.getLng());
            oldAccident.setState(accidents.getState());
            oldAccident.setZipcode(accidents.getZipcode());
            oldAccident.setStreet(accidents.getStreet());
            oldAccident.setVisibility(accidents.getVisibility());
            return accidentRepo.save(oldAccident);
        }
    }


//    check info match on admin users
//    @GetMapping(value = "/infoCheck")
//    public Users checkInfoBy5Fields(@RequestBody Users user) {
//        List<Users> u= userRepo.checkInfo(user.getUsrname(), user.getCity(),
//                user.getState(), user.getEmail(), user.getPhone());
//        if(u.size()==0){
//            throw new RuntimeException("user doesn't exist");
//        }else if(u.size()>1){
//            throw new RuntimeException("user duplicated");
//        }else return u.get(0);
//
//    }


}