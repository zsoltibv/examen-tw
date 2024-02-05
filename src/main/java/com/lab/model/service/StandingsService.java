package com.lab.model.service;

import com.lab.model.model.StandingsEntity;
import com.lab.model.repository.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingsService {
    private final StandingsRepository standingsRepository;

    @Autowired
    public StandingsService(StandingsRepository standingsRepository) {
        this.standingsRepository = standingsRepository;
    }

    public List<StandingsEntity> getAllStandings() {
        // Assuming you have a method in your repository to fetch all standings
        return standingsRepository.findAll(Sort.by(Sort.Order.desc("points")));
    }
}
