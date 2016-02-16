package services;

import com.avaje.ebean.Model;
import models.BlogPost;
import models.User;

import java.util.List;
import java.util.stream.Collectors;

public class BlogPostService {
    public static final Model.Finder<Long, BlogPost> find = new Model.Finder<Long, BlogPost>(
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
