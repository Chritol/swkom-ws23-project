package org.openapitools.paperlessocr.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
public class DocumentsLog {

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

    @Column(name = "\"group\"")
    private UUID group;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private OffsetDateTime created;

}
