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

            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "artiste:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "spectacle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "representation:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "salle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Superviseur, "salle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Superviseur, "salle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Superviseur, "salle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Gerant, "salle:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Gerant, "salle:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Gerant, "salle:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "reservation:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "reservation:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "reservation:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "user:*"));
            
            logger.info("Successfully created permissions for user roles.");
        }
    }
}
