package org.openapitools.mapper;

import org.openapitools.persistence.entities.*;
import org.openapitools.persistence.repositories.*;
import org.openapitools.model.Permissions;
import org.openapitools.model.PermissionsView;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Service
public abstract class PermissionsMapper implements BaseMapper<AuthUser, Permissions> {

    @Autowired
    private DocumentsCorrespondentRepository correspondentRepository;
    @Autowired
    private DocumentsDocumenttypeRepository documentTypeRepository;
    @Autowired
    private DocumentsStoragepathRepository storagePathRepository;
    @Autowired
    private AuthUserRepository userRepository;
    @Autowired
    private AuthGroupRepository groupRepository;
    @Autowired
    private DocumentsDocumentTagsRepository documentTagsRepository;
    @Autowired
    private AuthPermissionRepository permissionRepository;

    @Mapping(target = "view", source = "id", qualifiedByName = "viewEntity")
    @Mapping(target = "change", source = "id", qualifiedByName = "changeEntity")
    abstract public Permissions entityToDto(AuthUser entity);

    @Named("viewEntity")
    PermissionsView map1(Integer id) {
        if(id==null)
            return new PermissionsView();
        return new PermissionsView().addUsersItem(id);
    }

    @Named("changeEntity")
    PermissionsView map2(Integer id) {
        if(id==null)
            return new PermissionsView();
        return new PermissionsView().addUsersItem(id);
    }

}
