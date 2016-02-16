package models;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.data.validation.Constraints;
import com.avaje.ebean.Model;

/**
 * Model representing Blog Post
 */
@Entity
public class BlogPost extends Model {

    @Id
    public Long id;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String subject;

    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String content;

    @ManyToOne
    public User user;

    public Long commentCount;

    public Long likeCount;

    @OneToMany(cascade = CascadeType.ALL)
    public List<PostComment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> likedBy;

}
