package com.vorofpie.teamcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technologies")
@ToString(exclude = "skillLevels")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_id")
    private Long technologyId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "is_required")
    private Boolean isRequired;

    @OneToMany(mappedBy = "technology", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<SkillLevel> skillLevels = new HashSet<>();


    public void addSkillLevel(SkillLevel skillLevel) {
        skillLevels.add(skillLevel);
        skillLevel.setTechnology(this);
    }
}
