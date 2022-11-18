package com.spocin.akamai_homework.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record SocialNetworkPostCreateDTO (
        @NotBlank
        @Length(max = 255)
        String author,

        @NotBlank
        @Length(max = 1000)
        String content
) { }
