package org.openapitools.mapper;

import org.openapitools.persistence.entities.*;
import org.openapitools.persistence.repositories.*;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.PermissionsView;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Service
public abstract class PermissionsViewMapper implements BaseMapper<AuthPermission, PermissionsView> {

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
    private AuthPermissionRepository  permissionRepository;

    @Mapping(target = "permissionUserPermissions", source = "users", qualifiedByName = "usersDto")
    @Mapping(target = "permissionGroupPermissions", source = "groups", qualifiedByName = "groupsDto")
    abstract public AuthPermission dtoToEntity(PermissionsView dto);

    @Mapping(target = "users", source = "permissionUserPermissions", qualifiedByName = "usersEntity")
    @Mapping(target = "groups", source = "permissionGroupPermissions", qualifiedByName = "groupsEntity")
    abstract public PermissionsView entityToDto(AuthPermission entity);

    @Named("usersEntity")
    List<Integer> map1(Set<AuthUserUserPermissions> userPermissions) {
        return userPermissions.stream().map( userPermission->(int)userPermission.getUser().getId() ).toList();
    }

    @Named("groupsEntity")
    List<Integer> map2(Set<AuthGroupPermissions> groupPermissions) {
        return groupPermissions.stream().map( groupPermission->(int)groupPermission.getGroup().getId() ).toList();
    }

    @Named("usersDto")
    Set<AuthUserUserPermissions> map3(List<Integer> users) {
        Set<AuthUserUserPermissions> userPermissions = new HashSet<>();
        for (Integer user : users) {
            AuthUserUserPermissions userPermission = new AuthUserUserPermissions();
            userPermission.setUser(userRepository.getReferenceById(user));
            userPermissions.add(userPermission);
        }
        return userPermissions;
    }

    @Named("groupsDto")
    Set<AuthGroupPermissions> map4(List<Integer> groups) {
        Set<AuthGroupPermissions> groupPermissions = new HashSet<>();
        for (Integer group : groups) {
            AuthGroupPermissions groupPermission = new AuthGroupPermissions();
            groupPermission.setGroup(groupRepository.getReferenceById(group));
            groupPermissions.add(groupPermission);
        }
        return groupPermissions;
    }

}
