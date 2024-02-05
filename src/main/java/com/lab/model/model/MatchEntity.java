package com.lab.model.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "match")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private TeamEntity team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private TeamEntity team2;

    @Column(name = "score_team1")
    private int scoreTeam1;

    @Column(name = "score_team2")
    private int scoreTeam2;
}