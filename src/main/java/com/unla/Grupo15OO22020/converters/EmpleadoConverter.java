package com.unla.Grupo15OO22020.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unla.Grupo15OO22020.entities.Empleado;
import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.models.EmpleadoModel;
import com.unla.Grupo15OO22020.models.LocalModel;


@Component("empleadoConverter")
public class EmpleadoConverter {

	@Autowired
	@Qualifier("localConverter")
	private LocalConverter localConverter;
	
	
	public EmpleadoModel entityToModel(Empleado empleado) {
		if(empleado!=null)
			return new EmpleadoModel(empleado.getIdPersona(), empleado.getNombre(), empleado.getApellido(), empleado.getFechaNacimiento(),
					empleado.getDni(), empleado.getFranjaHoraria(), empleado.isGerente(), localConverter.entityToModel(empleado.getLocal()));
		else return null;
	}

	public Empleado modelToEntity(EmpleadoModel empleadoModel) {
		if(empleadoModel!=null)
			return new Empleado(empleadoModel.getIdPersona(),empleadoModel.getNombre(), empleadoModel.getApellido(), empleadoModel.getFechaNacimiento(),
				empleadoModel.getDni(), empleadoModel.getFranjaHoraria(), empleadoModel.isGerente(), localConverter.modelToEntity(empleadoModel.getLocal()));
		else return null;
	}

}
