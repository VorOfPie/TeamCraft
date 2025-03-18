package com.vorofpie.teamcraft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thresholds")
public class Threshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_id")
    private Long thresholdId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    @Column(name = "min_level", nullable = false)
    private Double minLevel;

    @Column(name = "min_group_level", nullable = false)
    private Double minGroupLevel;
}