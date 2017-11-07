package org.applicationn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Reservation")
@Table(name="\"RESERVATION\"")
@XmlRootElement
public class ReservationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Column(name="\"MODEPAIEMENT\"")
    @Enumerated(EnumType.STRING)
    private ReservationModePaiement modePaiement;

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

    public ReservationModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ReservationModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

}
