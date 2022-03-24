package com.bank.dms.dao.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bank.dms.core.vo.Comment;
import com.bank.dms.core.vo.Document;
import com.bank.dms.core.vo.Post;
import com.bank.dms.dao.model.User;
import com.bank.dms.dao.repository.CommentRepository;
import com.bank.dms.dao.repository.PostRepository;
import com.bank.dms.dao.repository.UserRepository;

@Component
public class CommentDataServiceImpl implements CommentDataService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Long createCommentAgainstPost(Long postId, Comment comment) {
		Long commentId = null;
		if (postId != null && postId.longValue() > 0) {
			if (comment.getCreatedBy() != null) {
				User createdByModel = userRepository.findById(comment.getCreatedBy().getId()).orElse(null);
				if (createdByModel != null) {
					com.bank.dms.dao.model.Post postModel = postRepository.findById(postId).orElse(null);
					if (postModel != null) {
						com.bank.dms.dao.model.Comment commentModel = new com.bank.dms.dao.model.Comment(
								comment.getContent(), createdByModel, postModel);
						commentModel = commentRepository.save(commentModel);
						if (commentModel != null) {
							commentId = commentModel.getId();
						}
					}
				}
			}
		}
		return commentId;
	}

	@Override
	public List<Comment> fetchCommentsByPost(Long postId) {
		final List<Comment> comments = new ArrayList<Comment>();
		if (postId != null && postId.longValue() > 0) {
			com.bank.dms.dao.model.Post postModel = postRepository.findById(postId).orElse(null);
			if (postModel != null && !CollectionUtils.isEmpty(postModel.getComments())) {
				postModel.getComments().forEach(commentModel -> {
					if (commentModel != null) {
						User userModel = commentModel.getCreatedBy();
						if (userModel != null) {
							Document document = null;
							com.bank.dms.core.vo.User user = new com.bank.dms.core.vo.User(userModel.getFirstName(),
									userModel.getLastName());
							if (postModel.getDocument() != null) {
								com.bank.dms.dao.model.Document documentModel = postModel.getDocument();
								document = new Document(documentModel.getId(), documentModel.getName(),
										documentModel.getDescription(), documentModel.getPath(),
										documentModel.getContent());
							}
							comments.add(new Comment(commentModel.getId(), commentModel.getContent(), user,
									new Post(postModel.getId(), postModel.getDescription(), user, document)));
						}
					}
				});
			}
		}
		return comments;
	}
}
