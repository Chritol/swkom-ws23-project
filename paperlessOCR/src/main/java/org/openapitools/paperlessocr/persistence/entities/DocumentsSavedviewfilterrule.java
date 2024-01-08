package org.openapitools.paperlessocr.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
public class DocumentsSavedviewfilterrule {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private Integer ruleType;

    @Column
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saved_view_id", nullable = false)
    private DocumentsSavedview savedView;

}
