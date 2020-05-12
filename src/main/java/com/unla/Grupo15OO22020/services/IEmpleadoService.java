package com.unla.Grupo15OO22020.services;

import java.util.List;


import com.unla.Grupo15OO22020.entities.Empleado;
import com.unla.Grupo15OO22020.models.EmpleadoModel;

public interface IEmpleadoService {
		
	public List<Empleado> getAll();
	
	public EmpleadoModel findByIdPersona(long id);
	
	public EmpleadoModel findByNombre(String nombre);
	
	public EmpleadoModel insertOrUpdate(EmpleadoModel empleadoModel);
	
	public boolean remove(long idPersona);
	
	public List<EmpleadoModel> findByGerente(boolean gerente);

}
