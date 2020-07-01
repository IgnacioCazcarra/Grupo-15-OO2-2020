package com.unla.Grupo15OO22020.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.models.PedidoModel;

@Component("pedidoConverter")
public class PedidoConverter {

	@Autowired
	@Qualifier("productoConverter")
	private ProductoConverter productoConverter;

	@Autowired
	@Qualifier("clienteConverter")
	private ClienteConverter clienteConverter;

	@Autowired
	@Qualifier("empleadoConverter")
	private EmpleadoConverter empleadoConverter;

	@Autowired
	@Qualifier("localConverter")
	private LocalConverter localConverter;



	public PedidoModel entityToModel(Pedido pedido) {
		return new PedidoModel(pedido.getIdPedido(), pedido.getCantidad(),pedido.getFecha(), productoConverter.entityToModel(pedido.getProducto()),
				clienteConverter.entityToModel(pedido.getCliente()), localConverter.entityToModel(pedido.getLocal()), pedido.getSubtotal(), empleadoConverter.entityToModel(pedido.getVendedor()), 
				 empleadoConverter.entityToModel(pedido.getColaborador()), pedido.isAceptado());
	}

	public Pedido modelToEntity(PedidoModel pedido) {
		return new Pedido(pedido.getIdPedido(), pedido.getCantidad(),pedido.getFecha(), productoConverter.modelToEntity(pedido.getProducto()),
				clienteConverter.modelToEntity(pedido.getCliente()), localConverter.modelToEntity(pedido.getLocal()), pedido.getSubtotal(), empleadoConverter.modelToEntity(pedido.getVendedor()), 
				 empleadoConverter.modelToEntity(pedido.getColaborador()), pedido.isAceptado());
	}

}
