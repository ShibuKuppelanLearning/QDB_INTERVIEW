package com.bank.dms.api.core.request;

import com.bank.dms.core.vo.Comment;

public class CreateCommentAgainstPostRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8428495013041675974L;

	private Long postId;

	private Comment comment;

	public CreateCommentAgainstPostRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateCommentAgainstPostRequest(Long postId, Comment comment) {
		super();
		this.postId = postId;
		this.comment = comment;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
