package com.vorofpie.teamcraft.model;

import com.vorofpie.teamcraft.model.SkillLevel;
import jakarta.persistence.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    // Возвращаем лист с названиями технологий и уровнями
    public List<String> getSkillsInfo() {
        return skillLevels.stream()
                .map(skillLevel -> skillLevel.getTechnology().getName() + ": " + skillLevel.getLevel())
                .collect(Collectors.toList());
    }

    // Получаем уровень конкретной технологии
    public double getSkillLevelForTechnology(String techName) {
        return skillLevels.stream()
                .filter(skillLevel -> skillLevel.getTechnology().getName().equals(techName))
                .mapToDouble(SkillLevel::getLevel)
                .findFirst()
                .orElse(0.0); // Если технология не найдена, возвращаем 0
    }

    // JavaFX свойства
    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public SimpleIntegerProperty experienceYearsProperty() {
        return new SimpleIntegerProperty(experienceYears);
    }

    public SimpleStringProperty educationProperty() {
        return new SimpleStringProperty(education);
    }

    public SimpleStringProperty companyProperty() {
        return new SimpleStringProperty(company);
    }

    public ListProperty<String> skillsProperty() {
        return new SimpleListProperty<>(FXCollections.observableArrayList(getSkillsInfo()));
    }
}
