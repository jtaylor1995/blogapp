package controllers;

import models.PostComment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Comment extends Controller {

    public Result getComment(Long id) {
        PostComment postComment = PostComment.findCommentById(id);

        if (postComment == null) {
            return notFound(Application.buildJsonResponse("error", "comment not found"));
        }
        return ok(Json.toJson(postComment));
    }

}
