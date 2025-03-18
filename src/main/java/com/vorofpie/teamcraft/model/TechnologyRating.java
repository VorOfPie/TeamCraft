package com.vorofpie.teamcraft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technology_ratings")
public class TechnologyRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "source")
    private String source;

    @Column(name = "date")
    private LocalDate date;
}