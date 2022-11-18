package com.spocin.akamai_homework.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SocialNetworkPostCreationDTO {
    @NotBlank
    @Length(max = 255)
    private String author;

    @NotBlank
    @Length(max = 1000)
    private String content;
}
