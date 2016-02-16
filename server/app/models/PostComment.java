package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;
import controllers.Post;

/**
 * Model representing Comments to Blog Posts
 */
@Entity
public class PostComment extends Model {

    @Id
    public Long id;

    @ManyToOne
    @JsonIgnore
    public BlogPost blogPost;

    @ManyToOne
    public User user;

    @Column(columnDefinition = "TEXT")
    public String content;

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> likedBy;

    public long likeCount;

}
