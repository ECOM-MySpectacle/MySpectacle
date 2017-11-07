package org.applicationn.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="Spectacle")
@Table(name="\"SPECTACLE\"")
@XmlRootElement
public class SpectacleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private SpectacleImage image;
    
    @Size(max = 100)
    @Column(length = 100, name="\"nom\"")
    @NotNull
    private String nom;

    @Column(name="\"GENRE\"")
    @Enumerated(EnumType.STRING)
    private SpectacleGenre genre;

    @ElementCollection(targetClass = SpectaclePublicc.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "SPECTACLE_PUBLICC", joinColumns = { @JoinColumn(name = "SPECTACLE_ID") })
    @Column(name = "\"PUBLICC\"")
    private Set<SpectaclePublicc> publicc;

    @Size(max = 100)
    @Column(length = 100, name="\"theme\"")
    @NotNull
    private String theme;

    @Size(max = 300)
    @Column(length = 300, name="\"description\"")
    private String description;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name="SPECTACLE_ARTISTESS",
              joinColumns={@JoinColumn(name="SPECTACLE_ID", referencedColumnName="ID")},
              inverseJoinColumns={@JoinColumn(name="ARTISTES_ID", referencedColumnName="ID")})
    private List<ArtisteEntity> artistess;

    @XmlTransient
    public SpectacleImage getImage() {
        return image;
    }

    public void setImage(SpectacleImage image) {
        this.image = image;
    }
    
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SpectacleGenre getGenre() {
        return genre;
    }

    public void setGenre(SpectacleGenre genre) {
        this.genre = genre;
    }

    public Set<SpectaclePublicc> getPublicc() {
        return publicc;
    }

    public void setPublicc(Set<SpectaclePublicc> publicc) {
        this.publicc = publicc;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<ArtisteEntity> getArtistess() {
        return artistess;
    }

    public void setArtistess(List<ArtisteEntity> artistes) {
        this.artistess = artistes;
    }

}
