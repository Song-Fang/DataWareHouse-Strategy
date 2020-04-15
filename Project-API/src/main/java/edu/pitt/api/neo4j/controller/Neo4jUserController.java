package edu.pitt.api.neo4j.controller;

import edu.pitt.api.Postgres.security.JwtTokenProvider;
import edu.pitt.api.neo4j.Config.AppKeys;
import edu.pitt.api.neo4j.domain.Neo4jAccident;
import edu.pitt.api.neo4j.domain.Neo4jUser;
import edu.pitt.api.neo4j.repository.Neo4jAccidentRepository;
import edu.pitt.api.neo4j.repository.Neo4jAdminRepository;
import edu.pitt.api.neo4j.repository.Neo4jUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(AppKeys.NEO4J_API_PATH + "/user")
public class Neo4jUserController {
    @Autowired
    Neo4jUserRepository neo4jUserRepository;
    @Autowired
    Neo4jAccidentRepository neo4jAccidentRepository;
    @Autowired
    Neo4jAdminRepository neo4jAdminRepository;

    @Autowired
    private JwtTokenProvider neo4jJwtTokenProvider;


    @PostMapping(value = "/login")
    public Object Userlogin(@RequestBody LoginBody body) {
        Optional<Neo4jUser> tempuser = neo4jUserRepository.findOneByUsernameAndPassword(body.username, body.password);
        Neo4jUser user = neo4jAdminRepository.findOneByUsernameAndPassword(body.username, body.password);
        if (!tempuser.isPresent()) {
            return ResponseEntity.badRequest().body("Neo4jUser username and password mismatch");
        }
        if (user == null || user.getIsAdmin()) {
            return ResponseEntity.badRequest().body("You are Admin Please Use Admin Login Link");
        }
        return Neo4jAdminController.getObject(tempuser, neo4jJwtTokenProvider);
    }

    @PostMapping(value = "/signup")
    public Object signup(@RequestBody Neo4jUser neo4jUser) {
        Neo4jUser exsitingNeo4jUser = neo4jUserRepository.findOneByUsername(neo4jUser.getUsername());
        Neo4jUser EmailUser = neo4jUserRepository.findOneByEmail(neo4jUser.getEmail());
        if (exsitingNeo4jUser != null) {
            throw new RuntimeException("username already exists");
        } else if (EmailUser != null) {
            throw new RuntimeException("email already exists");
        } else {
            String token = neo4jJwtTokenProvider.createNeo4jToken(neo4jUser);

            HashMap<String, Object> result = new HashMap<>();
            result.put("user", neo4jUserRepository.save(neo4jUser));
            result.put("token", token);
            return result;
        }
    }


    @PostMapping(value = "/infoCheck")
    public Neo4jUser infoCheck(@RequestBody Neo4jUser neo4jUser) {
        Neo4jUser exsitingNeo4jUser = neo4jUserRepository.findOneByUsernameAndCityAndStateAndEmailAndPhonenumber(neo4jUser.getUsername(), neo4jUser.getCity(), neo4jUser.getState(), neo4jUser.getEmail(), neo4jUser.getPhonenumber());
        if (exsitingNeo4jUser == null) {
            throw new RuntimeException("neo4jUser doesn't exist");
        } else {
            return exsitingNeo4jUser;
        }
    }

    @PutMapping(value = "/updatePassword/{username}")
    public Neo4jUser restPassword(@PathVariable String username, @RequestBody Neo4jUser neo4jUser) {
        Neo4jUser oldNeo4jUser = neo4jUserRepository.findOneByUsername(username);
        if (oldNeo4jUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldNeo4jUser.setPassword(neo4jUser.getPassword());
            String token = neo4jJwtTokenProvider.createNeo4jToken(oldNeo4jUser);

            HashMap<String, Object> result = new HashMap<>();
            result.put("user", neo4jUserRepository.save(oldNeo4jUser));
            result.put("token", token);
            return neo4jUserRepository.save(oldNeo4jUser);
        }
    }


    //set anonymous
    @PutMapping(value = "/updateSettings/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Neo4jUser updateSettings(@PathVariable String username) {
        Neo4jUser oldNeo4jUser = neo4jUserRepository.findOneByUsername(username);
        if (oldNeo4jUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldNeo4jUser.setAnonymous(!oldNeo4jUser.getIsAnonymous());
        }
        return neo4jUserRepository.save(oldNeo4jUser);
    }

    //get user information
    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Neo4jUser getAllInfo(@PathVariable String username) {
        Neo4jUser neo4jUser = neo4jUserRepository.findOneByUsername(username);
        if (neo4jUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            return neo4jUser;
        }
    }


    //update all information
    @PostMapping(value = "/updateAllInfo/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Neo4jUser updateAllInfo(@PathVariable String username, @RequestBody Neo4jUser neo4jUser) {
        Neo4jUser oldNeo4jUser = neo4jUserRepository.findOneByUsername(username);
        if (neo4jUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            oldNeo4jUser.setCity(neo4jUser.getCity());
            oldNeo4jUser.setState(neo4jUser.getState());
            oldNeo4jUser.setPhonenumber(neo4jUser.getPhonenumber());
            if (!oldNeo4jUser.getEmail().equals(neo4jUser.getEmail())) {
                if (emailExist(neo4jUser.getEmail())) {
                    throw new RuntimeException("email already exist");
                } else {
                    oldNeo4jUser.setEmail(neo4jUser.getEmail());
                }
            }
            return neo4jUserRepository.save(oldNeo4jUser);

        }
    }

    @PostMapping(value = "/self-report/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Neo4jAccident self_report (@PathVariable String username, @RequestBody Neo4jAccident accidents) {
        Neo4jUser neo4jUser = neo4jUserRepository.findOneByUsername(username);
        if (neo4jUser == null) {
            throw new RuntimeException("username doesn't exist");
        } else {
            if (!neo4jUser.getIsAnonymous()){
                accidents.setSource(username);
            }
        }
        return neo4jAccidentRepository.save(accidents);
    }

    @GetMapping(value = "/reports/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<Neo4jAccident> reportsByUsername (@PathVariable String username) {
        return neo4jAccidentRepository.findAllBySource(username);
    }

    @DeleteMapping(value = "/report/{reportId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public void deleteByReportId (@PathVariable Long reportId) {
        try{
            neo4jAccidentRepository.deleteById(reportId);
        } catch (NullPointerException er) {
            throw new RuntimeException("cannot find report");
        }

    }


    private boolean emailExist(String email) {
        Neo4jUser neo4jUser = neo4jUserRepository.findOneByEmail(email);
        return neo4jUser != null;
    }


    public static class LoginBody {
        public String username;
        public String password;
    }


}
