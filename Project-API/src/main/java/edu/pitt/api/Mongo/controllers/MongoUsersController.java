package edu.pitt.api.Mongo.controllers;

import edu.pitt.api.Mongo.config.AppKeys;
import edu.pitt.api.Mongo.models.MongoAccidents;
import edu.pitt.api.Mongo.models.MongoUsers;
import edu.pitt.api.Mongo.repository.MongoAccidentsRepository;
import edu.pitt.api.Mongo.repository.MongoUsersRepository;
import edu.pitt.api.Postgres.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(AppKeys.Mongo_API_PATH + "/user")
public class MongoUsersController {
    @Autowired
    private MongoUsersRepository userRepo;
    @Autowired
    private MongoAccidentsRepository acidRepo;
    @Autowired
    private JwtTokenProvider MongojwtTokenProvider;

    /*User registration*/
    @PostMapping(value = "/signup")
    public Object signup(@RequestBody MongoUsers user) {
        MongoUsers u = userRepo.findUsersByUsernameIs(user.getUsername());
        MongoUsers EmailUser = userRepo.findUsersByEmailIs(user.getEmail());
        if (u != null) {
            return ResponseEntity.badRequest().body("username already exists");
//            throw new RuntimeException("username already exists");
        } else if (EmailUser != null) {
            throw new RuntimeException("email already exists");
        } else {
            String token = MongojwtTokenProvider.createMongoToken(user);

            HashMap<String, Object> result = new HashMap<>();
            result.put("user", userRepo.save(user));
            result.put("token", token);
            return result;
        }
    }

    /*Check info is matched or not*/
    @PostMapping(value = "/infoCheck")
    public MongoUsers checkInfoBy5Fields(@RequestBody MongoUsers user) {

        MongoUsers u= userRepo.checkInfo(user.getUsername(), user.getCity(),
                user.getState(), user.getEmail(), user.getPhonenumber());
        if (u == null){
            throw new RuntimeException("user doesn't exist");
        }
        return u;

    }

    /*Reset password for user*/
    @PutMapping(value = "/updatePassword/{username}")
    public MongoUsers restPassword(@PathVariable String username, @RequestBody LoginBody body) {
        MongoUsers u = userRepo.findUsersByUsernameIs(username);
        if (u != null) {
            u.setPassword(body.password);
            return userRepo.save(u);
        } else throw new RuntimeException("username doesn't exist (Mongodb)");

    }

    /*user login*/
    @PostMapping(value = "/login")
    public Object login(@RequestBody LoginBody body) {
        MongoUsers u = userRepo.findUsersByUsernameIsAndPasswordIs(body.username, body.password);
        if (u == null) {
            return ResponseEntity.badRequest().body("User username and password mismatch");
        }
        if (u.isAdmin()) {
            return ResponseEntity.badRequest().body("You are Admin Please Use Admin Login Link");
        }
        return MongoAdminController.getObject(Optional.of(u), MongojwtTokenProvider);
    }


    /*Get all user info*/
    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public MongoUsers getAllInfo(@PathVariable String username) {
        MongoUsers user = userRepo.findUsersByUsernameIs(username);
        if (user == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            return user;
        }
    }


    /*update new info for user*/
    @PostMapping(value = "/updateAllInfo/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public MongoUsers updateAllInfo(@PathVariable String username, @RequestBody MongoUsers user) {
        MongoUsers u = userRepo.findUsersByUsernameIs(username);
        if (u == null) {
            throw new RuntimeException("username doesn't exist");
        } else {

            //check email eligibility
            String newEmail = user.getEmail();
            if (!u.getEmail().equalsIgnoreCase(newEmail)) {
                if (userRepo.findUsersByEmailIs(newEmail) != null) {
                    throw new RuntimeException("email already exist");
                } else {
                    u.setEmail(newEmail);
                }
            }
            u.setCity(user.getCity());
            u.setState(user.getState());
            u.setPhonenumber(user.getPhonenumber());
            return userRepo.save(u);

        }
    }

    /*Update report settings*/
    @PutMapping(value = "/updateSettings/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public MongoUsers updateSettings(@PathVariable String username) {
        MongoUsers u = userRepo.findUsersByUsernameIs(username);
        if (u == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            u.setAnonymous(!u.isAnonymous());
        }
        return userRepo.save(u);
    }

    /*Self-report mongoAccidents*/
    @PostMapping(value = "/self-report/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public MongoAccidents self_report(@PathVariable String username, @RequestBody MongoAccidents mongoAccidents) {
        MongoUsers user = userRepo.findUsersByUsernameIs(username);
        if (user == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            if (!user.isAnonymous()) {
                mongoAccidents.setSource(username);
            }
        }
        return acidRepo.save(mongoAccidents);
    }

    /*View user's all history reports*/
    @GetMapping(value = "/reports/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<MongoAccidents> reportsByUsername(@PathVariable String username) {
        return acidRepo.findAllBySource(username);
    }


    /* Delete pending reports */
    @DeleteMapping(value = "/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public void deleteByReportId (@PathVariable String reportId) {
        try{
            acidRepo.deleteById(String.valueOf(reportId));
        } catch (NullPointerException er) {
            throw new RuntimeException("cannot find report");
        }

    }

    public static class LoginBody {
        public String username;
        public String password;
    }

    @GetMapping(value = "/All/{username}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<MongoUsers> getAllUser(@PathVariable String username) {
//        return userRepo.findAll();
        if(username.equals("All")) return userRepo.findAll();
        return userRepo.findUsersByUsername(username);
    }

    @GetMapping(value = "/One/{id}")
    public List<MongoUsers> getOneUser(@PathVariable String id) {
        return userRepo.findByThePersonsid(id);
    }

    static MongoUsers updateUser(@PathVariable String username, @RequestBody MongoUsers user, MongoUsersRepository userRepository) {
        MongoUsers u = userRepository.findUsersByUsernameIs(username);
        if (u == null) {
            throw new RuntimeException("username doesn't exist");
        } else {

            //check email eligibility
            String newEmail = user.getEmail();
            if(newEmail.length()<4){
                throw new RuntimeException("No valid email found in requestBody");
            }
            try{
                if (!u.getEmail().equalsIgnoreCase(newEmail)) {
                    if (userRepository.findUsersByEmailIs(newEmail) != null) {
                        throw new RuntimeException("email already exist");
                    } else {
                        u.setEmail(newEmail);
                    }
                }
            }catch(Exception e){
                 throw new RuntimeException("users email doesn't exist");
            }
            u.setCity(user.getCity());
            u.setState(user.getState());
            u.setPhonenumber(user.getPhonenumber());
            return userRepository.save(u);

        }
    }

}
