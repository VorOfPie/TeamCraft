package com.vorofpie.teamcraft.model;

import com.vorofpie.teamcraft.model.Programmer;
import com.vorofpie.teamcraft.model.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill_levels")
public class SkillLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_level_id")
    private Long skillLevelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programmer_id", nullable = false)
    private Programmer programmer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    @Column(name = "level", nullable = false)
    private Double level;
}
