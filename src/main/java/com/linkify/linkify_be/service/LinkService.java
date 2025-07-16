package com.linkify.linkify_be.service;

import com.linkify.linkify_be.api.CreateLinkRequest;
import com.linkify.linkify_be.api.UpdateLinkRequest;
import com.linkify.linkify_be.dto.LinkResponseDTO;
import com.linkify.linkify_be.model.Link;
import com.linkify.linkify_be.model.Profile;
import com.linkify.linkify_be.repository.LinkRepository;
import com.linkify.linkify_be.repository.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

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

    public List<LinkResponseDTO> createLinks(Long profileId, CreateLinkRequest[] requests) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        List<Link> links = Arrays.stream(requests)
                .map(req -> toEntity(req, profile))
                .collect(Collectors.toList());

        List<Link> savedLinks = linkRepository.saveAll(links);

        return savedLinks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LinkResponseDTO> updateLinks(Long profileId, UpdateLinkRequest[] requests) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        List<Link> updatedLinks = Arrays.stream(requests)
                .map(req -> {
                    Optional<Link> existingLinkOptional = linkRepository.findById(req.getId());
                    if (existingLinkOptional.isEmpty()) {
                        throw new IllegalArgumentException(
                                "Link with ID " + req.getId() + " not found for profile " + profileId);
                    }
                    Link existingLink = existingLinkOptional.get();

                    if (!existingLink.getProfile().getId().equals(profileId)) {
                        throw new IllegalArgumentException(
                                "Link with ID " + req.getId() + " does not belong to profile " + profileId);
                    }

                    existingLink.setPlatform(req.getPlatform());
                    existingLink.setLink(req.getLink());
                    existingLink.setIconPath(req.getIconPath());
                    existingLink.setColor(req.getColor());

                    return existingLink;
                })
                .collect(Collectors.toList());

        List<Link> savedLinks = linkRepository.saveAll(updatedLinks);

        return savedLinks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void deleteLink(String linkId) {
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
