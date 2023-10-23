package org.openapitools.persistence.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PaperlessMailProcessedmail {

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

    @Column(nullable = false, length = 256)
    private String folder;

    @Column(nullable = false, length = 256)
    private String uid;

    @Column(nullable = false, length = 256)
    private String subject;

    @Column(nullable = false)
    private OffsetDateTime received;

    @Column(nullable = false)
    private OffsetDateTime processed;

    @Column(nullable = false, length = 256)
    private String status;

    @Column(columnDefinition = "text")
    private String error;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AuthUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false)
    private PaperlessMailMailrule rule;

}
