package com.linkify.linkify_be.controller;

import com.linkify.linkify_be.api.CreateLinkRequest;
import com.linkify.linkify_be.api.UpdateLinkRequest;
import com.linkify.linkify_be.dto.LinkResponseDTO;
import com.linkify.linkify_be.service.LinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles/{profileId}/links")
@Tag(name = "Link Controller", description = "Operations related to links management")
public class LinkController {

    private final LinkService linkService;

    @Autowired  
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(summary = "Get all links for a profile")
    @GetMapping
    public ResponseEntity<List<LinkResponseDTO>> getLinks(@PathVariable Long profileId) {
        return new ResponseEntity<>(linkService.getLinksByProfile(profileId), HttpStatus.OK);
    }

    @Operation(summary = "Create new links for a profile")
    @PostMapping
    public ResponseEntity<List<LinkResponseDTO>> createLinks(@PathVariable Long profileId,
            @RequestBody @Valid CreateLinkRequest[] request) {
        return new ResponseEntity<>(linkService.createLinks(profileId, request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update links for a profile")
    @PutMapping()
    public ResponseEntity<List<LinkResponseDTO>> updateLinks(@PathVariable Long profileId,
            @RequestBody UpdateLinkRequest[] requests) {
        List<LinkResponseDTO> updatedLinks = linkService.updateLinks(profileId, requests);
        return ResponseEntity.ok(updatedLinks);
    }

    @Operation(summary = "Delete a link")
    @DeleteMapping("/{linkId}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long profileId, @PathVariable String linkId) {
        linkService.deleteLink(linkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}