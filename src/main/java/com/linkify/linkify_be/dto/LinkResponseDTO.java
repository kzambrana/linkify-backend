package com.linkify.linkify_be.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponseDTO {
    public Long id;
    public String platform;
    public String link;
    public String iconPath;
    public String color;
}
