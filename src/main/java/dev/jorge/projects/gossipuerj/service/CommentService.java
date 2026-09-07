package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.dto.request.comment.CommentRequest;
import dev.jorge.projects.gossipuerj.dto.response.comment.CommentResponse;
import dev.jorge.projects.gossipuerj.exception.comment.CommentNotFoundException;
import dev.jorge.projects.gossipuerj.model.Comment;
import dev.jorge.projects.gossipuerj.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthService authService;
    private final PostService postService;

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(String postId, String commentId, String userId, CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.content());
        comment.setAuthor(authService.findById(userId));
        comment.setPost(postService.findById(postId));
        comment.setParent(commentId != null ? findById(commentId) : null);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findPostComments(String postId) {
        return commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findReplies(String postId, String commentId) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);
        if (comment == null) {
            throw new CommentNotFoundException(commentId);
        }
        return comment.getReplies()
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public Comment findById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

}