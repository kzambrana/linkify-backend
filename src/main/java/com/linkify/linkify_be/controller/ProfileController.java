package com.linkify.linkify_be.controller;

import com.linkify.linkify_be.api.CreateProfileRequest;
import com.linkify.linkify_be.api.UpdateProfileRequest;
import com.linkify.linkify_be.dto.ProfileResponseDTO;
import com.linkify.linkify_be.model.Profile;
import com.linkify.linkify_be.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Profile Controller", description = "Operations related to profile link management")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Get a profile by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Long id) {
        return profileService.getProfile(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all profiles")
    @GetMapping()
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        List<ProfileResponseDTO> profiles = profileService.getAllPProfiles();
        return ResponseEntity.ok(profiles);
    }


    @Operation(summary = "Create a new profile")
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody @Valid CreateProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.createProfile(request));
    }

    @Operation(summary = "Update an existing profile")
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequest profile) {
        return profileService.updateProfile(id, profile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
