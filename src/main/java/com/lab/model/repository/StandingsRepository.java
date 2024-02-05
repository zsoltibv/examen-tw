package com.lab.model.repository;

import com.lab.model.model.RoleEntity;
import com.lab.model.model.StandingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandingsRepository extends JpaRepository<StandingsEntity, Long> {
    Optional<StandingsEntity> findByTeamId(Long teamId);

}
