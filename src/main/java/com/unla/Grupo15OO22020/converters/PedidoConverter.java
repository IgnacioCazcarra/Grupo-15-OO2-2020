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

	@Autowired
	@Qualifier("solicitudConverter")
	private SolicitudConverter solicitudConverter;

	public PedidoModel entityToModel(Pedido pedido) {
		return new PedidoModel(pedido.getIdPedido(), pedido.getCantidad(),
				productoConverter.entityToModel(pedido.getProducto()),
				clienteConverter.entityToModel(pedido.getCliente()),
				empleadoConverter.entityToModel(pedido.getEmpleado()), localConverter.entityToModel(pedido.getLocal()),
				solicitudConverter.entityToModel(pedido.getSolicitud()),pedido.getSubtotal());
	}

	public Pedido modelToEntity(PedidoModel pedidoModel) {
		return new Pedido(pedidoModel.getIdPedido(), pedidoModel.getCantidad(),
				productoConverter.modelToEntity(pedidoModel.getProducto()),
				clienteConverter.modelToEntity(pedidoModel.getCliente()),
				empleadoConverter.modelToEntity(pedidoModel.getEmpleado()),
				localConverter.modelToEntity(pedidoModel.getLocal()),
				solicitudConverter.modelToEntity(pedidoModel.getSolicitud()),pedidoModel.getSubtotal());
	}

}
