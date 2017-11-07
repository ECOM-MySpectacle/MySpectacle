package org.applicationn.service.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.SimpleByteSource;

/**
 * Central wrapping of the Apache Shiro security library
 */
@Named("security")
public class SecurityWrapper implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SecurityWrapper.class.getName());
    
    // Because Shiro does not support public permissions, we need to workaround them:
    private static final Set<String> publicPermissions = 
            new HashSet<>(Arrays.asList(new String[]{"artiste:read" ,"artiste:read" ,"artiste:read" ,"artiste:read" ,"artiste:read" ,"spectacle:read" ,"spectacle:read" ,"spectacle:read" ,"spectacle:read" ,"spectacle:read" ,"representation:read" ,"representation:read" ,"representation:read" ,"representation:read" ,"representation:read" ,"salle:read" ,"salle:read" ,"salle:read" ,"salle:read" ,"salle:read" ,"reservation:read" ,"reservation:read" ,"reservation:read" ,"reservation:read" ,"reservation:read"}));
    
    public static boolean login(String username, String password, boolean rememberMe) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, "AuthenticationException", e);
            return false;
        }
        return true;
    }
    
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
    
    public static String getUsername() {
        
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            return null;
        }
        
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
    
    public static boolean isPermitted(String permission) {
        return publicPermissions.contains(permission)
                || SecurityUtils.getSubject().isPermitted(permission);
    }

    public static boolean hasReadPermissionOnlyOwner(String entity) {
        return !SecurityUtils.getSubject().isPermitted(entity + ":read")
                && SecurityUtils.getSubject().isPermitted(entity + ":read:owner");
    }
    
    public static String generateSalt() {
        return new BigInteger(250, new SecureRandom()).toString(32);
    }
    
    public static String hashPassword(String password, String salt) {
        Sha512Hash hash = new Sha512Hash(password, (new SimpleByteSource(salt)).getBytes());
        return hash.toHex();
    }
}
