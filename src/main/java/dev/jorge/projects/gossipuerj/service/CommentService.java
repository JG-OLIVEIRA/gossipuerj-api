package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.dto.request.comment.CommentRequest;
import dev.jorge.projects.gossipuerj.exception.comment.CommentNotFoundException;
import dev.jorge.projects.gossipuerj.model.Comment;
import dev.jorge.projects.gossipuerj.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Comment> findByPostId(String postId, Pageable pageable) {
        return commentRepository.findByPostIdAndParentIdIsNull(postId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByPostIdAndParentId(String postId, String commentId, Pageable pageable) {
        return commentRepository.findByPostIdAndParentId(postId, commentId, pageable);
    }

    @Transactional(readOnly = true)
    public Comment findByPostIdAndId(String postId, String commentId) {
        return commentRepository.findByPostIdAndId(postId, commentId);
    }

    @Transactional(readOnly = true)
    public Comment findById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

}