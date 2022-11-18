package com.spocin.akamai_homework.repos;

import com.spocin.akamai_homework.models.SocialNetworkPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SocialNetworkPostRepo extends JpaRepository<SocialNetworkPost, Long> {
    List<SocialNetworkPost> findByAuthor (String author);

    boolean existsByAuthor (String author);

    List<SocialNetworkPost> findByPostDateBetween (LocalDateTime fromDate, LocalDateTime toDate);

    @Query(value = "SELECT TOP(10) id, post_Date, author, content, view_Count  FROM social_network_post order by view_Count DESC, post_Date ASC", nativeQuery = true)
    List<SocialNetworkPost> findTop10ByViewCount();
}
