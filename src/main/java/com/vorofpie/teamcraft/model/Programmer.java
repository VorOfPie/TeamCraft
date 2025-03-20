package com.vorofpie.teamcraft.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programmers")
@ToString(exclude = "skillLevels")
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

    @OneToMany(mappedBy = "programmer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SkillLevel> skillLevels = new HashSet<>();


    // Метод для получения списка технологий и их уровней
    public List<String> getSkillsInfo() {
        return skillLevels.stream()
                .map(skillLevel -> skillLevel.getTechnology().getName() + ": " + skillLevel.getLevel())
                .collect(Collectors.toList());
    }
}
