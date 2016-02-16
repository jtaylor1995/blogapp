package controllers;

import models.PostComment;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.PostCommentService;
import services.UserService;

public class Comment extends Controller {

    private static User getUser() {
        return UserService.findByEmail(session().get("username"));
    }

    public Result getComment(Long id) {
        PostComment postComment = PostCommentService.findCommentById(id);

        if (postComment == null) {
            return notFound(Application.buildJsonResponse("error", "comment not found"));
        }
        return ok(Json.toJson(postComment));
    }

    public static class LikeForm {
        @Constraints.Required
        public Long commentId;
    }

    public Result likeComment() {
        Form<LikeForm> likeForm = Form.form(LikeForm.class).bindFromRequest();

        if (likeForm.hasErrors()) {
            return badRequest(likeForm.errorsAsJson());
        } else {
            PostComment postComment = PostCommentService.findCommentById(likeForm.get().commentId);
            User user = getUser();
            if (UserService.hasUserAlreadyLiked(postComment, user.id)) {
                return ok(Application.buildJsonResponse("success", "Cannot like a comment twice"));
            } else {
                user.postComment = postComment;
                postComment.likeCount++;
                user.save();
                postComment.save();
                return ok(Application.buildJsonResponse("success", "Comment liked successfully"));
            }
        }
    }

}
