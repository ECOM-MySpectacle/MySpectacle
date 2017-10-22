package org.applicationn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Panier")
@Table(name="\"PANIER\"")
@XmlRootElement
public class PanierEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="\"utilisateur\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer utilisateur;

    @Column(name="\"representation\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer representation;

    public Integer getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getRepresentation() {
        return this.representation;
    }

    public void setRepresentation(Integer representation) {
        this.representation = representation;
    }

}
