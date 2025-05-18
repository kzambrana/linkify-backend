package com.linkify.linkify_be.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponseDTO {
    public String id;
    public String platform;
    public String link;
    public String iconPath;
    public String color;
}
