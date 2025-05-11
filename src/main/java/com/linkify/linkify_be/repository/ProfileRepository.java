package com.linkify.linkify_be.repository;

import com.linkify.linkify_be.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
