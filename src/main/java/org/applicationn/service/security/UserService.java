package org.applicationn.service.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserStatus;
import org.applicationn.service.BaseService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;
import org.primefaces.model.SortOrder;

@Named
public class UserService extends BaseService<UserEntity> implements Serializable {
    
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private static final long serialVersionUID = 1L;
    
    public UserService(){
        super(UserEntity.class);
    }

    @Named("users")
    @Transactional
    public List<UserEntity> findAllUserEntities() {
        return entityManager.createQuery("SELECT o FROM User o", UserEntity.class).getResultList();
    }

    @Transactional
    public UserEntity findUserByUsername(String username) {
        UserEntity user;
        try {
            user = entityManager.createQuery("SELECT o FROM User o WHERE o.username = :p", UserEntity.class)
                    .setParameter("p", username).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "User with user name ''{0}'' does not exist.", username);
            return null;
        }
        return user;
    }
    
    @Transactional
    public UserEntity findUserByEmail(String email) {
        UserEntity user;
        try {
            user = entityManager.createQuery("SELECT o FROM User o WHERE o.email = :p", UserEntity.class)
                    .setParameter("p", email).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "User with email ''{0}'' does not exist.", email);
            return null;
        }
        return user;
    }
    
    @Transactional
    public UserEntity findUserByEmailResetPasswordKey(String emailResetPasswordKey) {
        UserEntity user;
        try {
            user = entityManager.createQuery("SELECT o FROM User o WHERE o.emailResetPasswordKey = :p", UserEntity.class)
                    .setParameter("p", emailResetPasswordKey).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "User with passwort reset key ''{0}'' not found.", emailResetPasswordKey);
            return null;
        }
        return user;
    }
    
    @Transactional
    public UserEntity findUserByEmailConfirmationKey(String emailConfirmationKey) {
        UserEntity user;
        try {
            user = entityManager.createQuery("SELECT o FROM User o WHERE o.emailConfirmationKey = :p", UserEntity.class)
                    .setParameter("p", emailConfirmationKey).getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "User with activation key ''{0}'' not found.", emailConfirmationKey);
            return null;
        }
        return user;
    }
    
    @Override
    @Transactional
    public UserEntity save(UserEntity user){
        
        String salt = SecurityWrapper.generateSalt();
        user.setSalt(salt);
        
        user.setPassword(SecurityWrapper.hashPassword(user.getPassword(), salt));
        
        user.setCreatedAt(new Date());
        return super.save(user);
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM User o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(UserEntity user) {
        
        /* This is called before a User is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }
    
    @Override
    @Transactional
    public List<UserEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT o FROM User o");

        String nextConnective = " WHERE";
        
        Map<String, Object> queryParameters = new HashMap<>();
        
        if (filters != null && !filters.isEmpty()) {
            
            nextConnective += " ( ";
        
            for(String filterProperty : filters.keySet()) {
                
                switch (filterProperty) {
                
                case "username":
                    query.append(nextConnective).append(" o.username LIKE :username");
                    queryParameters.put("username", "%" + filters.get(filterProperty) + "%");
                    break;
                
                case "email":
                    query.append(nextConnective).append(" o.email LIKE :email");
                    queryParameters.put("email", "%" + filters.get(filterProperty) + "%");
                    break;
                    
                case "status":
                    query.append(nextConnective).append(" o.status = :status");
                    queryParameters.put("status", UserStatus.valueOf(filters.get(filterProperty).toString()));
                    break;
                
                case "age":
                    query.append(nextConnective).append(" o.age = :age");
                    queryParameters.put("age", new Integer(filters.get(filterProperty).toString()));
                    break;

                case "nom":
                    query.append(nextConnective).append(" o.nom LIKE :nom");
                    queryParameters.put("nom", "%" + filters.get(filterProperty) + "%");
                    break;

                case "prenom":
                    query.append(nextConnective).append(" o.prenom LIKE :prenom");
                    queryParameters.put("prenom", "%" + filters.get(filterProperty) + "%");
                    break;

                case "numero":
                    query.append(nextConnective).append(" o.numero LIKE :numero");
                    queryParameters.put("numero", "%" + filters.get(filterProperty) + "%");
                    break;

                case "adresse":
                    query.append(nextConnective).append(" o.adresse LIKE :adresse");
                    queryParameters.put("adresse", "%" + filters.get(filterProperty) + "%");
                    break;

                }
                
                nextConnective = " AND";
            }
            
            query.append(" ) ");
        }
        
        if (sortField != null && !sortField.isEmpty()) {
            query.append(" ORDER BY o.").append(sortField);
            query.append(SortOrder.DESCENDING.equals(sortOrder) ? " DESC" : " ASC");
        }
        
        TypedQuery<UserEntity> q = this.entityManager.createQuery(query.toString(), UserEntity.class);
        
        for(String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    /**
     * Method change user's password if oldPassword is correct and newPassword equals newPasswordRepeat
     * @param user
     * @param newPassword
     * @param newPasswordRepeat
     * @param oldPassword
     * @return 
     * */
    @Transactional
    public FacesMessage changePassword(UserEntity user, String newPassword, String newPasswordRepeat, String oldPassword){
        try {
            FacesMessage facesMessage;

            if (user.getPassword().equals(SecurityWrapper.hashPassword(oldPassword, user.getSalt()))) {
                facesMessage = this.changePassword(user, newPassword, newPasswordRepeat);

            } else {
                String message = "incorrect_password";
                facesMessage = MessageFactory.getMessage(message);
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            }

            return facesMessage;
        } catch (Throwable e){
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method change user's password if newPassword equals newPasswordRepeat.
     * This is less safety method since it doesn't check old password, we use it in forgot password handler only.
     * @param user
     * @param newPassword
     * @param newPasswordRepeat
     * @return 
     * */
    @Transactional
    public FacesMessage changePassword(UserEntity user, String newPassword, String newPasswordRepeat){
        try {
            FacesMessage facesMessage;

            if (!newPassword.equals("") && newPassword.equals(newPasswordRepeat)) {
                user.setPassword(SecurityWrapper.hashPassword(newPassword, user.getSalt()));

                this.update(user);
                facesMessage = MessageFactory.getMessage("password_successfully_changed");

            } else {
                String message = "error_while_changing_password";

                facesMessage = MessageFactory.getMessage(message);
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            }

            return facesMessage;
        } catch (Throwable e){
            throw new RuntimeException(e);
        }
    }
}
