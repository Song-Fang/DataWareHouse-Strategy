package edu.pitt.api.Postgres.controllers;

import edu.pitt.api.Postgres.config.AppKeys;
import edu.pitt.api.Postgres.models.Accidents;
import edu.pitt.api.Postgres.models.User;
import edu.pitt.api.Postgres.repository.AccidentRepository;
import edu.pitt.api.Postgres.repository.AdminRepository;
import edu.pitt.api.Postgres.repository.UserRepository;
import edu.pitt.api.Postgres.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(AppKeys.Postgres_API_PATH + "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccidentRepository accidentRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping(value = "/login")
    public Object Userlogin(@RequestBody LoginBody body) {
        Optional<User> tempuser = userRepository.findOneByUsernameAndPassword(body.username, body.password);
        User user = adminRepository.findOneByUsernameAndPassword(body.username, body.password);
        if (!tempuser.isPresent()) {
            return ResponseEntity.badRequest().body("User username and password mismatch");
//            throw new RuntimeException("User username and password mismatch");
        }
        if (user == null || user.getIsAdmin()) {
            return ResponseEntity.badRequest().body("You are Admin Please Use Admin Login Link");
        }

        return AdminController.getObject(tempuser, jwtTokenProvider);
    }

    @PostMapping(value = "/signup")
    public Object signup(@RequestBody User user) {
        User exsitingUser = userRepository.findOneByUsername(user.getUsername());
        User EmailUser = userRepository.findOneByEmail(user.getEmail());
        if (exsitingUser != null) {
            throw new RuntimeException("username already exists");
        } else if (EmailUser != null) {
            throw new RuntimeException("email already exists");
        } else {
            String token = jwtTokenProvider.createUserToken(user);

            HashMap<String, Object> result = new HashMap<>();
            result.put("user", userRepository.save(user));
            result.put("token", token);
            return result;
        }
    }


    @PostMapping(value = "/infoCheck")
    public User infoCheck(@RequestBody User user) {
        User exsitingUser = userRepository.findOneByUsernameAndCityAndStateAndEmailAndPhonenumber(user.getUsername(), user.getCity(), user.getState(), user.getEmail(), user.getPhonenumber());
        if (exsitingUser == null) {
            throw new RuntimeException("user doesn't exist");
        } else {
            return exsitingUser;
        }
    }

    @PutMapping(value = "/updatePassword/{username}")
    public Object restPassword(@PathVariable String username, @RequestBody User user) {
        User oldUser = userRepository.findOneByUsername(username);
        if (oldUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldUser.setPassword(user.getPassword());
            String token = jwtTokenProvider.createUserToken(oldUser);

            HashMap<String, Object> result = new HashMap<>();
            result.put("user", userRepository.save(oldUser));
            result.put("token", token);
            return result;
        }
    }

    @PutMapping(value = "/updateSettings/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public User updateSettings(@PathVariable String username) {
        User oldUser = userRepository.findOneByUsername(username);
        if (oldUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldUser.setAnonymous(!oldUser.getIsAnonymous());
        }
        return userRepository.save(oldUser);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public User getAllInfo(@PathVariable String username) {
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            return user;
        }
    }

    @PostMapping(value = "/updateAllInfo/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public User updateAllInfo(@PathVariable String username, @RequestBody User user) {
        User oldUser = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldUser.setCity(user.getCity());
            oldUser.setState(user.getState());
            oldUser.setPhonenumber(user.getPhonenumber());
            if (!oldUser.getEmail().equals(user.getEmail())) {
                if (emailExist(user.getEmail())) {
                    throw new RuntimeException("email already exist");
                } else {
                    oldUser.setEmail(user.getEmail());
                }
            }
            return userRepository.save(oldUser);

        }
    }

    @PostMapping(value = "/self-report/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Accidents self_report(@PathVariable String username, @RequestBody Accidents accidents) {
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            if (!user.getIsAnonymous()) {
                accidents.setSource(username);
            }
        }
        return accidentRepository.save(accidents);
    }

    @GetMapping(value = "/reports/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<Accidents> reportsByUsername(@PathVariable String username) {
        return accidentRepository.findAllBySource(username);
    }

    @DeleteMapping(value = "/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public void deleteByReportId(@PathVariable Long reportId) {
        try {
            accidentRepository.deleteById(reportId);
        } catch (NullPointerException er) {
            throw new RuntimeException("cannot find report");
        }

    }


    private boolean emailExist(String email) {
        User user = userRepository.findOneByEmail(email);
        return user != null;
    }


    public static class LoginBody {
        public String username;
        public String password;
    }


}
