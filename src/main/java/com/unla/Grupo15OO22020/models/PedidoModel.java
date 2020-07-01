package com.unla.Grupo15OO22020.models;

import java.sql.Date;

import com.sun.istack.Nullable;

public class PedidoModel {

	private long idPedido;
	private int cantidad;
	private float subtotal;
	private Date fecha;
	private ProductoModel producto;
	private ClienteModel cliente;
	private LocalModel local;
	private boolean aceptado;
	private EmpleadoModel vendedor;
	@Nullable
	private EmpleadoModel colaborador;

	public PedidoModel() {

	}

	public PedidoModel(long idPedido, int cantidad, Date fecha, ProductoModel producto, ClienteModel cliente,  LocalModel local, float subtotal, EmpleadoModel vendedor, EmpleadoModel colaborador,
			boolean aceptado) {
		super();
		this.idPedido = idPedido;
		this.cantidad = cantidad;
		this.setProducto(producto);
		this.setCliente(cliente);
		this.local = local;
		this.subtotal = subtotal;
		this.vendedor = vendedor;
		this.colaborador = colaborador;
		this.aceptado = aceptado;
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

	public ProductoModel getProducto() {
		return producto;
	}

	public void setProducto(ProductoModel producto) {
		this.producto = producto;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public LocalModel getLocal() {
		return local;
	}

	public void setLocal(LocalModel local) {
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