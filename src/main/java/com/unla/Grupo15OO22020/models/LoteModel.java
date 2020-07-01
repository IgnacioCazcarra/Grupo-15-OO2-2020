package com.unla.Grupo15OO22020.models;

import java.time.LocalDate;
import java.sql.Date;

import com.unla.Grupo15OO22020.entities.Stock;

public class LoteModel {

	private long idLote;
	private int cantidadInicial;
	private int cantidadActual;
	private Date fechaIngreso;
	private ProductoModel producto;
	private boolean estado;
	private Stock stock;
	
	public LoteModel() {

	}

	public LoteModel(long idLote, int cantidadInicial, int cantidadActual, Date fechaIngreso, ProductoModel producto, Stock stock) {
		super();
		this.idLote = idLote;
		this.cantidadInicial = cantidadInicial;
		this.cantidadActual = cantidadActual;
		this.fechaIngreso = fechaIngreso;
		this.producto = producto;
		this.estado = true;
		this.stock = stock;
	}

	
	
	
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public long getIdLote() {
		return idLote;
	}

	public void setIdLote(long idLote) {
		this.idLote = idLote;
	}

	public int getCantidadInicial() {
		return cantidadInicial;
	}

	public void setCantidadInicial(int cantidadInicial) {
		this.cantidadInicial = cantidadInicial;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public ProductoModel getProducto() {
		return producto;
	}

	public void setProducto(ProductoModel producto) {
		this.producto = producto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}


}