package org.project.speakeval.repository;

import org.project.speakeval.domain.About;
import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepository extends  JpaRepository<About,Long> {
}
