package com.michael.devplace.repository;

import com.michael.devplace.entity.TechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechRepository extends JpaRepository<TechEntity, Integer> {
}
