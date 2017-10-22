package org.applicationn.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Representation")
@Table(name="\"REPRESENTATION\"")
@XmlRootElement
public class RepresentationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"spectacle\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer spectacle;

    @Column(name="\"salle\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer salle;

    @Column(name="\"date\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Column(name="\"nbPlaceFosseDisponible\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceFosseDisponible;

    @Column(name="\"nbPlaceBalconDisponible\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceBalconDisponible;

    @Column(name="\"nbPlaceOrchestreDisponible\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlaceOrchestreDisponible;

    public Integer getSpectacle() {
        return this.spectacle;
    }

    public void setSpectacle(Integer spectacle) {
        this.spectacle = spectacle;
    }

    public Integer getSalle() {
        return this.salle;
    }

    public void setSalle(Integer salle) {
        this.salle = salle;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNbPlaceFosseDisponible() {
        return this.nbPlaceFosseDisponible;
    }

    public void setNbPlaceFosseDisponible(Integer nbPlaceFosseDisponible) {
        this.nbPlaceFosseDisponible = nbPlaceFosseDisponible;
    }

    public Integer getNbPlaceBalconDisponible() {
        return this.nbPlaceBalconDisponible;
    }

    public void setNbPlaceBalconDisponible(Integer nbPlaceBalconDisponible) {
        this.nbPlaceBalconDisponible = nbPlaceBalconDisponible;
    }

    public Integer getNbPlaceOrchestreDisponible() {
        return this.nbPlaceOrchestreDisponible;
    }

    public void setNbPlaceOrchestreDisponible(Integer nbPlaceOrchestreDisponible) {
        this.nbPlaceOrchestreDisponible = nbPlaceOrchestreDisponible;
    }

}
