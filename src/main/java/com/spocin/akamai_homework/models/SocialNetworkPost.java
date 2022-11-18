package com.spocin.akamai_homework.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SocialNetworkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime postDate;

    @NotBlank
    @Length(max = 255)
    private String author;

    @NotBlank
    @Length(max = 1000)
    private String content;

    @NotNull
    @Min(0)
    private Long viewCount;
}
