package org.openapitools.persistence.entities;

import javax.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class DocumentsSavedview {

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

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private Boolean showOnDashboard;

    @Column(nullable = false)
    private Boolean showInSidebar;

    @Column(length = 128)
    private String sortField;

    @Column(nullable = false)
    private Boolean sortReverse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AuthUser owner;

    @OneToMany(mappedBy = "savedView")
    private Set<DocumentsSavedviewfilterrule> savedViewDocumentsSavedviewfilterrules;

}
