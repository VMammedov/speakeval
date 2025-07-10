package org.project.speakeval.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class About {
    @Id
    @GeneratedValue

    @Column(nullable = false)
    private  Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String tecnology;

    @Column(nullable = false)
    private String purpose;

}
