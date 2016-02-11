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

    public static final Finder<Long, PostComment> find = new Finder<Long, PostComment>(
            Long.class, PostComment.class);

    public static List<PostComment> findAllCommentsByPost(final BlogPost blogPost) {
        return find
                .where()
                .eq("post", blogPost)
                .findList();
    }

    public static List<PostComment> findAllCommentsByUser(final User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

    public static PostComment findCommentById(final long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }

}
