package com.spocin.akamai_homework.controllers;

import com.spocin.akamai_homework.DTOs.SocialNetworkPostCreationDTO;
import com.spocin.akamai_homework.models.SocialNetworkPost;
import com.spocin.akamai_homework.services.SocialNetworkPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class SocialNetworkPostController {
    private final SocialNetworkPostService socialNetworkPostService;

    private final ModelMapper modelMapper;

    /* CREATE */
    @PostMapping("/create")
    public ResponseEntity<SocialNetworkPost> createSocialNetworkPost (
            @Valid @RequestBody SocialNetworkPostCreationDTO newPostDTO
    ) {
        SocialNetworkPost newPost = modelMapper.map(newPostDTO, SocialNetworkPost.class);
        newPost.setPostDate(LocalDateTime.now());
        newPost.setViewCount(0L);
        return new ResponseEntity<>(socialNetworkPostService.createSocialNetworkPost(newPost),HttpStatus.CREATED);
    }

    /* READ */
    @GetMapping
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

    @GetMapping(value = "/postDate")
    public ResponseEntity<List<SocialNetworkPost>> getSocialNetworkPostByDate (
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate
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
    @PutMapping("/update")
    public ResponseEntity<SocialNetworkPost> updateSocialNetworkPost (SocialNetworkPost post) {
        return new ResponseEntity<>(socialNetworkPostService.updateSocialNetworkPost(post),HttpStatus.OK);
    }

    /* DELETE */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSocialNetworkPost (
            @PathVariable Long id
    ) {
        socialNetworkPostService.deleteSocialNetworkPost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
