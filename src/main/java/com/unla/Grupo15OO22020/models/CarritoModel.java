package com.unla.Grupo15OO22020.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CarritoModel {

	private long idCarrito;
	private Set<PedidoModel> listaPedidos = new HashSet<PedidoModel>();
	private LocalDate fecha;
	private float total;

	public CarritoModel() {
	}
	
	public CarritoModel(LocalDate fecha) {
		super();
		
		this.fecha = fecha;
		
		//this.total = this.calcularTotal();

	}

	public long getIdCarrito() {
		return idCarrito;
	}

	protected void setIdCarrito(int idCarrito) {
		this.idCarrito = idCarrito;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	

	protected Set<PedidoModel> getListaPedidos() {
		return listaPedidos;
	}

	protected void setListaPedidos(Set<PedidoModel> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}



	/**********************************************************************************************************************************************/
	//public boolean //altaPedido(Producto producto, int cantidad, Local local, Cliente cliente, Empleado vendedorOriginal,
			//Empleado vendedorAuxiliar) throws Exception{
		
		/*int id = 1;
		if (!this.getListaPedidos().isEmpty()) {
			id = this.getListaPedidos().get(this.getListaPedidos().size() - 1).getIdPedido() + 1;
		}
		*///error en el get al set 
		
		//if(!local.getStock().stockValido(producto, cantidad)) throw new Exception ("ERROR: El stock del local no alcanza para realizar el pedido");
		
		//local.getStock().consumoStock(producto, cantidad);
		//return this.getListaPedidos().add(new Pedido(id, producto, cantidad, local, cliente, vendedorOriginal, vendedorAuxiliar, false)); tirar error porque el id ya no esta.
		
	//}

	/**********************************************************************************************************************************************/
	/*public void modificarPedido(int idPedido, ProductoModel producto, int cantidad, LocalModel local, ClienteModel cliente,
			EmpleadoModel vendedorOriginal, EmpleadoModel vendedorAuxiliar) throws Exception {
		if (traerPedido(idPedido) == null)
			throw new Exception("ERROR: No se encontro el pedido");
		PedidoModel p = traerPedido(idPedido);
		p.setVendedorOriginal(vendedorOriginal);
		p.setVendedorAuxiliar(vendedorAuxiliar);
		p.setTotal(p.CalcularSubtotal());
		p.setProducto(producto);
		p.setCantidad(cantidad);
		p.setCliente(cliente);
	}

	/**********************************************************************************************************************************************/
	/*public boolean bajaPedido(int idPedido) throws Exception {
		if (traerPedido(idPedido) == null)
			throw new Exception("ERROR: La ID ingresada no pertenece a ningun pedido");
		return listaPedidos.remove(traerPedido(idPedido));
	}

	/**********************************************************************************************************************************************/
	public PedidoModel traerPedido(int idPedido) {
		int i = 0;
		PedidoModel pedidoEncontrado = null;
		/*while (i < listaPedidos.size() && pedidoEncontrado == null) {
			if (listaPedidos.get(i).getIdPedido() == idPedido) {
				pedidoEncontrado = listaPedidos.get(i);
			}
			i++;
		}
		*///mismo error del get
		return pedidoEncontrado;
	}

	/**********************************************************************************************************************************************/

	/*public float calcularTotal() {
		float total = 0;

		for (PedidoModel p : listaPedidos) {
			total += p.CalcularSubtotal();
		}

		return total;
	}*/

}