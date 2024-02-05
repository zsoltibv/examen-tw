package com.lab.model.service;

import com.lab.model.model.MatchEntity;
import com.lab.model.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchEntity> getAllMatches() {
        return matchRepository.findAll();
    }
}
