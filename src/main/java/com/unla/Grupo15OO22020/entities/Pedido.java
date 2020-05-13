package com.unla.Grupo15OO22020.entities;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Producto producto;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Cliente cliente;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Empleado empleado;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Local local;
	
	@Column(name="createdat")
	@CreationTimestamp
	private LocalTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalTime updatedAt;
	
	public Pedido() {
		
	}

	public Pedido(long idPedido, int cantidad, float subtotal, boolean aceptado,Producto producto,Cliente cliente,Empleado empleado,Local local) {
		super();
		this.idPedido = idPedido;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
		this.aceptado = aceptado;
		this.producto = producto;
		this.cliente = cliente;
		this.empleado = empleado;
		this.local = local;
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

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
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

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	
}

