package com.linkify.linkify_be.service;

import com.linkify.linkify_be.api.CreateProfileRequest;
import com.linkify.linkify_be.api.UpdateProfileRequest;
import com.linkify.linkify_be.dto.ProfileResponseDTO;
import com.linkify.linkify_be.model.Profile;
import com.linkify.linkify_be.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<ProfileResponseDTO> getProfile(Long id) {
        return profileRepository.findById(id)
                .map(this::toDto);
    }

    public List<ProfileResponseDTO> getAllPProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ProfileResponseDTO createProfile(CreateProfileRequest request) {
        Profile profile = toEntity(request);
        profile = profileRepository.save(profile);
        return toDto(profile);
    }

    public Optional<ProfileResponseDTO> updateProfile(Long id, UpdateProfileRequest updatedProfile) {
        return profileRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updatedProfile.firstName);
                    existing.setLastName(updatedProfile.lastName);
                    existing.setEmail(updatedProfile.email);
                    existing.setImage(updatedProfile.image);
                    Profile saved = profileRepository.save(existing);
                    return toDto(saved);
                });
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    private ProfileResponseDTO toDto(Profile profile) {
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .image(profile.getImage())
                .links(profile.getLinks())
                .build();
    }

    private Profile toEntity(CreateProfileRequest request) {
        return Profile.builder()
                .firstName(request.firstName)
                .lastName(request.lastName)
                .email(request.email)
                .image(request.image)
                .links(null)
                .build();
    }

}
