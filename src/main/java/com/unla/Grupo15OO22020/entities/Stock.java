package com.unla.Grupo15OO22020.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

import com.unla.Grupo15OO22020.models.LoteModel;

@Entity
@Table(name="stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idStock;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="stock")
	private List<Lote> lotes = new ArrayList<Lote>();
	
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Local local;
	
	@Column(name="codigo")
	private String codigo;
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

	public Stock(long idStock, Local local, String codigo) {
		super();
		this.idStock = idStock;
		this.local = local;
		this.codigo = codigo;
	}

	public long getIdStock() {
		return idStock;
	}

	public void setIdStock(long idStock) {
		this.idStock = idStock;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
	
	
	
	
}
