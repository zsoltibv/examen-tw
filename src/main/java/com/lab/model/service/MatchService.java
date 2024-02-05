package com.lab.model.service;

import com.lab.model.model.MatchEntity;
import com.lab.model.model.StandingsEntity;
import com.lab.model.model.TeamEntity;
import com.lab.model.repository.MatchRepository;
import com.lab.model.repository.StandingsRepository;
import com.lab.model.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final StandingsRepository standingsRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository, StandingsRepository standingsRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.standingsRepository = standingsRepository;
    }

    public List<MatchEntity> getAllMatches() {
        return matchRepository.findAll();
    }

    public void addMatch(Long team1Id, Long team2Id, Long winnerId, Boolean isDraw) {
        // Fetch teams and winner from the repository
        TeamEntity team1 = teamRepository.findById(team1Id).orElseThrow();
        TeamEntity team2 = teamRepository.findById(team2Id).orElseThrow();
        TeamEntity winner = teamRepository.findById(winnerId).orElse(null);

        // Create a new match entity
        MatchEntity newMatch = new MatchEntity();
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        if (isDraw) {
            newMatch.setScoreTeam1(1);
            newMatch.setScoreTeam2(1);
        } else if (winnerId.equals(team1Id)) {
            newMatch.setScoreTeam1(3);
            newMatch.setScoreTeam2(0);
        } else {
            newMatch.setScoreTeam1(0);
            newMatch.setScoreTeam2(3);
        }
        // Save the new match
        matchRepository.save(newMatch);
        updateStandings(team1, team2, winner, isDraw);
    }

    private void updateStandings(TeamEntity team1, TeamEntity team2, TeamEntity winner, boolean isDraw) {
        // Update standings for team1
        updateTeamStandings(team1, winner, isDraw);

        // Update standings for team2
        updateTeamStandings(team2, winner, isDraw);
    }

    private void updateTeamStandings(TeamEntity team, TeamEntity winner, boolean isDraw) {
        StandingsEntity standings = standingsRepository.findByTeamId(team.getId()).orElse(new StandingsEntity());
        standings.setTeam(team);

        if (isDraw) {
            standings.setPoints(standings.getPoints() + 1);
        } else if (team.equals(winner)) {
            standings.setPoints(standings.getPoints() + 3);
        }

        // You might want to update goal difference logic here as well
        standings.setGoalDifference(standings.getGoalDifference() + calculateGoalDifference(team, winner, isDraw));
        standingsRepository.save(standings);
    }

    private int calculateGoalDifference(TeamEntity team, TeamEntity winner, boolean isDraw) {
        int goalDifference = 0;

        if (!isDraw) {
            // Calculate goal difference only for non-draw matches
            int team1Score = team.equals(winner) ? 3 : 0;
            int team2Score = team.equals(winner) ? 0 : 3;

            goalDifference = team1Score - team2Score;
        }

        return goalDifference;
    }
}
