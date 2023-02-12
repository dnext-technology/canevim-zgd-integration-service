package com.zorgundostu.integration.domain.identity.repository;


import com.zorgundostu.integration.domain.identity.model.identity.Identity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IdentityRepository extends JpaRepository<Identity, String> {
    Page<Identity> findAll(Pageable pageable);
}
