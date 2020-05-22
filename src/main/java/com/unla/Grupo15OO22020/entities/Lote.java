package com.unla.Grupo15OO22020.entities;


import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="lote")
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLote;
	
	@Column(name="cantidadInicial")
	private int cantidadInicial;
	
	@Column(name="cantidadActual")
	private int cantidadActual;
	
	@Column(name="fechaIngreso")
	//@CreationTimestamp
	private Date fechaIngreso;
	
	//@OneToOne(cascade = CascadeType.ALL)
	@OneToOne(cascade = CascadeType.MERGE)
  // @JoinColumn(name = "producto_idProducto", referencedColumnName = "idProducto")
    private Producto producto;
	
	
	@Column(name="estado")
	private boolean estado;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stock_idStock", nullable=false)
	private Stock stock;
	
	
	@Column(name="createdat")
	@CreationTimestamp
	private LocalTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalTime updatedAt;

	public Lote() {
		super();
	}

	public Lote(long idLote, int cantidadInicial, int cantidadActual, Date date, Producto producto, Stock stock) {
		super();
		this.idLote = idLote;
		this.cantidadInicial = cantidadInicial;
		this.cantidadActual = cantidadActual;
		this.fechaIngreso =  date;
		this.producto = producto;
		this.estado = true;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	
		
	
	
	
	
}