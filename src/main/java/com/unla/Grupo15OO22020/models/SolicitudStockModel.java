package com.unla.Grupo15OO22020.models;

import java.sql.Date;
import java.time.LocalDate;

import com.sun.istack.Nullable;
import com.unla.Grupo15OO22020.entities.Empleado;

public class SolicitudStockModel {
	private Date fecha;
	private ProductoModel producto;
	private int cantidad;
	private EmpleadoModel vendedor;
	@Nullable
	private EmpleadoModel colaborador;
	private boolean aceptado;
	private long idSolicitud;
	
	public SolicitudStockModel() {}
	
	public SolicitudStockModel(long idSolicitud, Date fecha, ProductoModel producto, int cantidad, EmpleadoModel vendedor, EmpleadoModel colaborador, boolean aceptado) {
		super();
		this.idSolicitud = idSolicitud;
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
		this.vendedor = vendedor;
		this.colaborador = colaborador;
		this.aceptado = aceptado;
		this.idSolicitud = idSolicitud;
	}


	public long getIdSolicitud() {
		return idSolicitud;
	}


	protected void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ProductoModel getProducto() {
		return producto;
	}

	public void setProducto(ProductoModel producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public EmpleadoModel getVendedor() {
		return vendedor;
	}

	public void setVendedor(EmpleadoModel vendedor) {
		this.vendedor = vendedor;
	}

	public EmpleadoModel getColaborador() {
		return colaborador;
	}

	public void setColaborador(EmpleadoModel colaborador) {
			this.colaborador = colaborador;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}	
	
}