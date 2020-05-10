package com.unla.Grupo15OO22020.entities;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idStock;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="stock")
	private Set<Lote> lotes = new HashSet<Lote>();
	
	@Column(name="cantidad")
	private int cantidad;
	
	
	
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Local local;
	
	
	//@OneToOne(cascade = CascadeType.MERGE)
//	private Local local;
	
	
	//@OneToOne(cascade = CascadeType.MERGE)
	//private Local local;

	
//	@OneToOne
  //  @JoinColumn(name = "FK_STOCK", updatable = false, nullable = false)
  //  private Local local;
	
	@Column(name="createdat")
	@CreationTimestamp
	private LocalTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalTime updatedAt;

	
	
	
	
	public Stock() {
		super();
	}

	public Stock(long idStock, Local local) {
		super();
		this.idStock = idStock;
		this.cantidad = 0;
		this.local = local;
	}

	public long getIdStock() {
		return idStock;
	}

	public void setIdStock(long idStock) {
		this.idStock = idStock;
	}

	public Set<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(Set<Lote> lotes) {
		this.lotes = lotes;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public LocalTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
	
	
	
}
