package org.applicationn.domain.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.applicationn.domain.BaseEntity;

@Entity(name="User")
@Table(name="\"USERS\"")
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    @NotNull
    private String username;

    @Size(max = 255)
    @NotNull
    private String password;

    @Size(max = 255)
    @NotNull
    private String salt;
    
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotNull
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // unique hash for account activation link
    @Size(max = 255)
    private String emailConfirmationKey;
    
    // unique hash for reset password link
    @Size(max = 255)
    private String emailResetPasswordKey;

    // date of generating reset password link
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordResetDate;
    
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = { @JoinColumn(name = "USER_ID") })
    @Column(name = "user_role")
    private List<UserRole> roles;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;
    
    @Column(name="\"age\"")
    @Digits(integer = 4, fraction = 0)
    private Integer age;

    @Size(max = 50)
    @Column(length = 50, name="\"nom\"")
    private String nom;

    @Size(max = 50)
    @Column(length = 50, name="\"prenom\"")
    private String prenom;

    @Size(max = 50)
    @Column(length = 50, name="\"numero\"")
    private String numero;

    @Size(max = 200)
    @Column(length = 200, name="\"adresse\"")
    private String adresse;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmailConfirmationKey() {
        return emailConfirmationKey;
    }

    public void setEmailConfirmationKey(String emailConfirmationKey) {
        this.emailConfirmationKey = emailConfirmationKey;
    }

    public String getEmailResetPasswordKey() {
        return emailResetPasswordKey;
    }

    public void setEmailResetPasswordKey(String emailResetPasswordKey) {
        this.emailResetPasswordKey = emailResetPasswordKey;
    }

    public Date getPasswordResetDate() {
        return passwordResetDate;
    }

    public void setPasswordResetDate() {
        this.passwordResetDate = new Date();
    }
    
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
