package com.unla.Grupo15OO22020.entities;

import java.sql.Date;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sun.istack.Nullable;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPedido;
	
	@Column(name = "cantidad")
	private int cantidad;
	
	@Column(name = "subtotal")
	private float subtotal;
	
	@Column(name = "aceptado")
	private boolean aceptado;
	
	@Column(name="fecha")
	private Date fecha;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Producto producto;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cliente cliente;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="id_vendedor")
	private Empleado vendedor;
	
	@Nullable
	@OneToOne(cascade = CascadeType.MERGE, optional=true)
	@JoinColumn(name="id_colaborador")
	private Empleado colaborador;
	
	@OneToOne
	private Local local;
	

	
	@Column(name="createdat")
	@CreationTimestamp
	private LocalTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalTime updatedAt;
	
	public Pedido() {
		
	}

	public Pedido(long idPedido, int cantidad, Date fecha, Producto producto,Cliente cliente,Local local,  float subtotal, Empleado vendedor, Empleado colaborador, boolean aceptado) {
		super();
		this.idPedido = idPedido;
		this.cantidad = cantidad;
		this.producto = producto;
		this.cliente = cliente;
		this.local = local;
		this.subtotal = subtotal;
		this.aceptado = aceptado;
		this.vendedor = vendedor;
		this.colaborador = colaborador;
		this.fecha = fecha;
	}

	public long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public float CalcularSubtotal() {
		return producto.getPrecio()*cantidad;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
}

