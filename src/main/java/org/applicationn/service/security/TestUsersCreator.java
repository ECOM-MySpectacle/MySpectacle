package org.applicationn.service.security;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserRole;
import org.applicationn.domain.security.UserStatus;

/**
 * Creates some test users in fresh database.
 * 
 * TODO This class is temporary for test, only. Just delete this class
 * if you do not need the test users to be created automatically.
 *
 */
@Singleton
@Startup
public class TestUsersCreator {

    private static final Logger logger = Logger.getLogger(TestUsersCreator.class.getName());
    
    @Inject
    private UserService userService;
    
    @PostConstruct
    public void postConstruct() {
        
       if(userService.countAllEntries() == 0) {
           
            logger.log(Level.WARNING, "Creating test user 'admin' with password 'admin'.");
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRoles(Arrays.asList(new UserRole[]{UserRole.Administrator}));
            admin.setStatus(UserStatus.Active);
            admin.setEmail("admin@domain.test");
            
            admin.setPrenom("x");
            
            admin.setNom("x");
            
            admin.setDateDeNaissance(new Date());
            
            admin.setAdresse("x");
            
            admin.setVille("x");
            
            admin.setCodePostal("x");
            
            userService.save(admin);
            
            logger.log(Level.WARNING, "Creating test user 'registered' with password 'registered'.");
            UserEntity registeredUser = new UserEntity();
            registeredUser.setUsername("registered");
            registeredUser.setPassword("registered");
            registeredUser.setRoles(Arrays.asList(new UserRole[]{UserRole.Registered}));
            registeredUser.setStatus(UserStatus.Active);
            registeredUser.setEmail("registered@domain.test");
            
            registeredUser.setPrenom("x");
            
            registeredUser.setNom("x");
            
            registeredUser.setDateDeNaissance(new Date());
            
            registeredUser.setAdresse("x");
            
            registeredUser.setVille("x");
            
            registeredUser.setCodePostal("x");
            
            userService.save(registeredUser);
            
            logger.log(Level.WARNING, "Creating test user 'superviseur' with password 'superviseur'.");
            UserEntity superviseurUser = new UserEntity();
            superviseurUser.setUsername("superviseur");
            superviseurUser.setPassword("superviseur");
            superviseurUser.setRoles(Arrays.asList(new UserRole[]{UserRole.Superviseur}));
            superviseurUser.setStatus(UserStatus.Active);
            superviseurUser.setEmail("superviseur@domain.test");
            
            superviseurUser.setPrenom("x");
            
            superviseurUser.setNom("x");
            
            superviseurUser.setDateDeNaissance(new Date());
            
            superviseurUser.setAdresse("x");
            
            superviseurUser.setVille("x");
            
            superviseurUser.setCodePostal("x");
            
            userService.save(superviseurUser);
            
            logger.log(Level.WARNING, "Creating test user 'gerant' with password 'gerant'.");
            UserEntity gerantUser = new UserEntity();
            gerantUser.setUsername("gerant");
            gerantUser.setPassword("gerant");
            gerantUser.setRoles(Arrays.asList(new UserRole[]{UserRole.Gerant}));
            gerantUser.setStatus(UserStatus.Active);
            gerantUser.setEmail("gerant@domain.test");
            
            gerantUser.setPrenom("x");
            
            gerantUser.setNom("x");
            
            gerantUser.setDateDeNaissance(new Date());
            
            gerantUser.setAdresse("x");
            
            gerantUser.setVille("x");
            
            gerantUser.setCodePostal("x");
            
            userService.save(gerantUser);
            
        }
    }
}
