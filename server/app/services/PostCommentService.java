package services;

import com.avaje.ebean.Model;
import models.PostComment;


public class PostCommentService {
    public static final Model.Finder<Long, PostComment> find = new Model.Finder<Long, PostComment>(
            Long.class, PostComment.class);

    public static PostComment findCommentById(final long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }
}
