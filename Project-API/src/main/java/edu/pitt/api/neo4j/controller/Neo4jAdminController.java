package edu.pitt.api.neo4j.controller;

import edu.pitt.api.Postgres.models.User;
import edu.pitt.api.Postgres.security.JwtTokenProvider;
import edu.pitt.api.neo4j.Config.AppKeys;
import edu.pitt.api.neo4j.domain.Neo4jAccident;
import edu.pitt.api.neo4j.domain.Neo4jUser;
import edu.pitt.api.neo4j.repository.Neo4jAccidentRepository;
import edu.pitt.api.neo4j.repository.Neo4jAdminRepository;
import edu.pitt.api.neo4j.repository.Neo4jUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(AppKeys.NEO4J_API_PATH + "/admin")
public class Neo4jAdminController {

    @Autowired
    Neo4jUserRepository neo4jUserRepository;
    @Autowired
    Neo4jAdminRepository neo4jAdminRepository;
    @Autowired
    Neo4jAccidentRepository neo4jAccidentRepository;

    @Autowired
    private JwtTokenProvider neo4jJwtTokenProvider;

    static Object getObject(Optional<Neo4jUser> admin, JwtTokenProvider neo4jJwtTokenProvider) {
        try {
            String token = neo4jJwtTokenProvider.createNeo4jToken(admin.get());
            HashMap<String, Object> result = new HashMap<>();
            result.put("user", admin);
            result.put("token", token);
            return result;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username/password supplied");
        }
    }

    @PostMapping("/login")
    public Object adminLogin(@RequestBody Neo4jUserController.LoginBody body) {
        Optional<Neo4jUser> admin = neo4jUserRepository.findOneByUsernameAndPassword(body.username, body.password);
        Neo4jUser Admin = neo4jAdminRepository.findOneByUsernameAndPassword(body.username, body.password);

        if (!admin.isPresent()) {
            return ResponseEntity.badRequest().body("Neo4jUser username and password mismatch");
        }
        if (Admin == null || !Admin.getIsAdmin()) {
            return ResponseEntity.badRequest().body("You are not admin");
        }
        return getObject(admin, neo4jJwtTokenProvider);

    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Iterable<Neo4jUser> getAllUser() {
        return neo4jUserRepository.findAll(Sort.by(Sort.Direction.ASC, "isAdmin"));
    }

    @GetMapping("/allAccidents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Neo4jAccident> getRecent100Reports() {
//        List<Neo4jAccident> list = this.neo4jAccidentRepository.findFirst100OrderByStartTimeDesc();
//        list.forEach(l -> {
//            System.out.println("   " +l);
//        });
        return neo4jAccidentRepository.findFirst100OrderByStartTimeDesc();
    }

    @PutMapping("/changeRole/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Neo4jUser changeRole(@PathVariable String username) {
        Neo4jUser user = neo4jUserRepository.findOneByUsername(username);
        user.setAdmin(!user.getIsAdmin());
        neo4jUserRepository.save(user);
        return user;
    }

    @PutMapping("/updateUser/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Neo4jUser updateByUsername(@PathVariable String username, @RequestBody Neo4jUser user) {
        Neo4jUser olduser = neo4jUserRepository.findOneByUsername(username);
        if (olduser == null) {
            throw new RuntimeException("No user is found");
        } else {
            olduser.setEmail(user.getEmail());
            olduser.setPhonenumber(user.getPhonenumber());
            olduser.setState(user.getState());
            olduser.setCity(user.getCity());
            return neo4jUserRepository.save(olduser);
        }
    }

    @PutMapping("/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Neo4jAccident updateByReportId(@PathVariable Long reportId, @RequestBody Neo4jAccident accidents) {
        Neo4jAccident oldNeo4jAccident = neo4jAccidentRepository.findOneById(reportId);
        if (oldNeo4jAccident == null) {
            throw new RuntimeException("No report is found");
        } else {
            oldNeo4jAccident.setCity(accidents.getCity());
            oldNeo4jAccident.setHumidity(accidents.getHumidity());
            oldNeo4jAccident.setLatitude(accidents.getLatitude());
            oldNeo4jAccident.setLongitude(accidents.getLongitude());
            oldNeo4jAccident.setState(accidents.getState());
            oldNeo4jAccident.setZipcode(accidents.getZipcode());
            oldNeo4jAccident.setStreet(accidents.getStreet());
            oldNeo4jAccident.setVisibility(accidents.getVisibility());
            return neo4jAccidentRepository.save(oldNeo4jAccident);
        }
    }

    @DeleteMapping("/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccidentsById(@PathVariable Long reportId) {
        try {
            neo4jAccidentRepository.deleteById(reportId);
        } catch (NullPointerException er) {
            throw new RuntimeException("No report is found");
        }
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long deleteUserbyUsername(@PathVariable String username) {
        try {
            return neo4jUserRepository.deleteByUsername(username);
        } catch (NullPointerException er) {
            throw new RuntimeException("No report is found");
        }
    }
}
