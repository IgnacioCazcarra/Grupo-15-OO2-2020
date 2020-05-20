package com.unla.Grupo15OO22020.entities;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="SolicitudStock")
public class SolicitudStock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idSolicitud;
	
	@Column(name="fecha")
	private Date fecha;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Producto producto;
	
	@Column(name="cantidad")
	private int cantidad;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="id_vendedor")
	private Empleado vendedor;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="id_colaborador")
	private Empleado colaborador;
	
	@Column(name="aceptado")
	private boolean aceptado;

	public SolicitudStock() {}
	
	public SolicitudStock(long idSolicitud, Date fecha, Producto producto, int cantidad, Empleado vendedor, Empleado colaborador,
			boolean aceptado) {
		super();
		this.idSolicitud = idSolicitud;
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
		this.vendedor = vendedor;
		this.colaborador = colaborador;
		this.aceptado = aceptado;
	}

	public long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Empleado getVendedor() {
		return vendedor;
	}

	public void setVendedor(Empleado vendedor) {
		this.vendedor = vendedor;
	}

	public Empleado getColaborador() {
		return colaborador;
	}

	public void setColaborador(Empleado colaborador) {
		this.colaborador = colaborador;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	
	
}
