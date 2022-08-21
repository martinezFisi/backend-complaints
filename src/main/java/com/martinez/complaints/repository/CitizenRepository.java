package com.martinez.complaints.repository;

import com.martinez.complaints.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long>, JpaSpecificationExecutor<Citizen> {

    Optional<Citizen> findCitizenByEmail(String email);

}
