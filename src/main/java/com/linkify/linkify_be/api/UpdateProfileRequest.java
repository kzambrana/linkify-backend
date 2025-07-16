package com.linkify.linkify_be.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String image;
}
