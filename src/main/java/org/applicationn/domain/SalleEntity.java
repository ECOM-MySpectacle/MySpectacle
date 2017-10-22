package org.applicationn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Salle")
@Table(name="\"SALLE\"")
@XmlRootElement
public class SalleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    @Column(length = 100, name="\"nom\"")
    @NotNull
    private String nom;

    @Size(max = 200)
    @Column(length = 200, name="\"adresse\"")
    @NotNull
    private String adresse;

    @Column(name="\"nbPlaceFosse\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceFosse;

    @Column(name="\"nbPlaceBalcon\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceBalcon;

    @Column(name="\"nbPlaceOrchestre\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceOrchestre;

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

    public Integer getNbPlaceFosse() {
        return this.nbPlaceFosse;
    }

    public void setNbPlaceFosse(Integer nbPlaceFosse) {
        this.nbPlaceFosse = nbPlaceFosse;
    }

    public Integer getNbPlaceBalcon() {
        return this.nbPlaceBalcon;
    }

    public void setNbPlaceBalcon(Integer nbPlaceBalcon) {
        this.nbPlaceBalcon = nbPlaceBalcon;
    }

    public Integer getNbPlaceOrchestre() {
        return this.nbPlaceOrchestre;
    }

    public void setNbPlaceOrchestre(Integer nbPlaceOrchestre) {
        this.nbPlaceOrchestre = nbPlaceOrchestre;
    }

}
