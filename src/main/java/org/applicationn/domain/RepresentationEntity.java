package org.applicationn.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(name="\"date\"")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Column(name="\"nbPlacesFosseLibres\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesFosseLibres;

    @Column(name="\"nbPlacesBalconLibres\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesBalconLibres;

    @Column(name="\"nbPlacesOrchestreLibres\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer nbPlacesOrchestreLibres;

    @ManyToOne(optional=true)
    @JoinColumn(name = "SALLE_ID", referencedColumnName = "ID")
    private SalleEntity salle;

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNbPlacesFosseLibres() {
        return this.nbPlacesFosseLibres;
    }

    public void setNbPlacesFosseLibres(Integer nbPlacesFosseLibres) {
        this.nbPlacesFosseLibres = nbPlacesFosseLibres;
    }

    public Integer getNbPlacesBalconLibres() {
        return this.nbPlacesBalconLibres;
    }

    public void setNbPlacesBalconLibres(Integer nbPlacesBalconLibres) {
        this.nbPlacesBalconLibres = nbPlacesBalconLibres;
    }

    public Integer getNbPlacesOrchestreLibres() {
        return this.nbPlacesOrchestreLibres;
    }

    public void setNbPlacesOrchestreLibres(Integer nbPlacesOrchestreLibres) {
        this.nbPlacesOrchestreLibres = nbPlacesOrchestreLibres;
    }

    public SalleEntity getSalle() {
        return this.salle;
    }

    public void setSalle(SalleEntity salle) {
        this.salle = salle;
    }

}
