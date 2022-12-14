package com.spocin.akamai_homework.service;

import com.spocin.akamai_homework.exception.NotFoundException;
import com.spocin.akamai_homework.exception.NotValidException;
import com.spocin.akamai_homework.model.SocialNetworkPost;
import com.spocin.akamai_homework.repo.SocialNetworkPostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialNetworkPostService {
    private final SocialNetworkPostRepo socialNetworkPostRepo;

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

    public List<SocialNetworkPost> findSocialNetworkPostByDate (LocalDateTime fromDate, LocalDateTime toDate) {
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

    public List<SocialNetworkPost> findSocialNetworkPostByDate (LocalDateTime fromDate) {
        if (fromDate == null) {
            throw new NotValidException("FromDate must be specified");
        }

        LocalDateTime dateTimeNow = LocalDateTime.now();

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
