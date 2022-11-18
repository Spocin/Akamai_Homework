package com.spocin.akamai_homework.repos;

import com.spocin.akamai_homework.models.SocialNetworkPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface SocialNetworkPostRepo extends JpaRepository<SocialNetworkPost, Long> {
    List<SocialNetworkPost> findByAuthor (String author);

    boolean existsByAuthor (String author);

    List<SocialNetworkPost> findByPostDateBetween (OffsetDateTime fromDate, OffsetDateTime toDate);

    @Query(value = "SELECT TOP(10) Id, postDate, author, content, viewCount FROM SocialNetworkPost order by viewCount", nativeQuery = true)
    List<SocialNetworkPost> findTop10ByViewCount();
}
