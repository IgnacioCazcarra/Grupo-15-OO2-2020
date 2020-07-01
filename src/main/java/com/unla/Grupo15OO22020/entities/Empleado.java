package com.unla.Grupo15OO22020.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Empleado extends Persona {



	@Column(name = "franjaHoraria")
	private String franjaHoraria;

	@Column(name = "gerente")
	private boolean gerente;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "idLocal")
	private Local local;

	public Empleado() {

	}

	public Empleado(long idPersona, String nombre, String apellido, Date fechaNacimiento, long dni, String franjaHoraria,
			boolean gerente, Local local) {
		super(idPersona, nombre, apellido, fechaNacimiento, dni);
		this.franjaHoraria = franjaHoraria;
		this.gerente = gerente;
		this.local = local;
	}



	public String getFranjaHoraria() {
		return franjaHoraria;
	}

	public void setFranjaHoraria(String franjaHoraria) {
		this.franjaHoraria = franjaHoraria;
	}

	public boolean isGerente() {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	
	
}
