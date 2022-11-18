package com.spocin.akamai_homework.services;

import com.spocin.akamai_homework.exceptions.NotFoundException;
import com.spocin.akamai_homework.exceptions.NotValidException;
import com.spocin.akamai_homework.models.SocialNetworkPost;
import com.spocin.akamai_homework.repos.SocialNetworkPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SocialNetworkPostService {
    private final SocialNetworkPostRepo socialNetworkPostRepo;

    @Autowired
    public SocialNetworkPostService (SocialNetworkPostRepo socialNetworkPostRepo) {
        this.socialNetworkPostRepo = socialNetworkPostRepo;
    }

    /* CREATE */
    public SocialNetworkPost createSocialNetworkPost (SocialNetworkPost socialNetworkPost) {
        return socialNetworkPostRepo.save(socialNetworkPost);
    }

    /* READ */
    public List<SocialNetworkPost> findAllSocialNetworkPost () {
        return socialNetworkPostRepo.findAll();
    }

    public SocialNetworkPost findSocialNetworkPostById (Long id) {
        return socialNetworkPostRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Post with Id: " + id + " not found"));
    }

    public List<SocialNetworkPost> findSocialNetworkPostByAuthor (String author) {
        if (!socialNetworkPostRepo.existsByAuthor(author)) {
            throw new NotFoundException(author + " does not exists");
        }

        return socialNetworkPostRepo
                .findByAuthor(author);
    }

    public List<SocialNetworkPost> findSocialNetworkPostByDate (OffsetDateTime fromDate, OffsetDateTime toDate) {
        if (fromDate == null) {
            throw new NotValidException("FromDate must be specified");
        }

        if (toDate == null) {
            throw new NotValidException("ToDate must be specified");
        }

        if (toDate.isBefore(fromDate)) {
            throw new NotValidException("ToDate must be after fromDate");
        }

        return socialNetworkPostRepo
                .findByPostDateBetween(fromDate,toDate);
    }

    public List<SocialNetworkPost> findSocialNetworkPostByDate (OffsetDateTime fromDate) {
        if (fromDate == null) {
            throw new NotValidException("FromDate must be specified");
        }

        OffsetDateTime dateTimeNow = OffsetDateTime.now();

        if (!fromDate.isBefore(dateTimeNow)) {
            throw new NotValidException("FromDate cannot be from future");
        }

        return socialNetworkPostRepo
                .findByPostDateBetween(fromDate,dateTimeNow);
    }

    public List<SocialNetworkPost> findTop10SocialNetworkPostByViewCount () {
        return socialNetworkPostRepo
                .findTop10ByViewCount();
    }

    /* UPDATE */
    public SocialNetworkPost updateSocialNetworkPost (SocialNetworkPost socialNetworkPost) {
        checkIfExistsById(socialNetworkPost.getId());
        return socialNetworkPostRepo.save(socialNetworkPost);
    }

    /* DELETE */
    public void deleteSocialNetworkPost (Long id) {
        checkIfExistsById(id);
        socialNetworkPostRepo.deleteById(id);
    }

    private void checkIfExistsById (Long id) {
        if (!socialNetworkPostRepo.existsById(id)) {
            throw new NotFoundException("Post with id: " + id + " does not exist");
        }
    }
}
