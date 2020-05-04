package com.unla.Grupo15OO22020.services;

import java.util.List;



import com.unla.Grupo15OO22020.entities.Cliente;
import com.unla.Grupo15OO22020.models.ClienteModel;

public interface IClienteService {

	public List<Cliente> getAll();
	
	public ClienteModel insertOrUpdate(ClienteModel clienteModel);
	
	public ClienteModel findByIdPersona(long idPersona);
	
	public boolean remove(long idPersona);
	
}
