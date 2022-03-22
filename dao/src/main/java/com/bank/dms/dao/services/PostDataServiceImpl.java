package com.bank.dms.dao.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bank.dms.core.vo.Post;
import com.bank.dms.dao.model.Document;
import com.bank.dms.dao.model.User;
import com.bank.dms.dao.repository.DocumentRepository;
import com.bank.dms.dao.repository.PostRepository;
import com.bank.dms.dao.repository.UserRepository;

@Component
public class PostDataServiceImpl implements PostDataService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	@Transactional
	public Long createPost(Post post) {
		Long postId = null;
		User user = null;
		Document document = null;
		if (post.getCreatedBy() != null) {
			user = userRepository.findById(post.getCreatedBy().getId()).orElse(null);
		}

		if (post.getDocument() != null) {
			document = documentRepository.findById(post.getDocument().getId()).orElse(null);
		}

		if (user != null) {
			com.bank.dms.dao.model.Post postModel = new com.bank.dms.dao.model.Post(post.getDescription(), user,
					document);
			postModel = postRepository.save(postModel);
			if (postModel != null) {
				postId = postModel.getId();
			}
		}
		return postId;
	}

	@Override
	public Post fetchPostById(Long userId) {
		com.bank.dms.dao.model.Post postModel = null;
		com.bank.dms.dao.model.User userModel = null;
		com.bank.dms.dao.model.Document documentModel = null;
		Post post = null;
		if (userId != null) {
			postModel = postRepository.findById(userId).orElse(null);
			userModel = postModel.getCreatedBy();
			documentModel = postModel.getDocument();
			post = new Post(postModel.getId(), postModel.getDescription(),
					new com.bank.dms.core.vo.User(userModel.getId(), userModel.getFirstName(), userModel.getLastName()),
					new com.bank.dms.core.vo.Document(documentModel.getName(), documentModel.getDescription(),
							documentModel.getPath(), documentModel.getContent()));
		}
		return post;
	}

	@Override
	public List<Post> fetchPostsByUserId(Long userId) {
		final List<Post> posts = new ArrayList<Post>();
		if (userId != null) {
			User userModel = userRepository.findById(userId).orElse(null);
			if (userModel != null) {
				List<com.bank.dms.dao.model.Post> postModels = postRepository.findByCreatedBy(userModel);
				if (!CollectionUtils.isEmpty(postModels)) {
					postModels.forEach(postModel -> {
						com.bank.dms.dao.model.Document documentModel = postModel.getDocument();
						posts.add(new Post(postModel.getId(), postModel.getDescription(),
								new com.bank.dms.core.vo.User(userModel.getId(), userModel.getFirstName(),
										userModel.getLastName()),
								new com.bank.dms.core.vo.Document(documentModel.getName(),
										documentModel.getDescription(), documentModel.getPath(),
										documentModel.getContent())));
					});
				}
			}
		}
		return posts;
	}
}