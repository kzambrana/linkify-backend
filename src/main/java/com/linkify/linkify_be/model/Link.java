package com.linkify.linkify_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "link")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Platform cannot be empty")
    private String platform;

    @NotBlank(message = "Link cannot be empty")
    private String link;

    @NotBlank(message = "Icon path cannot be empty")
    private String iconPath;

    @NotBlank(message = "Color cannot be empty")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonBackReference
    private Profile profile;
}
