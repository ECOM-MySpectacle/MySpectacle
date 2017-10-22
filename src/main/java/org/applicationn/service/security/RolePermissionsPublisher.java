package org.applicationn.service.security;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.applicationn.domain.security.RolePermission;
import org.applicationn.domain.security.UserRole;

@Singleton
@Startup
public class RolePermissionsPublisher {

    private static final Logger logger = Logger.getLogger(RolePermissionsPublisher.class.getName());
    
    @Inject
    private RolePermissionsService rolePermissionService;
    
    @PostConstruct
    public void postConstruct() {

        if (rolePermissionService.countAllEntries() == 0) {

            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "spectacle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "spectacle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "spectacle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "salle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "salle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "salle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "artiste:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "artiste:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "artiste:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "representation:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "representation:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Organisateur, "representation:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "panier:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "panier:read"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "panier:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "panier:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "user:*"));
            
            logger.info("Successfully created permissions for user roles.");
        }
    }
}
