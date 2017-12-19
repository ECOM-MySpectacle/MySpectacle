package org.applicationn.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity(name = "Reservation")
@Table(name = "\"RESERVATION\"")
@XmlRootElement
public class ReservationEntity extends BaseEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Column(name = "\"nbPlaceFosse\"")
	@Digits(integer = 8, fraction = 0)
	@NotNull
	private Integer nbPlaceFosse;

	@Column(name = "\"nbPlaceBalcon\"")
	@Digits(integer = 8, fraction = 0)
	@NotNull
	private Integer nbPlaceBalcon;

	@Column(name = "\"nbPlaceOrchestre\"")
	@Digits(integer = 8, fraction = 0)
	@NotNull
	private Integer nbPlaceOrchestre;

	@Column(name = "\"email\"")
	private String email;

	@Column(name = "\"MODEPAIEMENT\"")
	@Enumerated(EnumType.STRING)
	private ReservationModePaiement modePaiement;

	@ManyToOne(optional = false)
	@JoinColumn(name = "REPRESENTATION_ID", referencedColumnName = "ID")
	private RepresentationEntity representation;

	public Integer getNbPlaceFosse()
	{
		return this.nbPlaceFosse;
	}

	public void setNbPlaceFosse(Integer nbPlaceFosse)
	{
		this.nbPlaceFosse = nbPlaceFosse;
	}

	public Integer getNbPlaceBalcon()
	{
		return this.nbPlaceBalcon;
	}

	public void setNbPlaceBalcon(Integer nbPlaceBalcon)
	{
		this.nbPlaceBalcon = nbPlaceBalcon;
	}

	public Integer getNbPlaceOrchestre()
	{
		return this.nbPlaceOrchestre;
	}

	public void setNbPlaceOrchestre(Integer nbPlaceOrchestre)
	{
		this.nbPlaceOrchestre = nbPlaceOrchestre;
	}

	public ReservationModePaiement getModePaiement()
	{
		return modePaiement;
	}

	public void setModePaiement(ReservationModePaiement modePaiement)
	{
		this.modePaiement = modePaiement;
	}

	public RepresentationEntity getRepresentation()
	{
		return this.representation;
	}

	public void setRepresentation(RepresentationEntity representation)
	{
		this.representation = representation;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
