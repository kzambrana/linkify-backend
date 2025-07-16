package com.linkify.linkify_be.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkRequest {
    private String id;
    private String platform;
    private String link;
    private String iconPath;
    private String color;
}
