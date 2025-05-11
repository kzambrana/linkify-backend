package com.linkify.linkify_be.service;

import com.linkify.linkify_be.api.CreateLinkRequest;
import com.linkify.linkify_be.dto.LinkResponseDTO;
import com.linkify.linkify_be.model.Link;
import com.linkify.linkify_be.model.Profile;
import com.linkify.linkify_be.repository.LinkRepository;
import com.linkify.linkify_be.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository, ProfileRepository profileRepository) {
        this.linkRepository = linkRepository;
        this.profileRepository = profileRepository;
    }

    public List<LinkResponseDTO> getLinksByProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .map(profile -> profile.getLinks().stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
    }

    public LinkResponseDTO createLink(Long profileId, CreateLinkRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        Link link = toEntity(request, profile);

        return toDto(linkRepository.save(link));
    }

    public LinkResponseDTO updateLink(Long linkId, CreateLinkRequest request) {
        Link existing = linkRepository.findById(linkId)
                .orElseThrow(() -> new IllegalArgumentException("Link not found"));

        existing.setPlatform(request.getPlatform());
        existing.setLink(request.getLink());
        existing.setIconPath(request.getIconPath());
        existing.setColor(request.getColor());

        return toDto(linkRepository.save(existing));
    }

    public void deleteLink(Long linkId) {
        linkRepository.deleteById(linkId);
    }

    private LinkResponseDTO toDto(Link link) {
        return LinkResponseDTO.builder()
                .id(link.getId())
                .platform(link.getPlatform())
                .link(link.getLink())
                .iconPath(link.getIconPath())
                .color(link.getColor())
                .build();
    }

    private Link toEntity(CreateLinkRequest request, Profile profile) {
        return Link.builder()
                .platform(request.getPlatform())
                .link(request.getLink())
                .iconPath(request.getIconPath())
                .color(request.getColor())
                .profile(profile)
                .build();
    }
}
