package com.unla.Grupo15OO22020.entities;


<<<<<<< HEAD
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.FetchType;

import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
@Entity
@Table(name = "local")
=======
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="local")
>>>>>>> 97632ef2fb85451952e9a799f546dd0c5f3bd65f
public class Local {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLocal;
<<<<<<< HEAD

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "latitud")
	private String latitud;
	
	@Column(name = "longitud")
	private String longitud;

	@Column(name = "telefono")
	private String telefono;
	
	@ManyToMany(mappedBy = "listaLocales")
	private Set<Cliente> listaClientes = new HashSet<Cliente>();

//	@Column(name = "stock")
//	private StockModel stock;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tablaLocal")
//	private Set<Cliente> listaClientes = new HashSet<Cliente>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
	private Set<Empleado> listaEmpleados = new HashSet<Empleado>();
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tablaLocal")
//	private Set<Factura> listaFacturas = new HashSet<Factura>();
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tablaLocal")
//	private Set<SolicitudStock> listaSolicitudesStock = new HashSet<SolicitudStock>();

	@Column(name = "createdAt")
	@CreationTimestamp
	private LocalDateTime createdAt;
	@Column(name = "updatedAt")
	@CreationTimestamp
	private LocalDateTime updateAt;
	
	
	public Local() {}
	
	public Local(long idLocal, String direccion, String latitud, String longitud, String telefono) {
=======
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="latitud")
	private double latitud;
	
	@Column(name="longitud")
	private double longitud;
	
	@Column(name="telefono")
	private String telefono;
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id", referencedColumnName = "idStock")
    private Stock stock;
	
	
	@ManyToMany(mappedBy = "listaLocales")
	private Set<Cliente> listaClientes = new HashSet<Cliente>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
	private Set<Empleado> listaEmpleados = new HashSet<Empleado>();
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="tablaLocal")
//	private Set<Factura> listaFacturas = new HashSet<Factura>();
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="tablaLocal")
//	private Set<SolicitudStock> listaSolicitudesStock = new HashSet<SolicitudStock>();
	
	
	
	@Column(name="createdat")
	@CreationTimestamp
	private LocalTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalTime updatedAt;

	public Local() {
		super();
	}

	public Local(long idLocal, String direccion, double latitud, double longitud, String telefono) {
>>>>>>> 97632ef2fb85451952e9a799f546dd0c5f3bd65f
		super();
		this.idLocal = idLocal;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.telefono = telefono;
<<<<<<< HEAD
		
=======
	}

	

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
>>>>>>> 97632ef2fb85451952e9a799f546dd0c5f3bd65f
	}

	public long getIdLocal() {
		return idLocal;
	}


	public void setIdLocal(long idLocal) {
		this.idLocal = idLocal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

<<<<<<< HEAD
	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
=======
	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
>>>>>>> 97632ef2fb85451952e9a799f546dd0c5f3bd65f
		this.longitud = longitud;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(Set<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
<<<<<<< HEAD

}
=======
	
	
	
	
}

>>>>>>> 97632ef2fb85451952e9a799f546dd0c5f3bd65f
