package services;

import com.avaje.ebean.Model;
import models.BlogPost;
import models.PostComment;
import models.User;

import java.util.List;

public class PostCommentService {
    public static final Model.Finder<Long, PostComment> find = new Model.Finder<Long, PostComment>(
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
