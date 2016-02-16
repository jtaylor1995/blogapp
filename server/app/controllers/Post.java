package controllers;

import models.BlogPost;
import models.PostComment;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import services.BlogPostService;

public class Post extends Controller {

    public static class PostForm {
        @Constraints.Required
        @Constraints.MaxLength(255)
        public String subject;

        @Constraints.Required
        public String content;

    }

    private static User getUser() {
        return User.findByEmail(session().get("username"));
    }

    public Result addPost() {
        Form<PostForm> postForm = Form.form(PostForm.class).bindFromRequest();

        if (postForm.hasErrors()) {
            return badRequest(postForm.errorsAsJson());
        } else {
            BlogPost newBlogPost = new BlogPost();
            newBlogPost.commentCount = 0L;
            newBlogPost.likeCount = 0L;
            newBlogPost.subject = postForm.get().subject;
            newBlogPost.content = postForm.get().content;
            newBlogPost.user = getUser();
            newBlogPost.save();
        }
        return ok(Application.buildJsonResponse("success", "Post added successfully"));
    }

    public Result getUserPosts() {
        User user = getUser();
//        if (user == null) {
//            //return badRequest(Application.buildJsonResponse("error", "No such user"));
//        }
        //Not sure what to do above, brings error when first time sign up
        //maybe sign up should take you to /# not /dashboard
        return ok(Json.toJson(BlogPostService.findBlogPostsByUser(user)));
    }


    public static class CommentForm {
        @Constraints.Required
        public Long postId;

        @Constraints.Required
        public String comment;
    }

    public Result addComment() {
        Form<CommentForm> commentForm = Form.form(CommentForm.class).bindFromRequest();

        if (commentForm.hasErrors()) {
            return badRequest(commentForm.errorsAsJson());
        } else {
            PostComment newComment = new PostComment();
            BlogPost blogPost = BlogPostService.findBlogPostById(commentForm.get().postId);
            blogPost.commentCount++;
            blogPost.save();
            newComment.blogPost = blogPost;
            newComment.user = getUser();
            newComment.content = commentForm.get().comment;
            newComment.likeCount = 0L;
            newComment.save();
            return ok(Application.buildJsonResponse("success", "Comment added successfully"));
        }
    }

    public static class LikeForm {
        @Constraints.Required
        public Long postId;
    }

    public Result likePost() {
        Form<LikeForm> likeForm = Form.form(LikeForm.class).bindFromRequest();

        if (likeForm.hasErrors()) {
            return badRequest(likeForm.errorsAsJson());
        } else {
            BlogPost blogPost = BlogPostService.findBlogPostById(likeForm.get().postId);
            User user = getUser();
            if (user.hasUserAlreadyLiked(blogPost, user.id)) {
                return ok(Application.buildJsonResponse("success", "Cannot like a post twice"));
            }
            else {
                user.blogPost = blogPost;
                blogPost.likeCount++;
                blogPost.save();
                user.save();
                return ok(Application.buildJsonResponse("success", "Post liked successfully"));
            }
        }
    }
}
