package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;
import services.UserService;

/**
 * Model representing a Blog user
 */
@Entity
public class User extends Model {

    @Id
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Column(length = 64, nullable = false)
    private byte[] shaPassword;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    public List<BlogPost> posts;

    @ManyToOne
    @JsonIgnore
    public BlogPost blogPost;

    @ManyToOne
    @JsonIgnore
    public PostComment postComment;

    public void setPassword(String password) {
        this.shaPassword = UserService.getSha512(password);
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
