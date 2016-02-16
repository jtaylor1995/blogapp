package services;

import com.avaje.ebean.Model;
import models.BlogPost;
import models.PostComment;
import models.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {

    public static final Model.Finder<Long, User> find = new Model.Finder<Long, User>(
            Long.class, User.class);

    public static User findByEmailAndPassword(String email, String password) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .eq("shaPassword", getSha512(password))
                .findUnique();
    }

    public static boolean hasUserAlreadyLiked(BlogPost blogPost, long id) {
        if (find.where().eq("blogPost", blogPost).eq("id", id).findUnique() == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean hasUserAlreadyLiked(PostComment postComment, long id) {
        if (find.where().eq("postComment", postComment).eq("id", id).findUnique() == null) {
            return false;
        } else {
            return true;
        }
    }

    public static User findByEmail(String email) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .findUnique();
    }

    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
