package org.openapitools.persistence.entities;

import javax.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PaperlessMailMailrule {

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
    private String name;

    @Column(nullable = false, length = 256)
    private String folder;

    @Column(length = 256)
    private String filterFrom;

    @Column(length = 256)
    private String filterSubject;

    @Column(length = 256)
    private String filterBody;

    @Column(nullable = false)
    private Integer maximumAge;

    @Column(nullable = false)
    private Integer action;

    @Column(length = 256)
    private String actionParameter;

    @Column(nullable = false)
    private Integer assignTitleFrom;

    @Column(nullable = false)
    private Integer assignCorrespondentFrom;

    @Column(nullable = false, name = "\"order\"")
    private Integer order;

    @Column(nullable = false)
    private Integer attachmentType;

    @Column(length = 256)
    private String filterAttachmentFilename;

    @Column(nullable = false)
    private Integer consumptionScope;

    @Column(length = 256)
    private String filterTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private PaperlessMailMailaccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_correspondent_id")
    private DocumentsCorrespondent assignCorrespondent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_document_type_id")
    private DocumentsDocumenttype assignDocumentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AuthUser owner;

    @OneToMany(mappedBy = "rule")
    private Set<PaperlessMailProcessedmail> rulePaperlessMailProcessedmails;

    @OneToMany(mappedBy = "mailrule")
    private Set<PaperlessMailMailruleAssignTags> mailrulePaperlessMailMailruleAssignTagses;

}
