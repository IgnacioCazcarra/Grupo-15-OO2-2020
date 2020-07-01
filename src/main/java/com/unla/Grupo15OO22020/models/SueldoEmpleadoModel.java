package com.unla.Grupo15OO22020.models;

public class SueldoEmpleadoModel {

	private long idSueldoEmpleado;
	private String nombre;
	private String apellido;
	private long dni;
	private LocalModel local;
	private double sueldo;
	
	public SueldoEmpleadoModel(String nombre, String apellido, long dni, LocalModel local, double sueldo) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.local = local;
		this.sueldo = sueldo;
	}
	
	public long getIdSueldoEmpleado() {
		return idSueldoEmpleado;
	}
	public void setIdSueldoEmpleado(long idSueldoEmpleado) {
		this.idSueldoEmpleado = idSueldoEmpleado;
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
	public long getDni() {
		return dni;
	}
	public void setDni(long dni) {
		this.dni = dni;
	}
	public LocalModel getLocal() {
		return local;
	}
	public void setLocal(LocalModel local) {
		this.local = local;
	}
	public double getSueldo() {
		return sueldo;
	}
	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}
	
	
}
