package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.Pedido;
import com.unla.Grupo15OO22020.models.PedidoModel;

public interface IPedidoService {
	
	public List<Pedido> getAll();
	
	public PedidoModel insertOrUpdate(PedidoModel pedidoModel);
	
	public PedidoModel findByIdPedido(long idPedido);
	
	public boolean remove(long idPedido);
	

}