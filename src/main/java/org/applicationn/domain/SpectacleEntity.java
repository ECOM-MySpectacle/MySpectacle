package org.applicationn.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Spectacle")
@Table(name="\"SPECTACLE\"")
@XmlRootElement
public class SpectacleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    @Column(length = 50, name="\"nom\"")
    @NotNull
    private String nom;

    @Column(name="\"TYPE\"")
    @Enumerated(EnumType.STRING)
    private SpectacleType type;

    @ElementCollection(targetClass = SpectacleCible.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "SPECTACLE_CIBLE", joinColumns = { @JoinColumn(name = "SPECTACLE_ID") })
    @Column(name = "\"CIBLE\"")
    private Set<SpectacleCible> cible;

    @Size(max = 200)
    @Column(length = 200, name="\"sujet\"")
    private String sujet;

    @Column(name="\"creation\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date creation;

    @Size(max = 500)
    @Column(length = 500, name="\"description\"")
    private String description;

    @Column(name="\"artiste\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer artiste;

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SpectacleType getType() {
        return type;
    }

    public void setType(SpectacleType type) {
        this.type = type;
    }

    public Set<SpectacleCible> getCible() {
        return cible;
    }

    public void setCible(Set<SpectacleCible> cible) {
        this.cible = cible;
    }

    public String getSujet() {
        return this.sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Date getCreation() {
        return this.creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getArtiste() {
        return this.artiste;
    }

    public void setArtiste(Integer artiste) {
        this.artiste = artiste;
    }

}
