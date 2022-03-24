package com.bank.dms.api.core.response;

import java.util.ArrayList;
import java.util.List;

import com.bank.dms.core.vo.Comment;

public class FetchCommentResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6756714156951187715L;

	private List<Comment> comments;

	public FetchCommentResponse() {
		// TODO Auto-generated constructor stub
	}

	public FetchCommentResponse(List<Comment> comments) {
		super();
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		if (this.comments == null) {
			this.comments = new ArrayList<Comment>();
		}
		this.comments.add(comment);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
