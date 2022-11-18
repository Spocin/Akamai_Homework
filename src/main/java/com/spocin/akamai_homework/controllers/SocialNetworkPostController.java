package com.spocin.akamai_homework.controllers;

import com.spocin.akamai_homework.DTOs.SocialNetworkPostCreationDTO;
import com.spocin.akamai_homework.models.SocialNetworkPost;
import com.spocin.akamai_homework.services.SocialNetworkPostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class SocialNetworkPostController {
    private final SocialNetworkPostService socialNetworkPostService;

    private final ModelMapper modelMapper;

    public SocialNetworkPostController (
            SocialNetworkPostService socialNetworkPostService,
            ModelMapper modelMapper
    ) {
        this.socialNetworkPostService = socialNetworkPostService;
        this.modelMapper = modelMapper;
    }

    /* CREATE */
    @PostMapping("/create")
    public ResponseEntity<SocialNetworkPost> createSocialNetworkPost (
            @Valid @RequestBody SocialNetworkPostCreationDTO newPostDTO
    ) {
        SocialNetworkPost newPost = modelMapper.map(newPostDTO, SocialNetworkPost.class);
        newPost.setPostDate(OffsetDateTime.now());
        newPost.setViewCount(0L);
        return new ResponseEntity<>(socialNetworkPostService.createSocialNetworkPost(newPost),HttpStatus.CREATED);
    }

    /* READ */
    @GetMapping()
    public ResponseEntity<List<SocialNetworkPost>> getAllSocialNetworkPosts () {
        return new ResponseEntity<>(socialNetworkPostService.findAllSocialNetworkPost(), HttpStatus.OK);
    }

    /* UPDATE */

    /* DELETE */
}
