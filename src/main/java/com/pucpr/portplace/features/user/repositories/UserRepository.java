package com.pucpr.portplace.features.user.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.enums.UserStatusEnum;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsById(long id);

    @Query("""
        SELECT u FROM User u
        WHERE (:includeDisabled = true OR u.disabled = false)        
        AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR u.status IN :status)
    """)
    Page<User> findAllByFilters(
        boolean includeDisabled, 
        String searchQuery, 
        List<UserStatusEnum> status,
        Pageable pageable
    );

}