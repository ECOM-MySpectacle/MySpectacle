package org.applicationn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.applicationn.domain.security.UserEntity;

@Entity(name="Salle")
@Table(name="\"SALLE\"")
@XmlRootElement
public class SalleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    @Column(length = 100, name="\"nom\"")
    @NotNull
    private String nom;

    @Size(max = 100)
    @Column(length = 100, name="\"adresse\"")
    @NotNull
    private String adresse;

    @Size(max = 100)
    @Column(length = 100, name="\"ville\"")
    @NotNull
    private String ville;

    @Column(name="\"nbPlacesFosse\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesFosse;

    @Column(name="\"nbPlacesBalcon\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesBalcon;

    @Column(name="\"nbPlacesOrchestre\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesOrchestre;

    @ManyToOne(optional=true)
    @JoinColumn(name = "GESTIONNAIRE_ID", referencedColumnName = "ID")
    private UserEntity gestionnaire;

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNbPlacesFosse() {
        return this.nbPlacesFosse;
    }

    public void setNbPlacesFosse(Integer nbPlacesFosse) {
        this.nbPlacesFosse = nbPlacesFosse;
    }

    public Integer getNbPlacesBalcon() {
        return this.nbPlacesBalcon;
    }

    public void setNbPlacesBalcon(Integer nbPlacesBalcon) {
        this.nbPlacesBalcon = nbPlacesBalcon;
    }

    public Integer getNbPlacesOrchestre() {
        return this.nbPlacesOrchestre;
    }

    public void setNbPlacesOrchestre(Integer nbPlacesOrchestre) {
        this.nbPlacesOrchestre = nbPlacesOrchestre;
    }

    public UserEntity getGestionnaire() {
        return this.gestionnaire;
    }

    public void setGestionnaire(UserEntity user) {
        this.gestionnaire = user;
    }

}
