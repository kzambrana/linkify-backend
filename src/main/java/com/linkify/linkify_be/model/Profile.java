package com.linkify.linkify_be.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "profile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name cannot be empty")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty")
  private String lastName;

  @NotBlank(message = "Email cannot be empty")
  private String email;

  @NotBlank(message = "Image cannot be empty")
  private String image;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Link> links;
}
