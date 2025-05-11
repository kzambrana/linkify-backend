package com.linkify.linkify_be.dto;

import java.util.List;

import com.linkify.linkify_be.model.Link;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private List<Link> links;
}
