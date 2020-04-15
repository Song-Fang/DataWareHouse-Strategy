package edu.pitt.api.Postgres.controllers;

import edu.pitt.api.Postgres.config.AppKeys;
import edu.pitt.api.Postgres.models.Accidents;
import edu.pitt.api.Postgres.models.User;
import edu.pitt.api.Postgres.repository.AccidentRepository;
import edu.pitt.api.Postgres.repository.AdminRepository;
import edu.pitt.api.Postgres.repository.UserRepository;
import edu.pitt.api.Postgres.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(AppKeys.Postgres_API_PATH + "/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccidentRepository accidentRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    static Object getObject(Optional<User> admin, JwtTokenProvider jwtTokenProvider) {
        try {
            String token = jwtTokenProvider.createUserToken(admin.get());
            HashMap<String, Object> result = new HashMap<>();
            result.put("user", admin);
            result.put("token", token);
            return result;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username/password supplied");
        }
    }

    @PostMapping("/login")
    public Object adminLogin(@RequestBody UserController.LoginBody body) {
        Optional<User> admin = userRepository.findOneByUsernameAndPassword(body.username, body.password);
        User Admin = adminRepository.findOneByUsernameAndPassword(body.username, body.password);

        if (!admin.isPresent()) {
            return ResponseEntity.badRequest().body("User username and password mismatch");
        }
        if (Admin == null || !Admin.getIsAdmin()) {
            return ResponseEntity.badRequest().body("You are not admin");
        }
        return getObject(admin, jwtTokenProvider);

    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "isAdmin"));
    }

    @GetMapping("/allAccidents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Accidents> getRecent100Reports() {
        return accidentRepository.findFirst100OrderByStartTimeDesc();
    }

    @PutMapping("changeRole/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User changeRole(@PathVariable String username) {
        User user = userRepository.findOneByUsername(username);
        user.setAdmin(!user.getIsAdmin());
        userRepository.save(user);
        return user;
    }

    @PutMapping("/updateUser/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User updateByUsername(@PathVariable String username, @RequestBody User user) {
        User olduser = userRepository.findOneByUsername(username);
        if (olduser == null) {
            throw new RuntimeException("No user is found");
        } else {
            olduser.setEmail(user.getEmail());
            olduser.setPhonenumber(user.getPhonenumber());
            olduser.setState(user.getState());
            olduser.setCity(user.getCity());
            return userRepository.save(olduser);
        }
    }

    @PutMapping("/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Accidents updateByReportId(@PathVariable Long reportId, @RequestBody Accidents accidents) {
        Accidents oldAccident = accidentRepository.findOneById(reportId);
        if (oldAccident == null) {
            throw new RuntimeException("No report is found");
        } else {
            oldAccident.setCity(accidents.getCity());
            oldAccident.setHumidity(accidents.getHumidity());
            oldAccident.setLatitude(accidents.getLatitude());
            oldAccident.setLongitude(accidents.getLongitude());
            oldAccident.setState(accidents.getState());
            oldAccident.setZipcode(accidents.getZipcode());
            oldAccident.setStreet(accidents.getStreet());
            oldAccident.setVisibility(accidents.getVisibility());
            return accidentRepository.save(oldAccident);
        }
    }

    @DeleteMapping("/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccidentsById(@PathVariable Long reportId) {
        try {
            accidentRepository.deleteById(reportId);
        } catch (NullPointerException er) {
            throw new RuntimeException("No report is found");
        }
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteUserbyUsername(@PathVariable String username) {
        try {
            userRepository.deleteByUsername(username);
        } catch (NullPointerException er) {
            throw new RuntimeException("No user is found");
        }
    }


}
