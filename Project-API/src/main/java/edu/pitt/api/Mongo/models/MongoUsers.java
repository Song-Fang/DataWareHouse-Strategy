package edu.pitt.api.Mongo.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;


@Document("user")
public class MongoUsers {

    @Id
    private ObjectId _id;
    @Field
    private boolean isAdmin;
    @Field
    private boolean isAnonymous=false;
    @Field
    private String username;
    @Field
    private String password;
    @Field
    private String email;
    @Field
    private String city;
    @Field
    private String state;
    @Field
    private String phonenumber;
    @Field
    private String[] report;



    //Constructors
    public MongoUsers(){}
    public MongoUsers(ObjectId _id, String username, boolean isAdmin, String password, String city, String state, String email, String phonenumber, String[] report)
    {
        this._id = _id;
        this.username = username;
        this.isAdmin = isAdmin;
        this.password = password;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phonenumber = phonenumber;
        this.report= report;
    }

    //ObjectId needs to be converted to String
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String[] getReport() { return report; }
    public void setReport(String[] report) { this.report = report; }

    public boolean isAnonymous(){return isAnonymous;}
    public void setAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public List<MongoRole> getRoles() {
        if (isAdmin) {
            return Arrays.asList(MongoRole.ROLE_ADMIN);
        } else {
            return Arrays.asList(MongoRole.ROLE_CLIENT);
        }
    }

}
