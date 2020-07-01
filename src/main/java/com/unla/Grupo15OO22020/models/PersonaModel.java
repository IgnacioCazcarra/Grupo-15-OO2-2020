package com.unla.Grupo15OO22020.models;


import java.sql.Date;

public abstract class PersonaModel {

	protected long idPersona;
	protected String nombre;
	protected String apellido;
	protected Date fechaNacimiento;
	protected long dni;

	public PersonaModel() {}
	
	public PersonaModel(long idPersona, String nombre, String apellido, Date fechaNacimiento, long dni) {

		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
	
		this.fechaNacimiento=fechaNacimiento;
	}

	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	public long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	

	

}