package com.unla.Grupo15OO22020.models;

public class PedidoModel {

	private long idPedido;
	private int cantidad;
	//private ClienteModel cliente;
	//private EmpleadoModel vendedorOriginal;
	//private EmpleadoModel vendedorAuxiliar;
	private float subtotal;
	//private boolean aceptado;
	private ProductoModel producto;
	private ClienteModel cliente;
	private EmpleadoModel empleado;
	private LocalModel local;
	private SolicitudStockModel solicitud;
	
	public PedidoModel() {
		
	}
	

	public PedidoModel(long idPedido, int cantidad, ProductoModel producto, ClienteModel cliente,EmpleadoModel empleado,LocalModel local, SolicitudStockModel solicitud, float subtotal) {
		super();
		this.idPedido = idPedido;
		this.cantidad = cantidad;
		this.setProducto(producto);
		this.setCliente(cliente);
		this.empleado = empleado;
		this.local = local;
		this.solicitud = solicitud;
		this.subtotal = subtotal;
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

	public SolicitudStockModel getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(SolicitudStockModel solicitud) {
		this.solicitud = solicitud;
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


	public EmpleadoModel getEmpleado() {
		return empleado;
	}


	public void setEmpleado(EmpleadoModel empleado) {
		this.empleado = empleado;
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
	
	
	/*
	public PedidoModel(ProductoModel producto, int cantidad, LocalModel local, ClienteModel cliente,
			EmpleadoModel vendedorOriginal, EmpleadoModel vendedorAuxiliar, boolean aceptado) {
		super();
		
		this.producto = producto;
		this.cantidad = cantidad;
		this.local = local;
		this.cliente = cliente;
		this.vendedorOriginal = vendedorOriginal;
		this.vendedorAuxiliar = vendedorAuxiliar;
		this.aceptado = aceptado;
		this.subtotal = CalcularSubtotal();
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

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public LocalModel getLocal() {
		return local;
	}

	public void setLocal(LocalModel local) {
		this.local = local;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public EmpleadoModel getVendedorOriginal() {
		return vendedorOriginal;
	}

	public void setVendedorOriginal(EmpleadoModel vendedorOriginal) {
		this.vendedorOriginal = vendedorOriginal;
	}

	public EmpleadoModel getVendedorAuxiliar() {
		return vendedorAuxiliar;
	}

	public void setVendedorAuxiliar(EmpleadoModel vendedorAuxiliar) {
		this.vendedorAuxiliar = vendedorAuxiliar;
	}

	public long getIdPedido() {
		return idPedido;
	}

	protected void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public float getTotal() {
		return subtotal;
	}

	public void setTotal(float subtotal) {
		this.subtotal = subtotal;
	}
		
	*/
	



}