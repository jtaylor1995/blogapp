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

    public static final Finder<Long, BlogPost> find = new Finder<Long, BlogPost>(
            Long.class, BlogPost.class);

    public static List<BlogPost> findAllBlogPosts() {
        List<BlogPost> blogPosts = find.findList();
        return orderPosts(blogPosts);
    }

    public static List<BlogPost> findBlogPostsByUser(final User user) {
        List<BlogPost> blogPosts = find.where().eq("user", user).findList();
        return orderPosts(blogPosts);
    }

    public static BlogPost findBlogPostById(final Long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }

    private static List<BlogPost> orderPosts(List<BlogPost> blogPosts) {
        return blogPosts.stream().sorted(
                (bp1, bp2) -> Long.compare(bp2.likeCount * 2 + bp2.commentCount, bp1.likeCount * 2 + bp1.commentCount)
        ).collect(Collectors.toList());
    }

}
