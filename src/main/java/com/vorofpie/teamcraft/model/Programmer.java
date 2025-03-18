package com.vorofpie.teamcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programmers")
public class Programmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programmer_id")
    private Long programmerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "education")
    private String education;

    @Column(name = "company")
    private String company;

    @OneToMany(mappedBy = "programmer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SkillLevel> skillLevels = new HashSet<>();

    public void addSkillLevel(SkillLevel skillLevel) {
        skillLevels.add(skillLevel);
        skillLevel.setProgrammer(this);
    }
}
