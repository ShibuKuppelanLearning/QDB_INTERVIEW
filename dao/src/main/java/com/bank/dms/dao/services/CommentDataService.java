package com.bank.dms.dao.services;

import java.util.List;

import com.bank.dms.core.vo.Comment;

public interface CommentDataService {

	Long createCommentAgainstPost(Long postId, Comment comment);

	List<Comment> fetchCommentsByPost(Long postId);
}