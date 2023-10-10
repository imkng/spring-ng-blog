package com.programming.springblog.service;

import com.programming.springblog.dto.PostDto;
import com.programming.springblog.exception.PostNotFoundException;
import com.programming.springblog.model.Post;
import com.programming.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto){
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    public List<PostDto> showAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(this::mapPostToDto).collect(Collectors.toList());
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id" + id));
        return mapPostToDto(post);
    }
    private PostDto mapPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setUserName(post.getUsername());
        return postDto;
    }
    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
}
