package org.applicationn.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="Artiste")
@Table(name="\"ARTISTE\"")
@XmlRootElement
public class ArtisteEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private ArtisteImage image;
    
    @Size(max = 100)
    @Column(length = 100, name="\"nom\"")
    @NotNull
    private String nom;

    @Size(max = 300)
    @Column(length = 300, name="\"bio\"")
    private String bio;

    public void setSpectacless(List<SpectacleEntity> spectacles) {
        this.spectacless = spectacles;
    }

    @XmlTransient
    public List<SpectacleEntity> getSpectacless() {
        return spectacless;
    }

    @ManyToMany(mappedBy="artistess", fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<SpectacleEntity> spectacless;

    @XmlTransient
    public ArtisteImage getImage() {
        return image;
    }

    public void setImage(ArtisteImage image) {
        this.image = image;
    }
    
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
