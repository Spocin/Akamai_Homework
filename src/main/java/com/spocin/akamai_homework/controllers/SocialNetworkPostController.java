package com.spocin.akamai_homework.controllers;

import com.spocin.akamai_homework.DTOs.SocialNetworkPostCreationDTO;
import com.spocin.akamai_homework.models.SocialNetworkPost;
import com.spocin.akamai_homework.services.SocialNetworkPostService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
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
    @GetMapping("")
    public ResponseEntity<List<SocialNetworkPost>> getAllSocialNetworkPosts () {
        return new ResponseEntity<>(socialNetworkPostService.findAllSocialNetworkPost(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SocialNetworkPost> getSocialNetworkPostById (@PathVariable Long postId) {
        return new ResponseEntity<>(socialNetworkPostService.findSocialNetworkPostById(postId),HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<SocialNetworkPost>> getSocialNetworkPostByAuthor (@PathVariable String author) {
        return new ResponseEntity<>(socialNetworkPostService.findSocialNetworkPostByAuthor(author), HttpStatus.OK);
    }

    @GetMapping(value = {"/postDate/{fromDate}/{toDate}","/postDate/{fromDate}"}) /* TODO Normally I would use @RequestParam but due to bug in Spring that interprets + as " " I had to do this that way*/
    public ResponseEntity<List<SocialNetworkPost>> getSocialNetworkPostByDate (
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime fromDate,

            @PathVariable(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime toDate
    ) {
        if (toDate != null) {
            return new ResponseEntity<>(socialNetworkPostService.findSocialNetworkPostByDate(fromDate,toDate), HttpStatus.OK);
        }
        return new ResponseEntity<>(socialNetworkPostService.findSocialNetworkPostByDate(fromDate),HttpStatus.OK);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<SocialNetworkPost>> getTop10SocialNetworkPostByViewCount () {
        return new ResponseEntity<>(socialNetworkPostService.findTop10SocialNetworkPostByViewCount(),HttpStatus.OK);
    }

    /* UPDATE */
    //TODO Update

    /* DELETE */
    //TODO Delete
}
