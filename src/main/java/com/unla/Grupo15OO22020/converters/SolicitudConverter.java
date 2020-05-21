package com.unla.Grupo15OO22020.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unla.Grupo15OO22020.entities.SolicitudStock;
import com.unla.Grupo15OO22020.models.SolicitudStockModel;

@Component("solicitudConverter")
public class SolicitudConverter {

	@Autowired
	@Qualifier("productoConverter")
	private ProductoConverter productoConverter;
	
	@Autowired
	@Qualifier("empleadoConverter")
	private EmpleadoConverter empleadoConverter;
	
	public SolicitudStock modelToEntity(SolicitudStockModel s) {
		
		if(s.getColaborador()!=null) {
			System.out.println("ENTRAA");
			return new SolicitudStock(s.getIdSolicitud(), s.getFecha(), productoConverter.modelToEntity(s.getProducto()), s.getCantidad(), 
				empleadoConverter.modelToEntity(s.getVendedor()),
				empleadoConverter.modelToEntity(s.getColaborador()), s.isAceptado());
		}
		else {
			return new SolicitudStock(s.getIdSolicitud(), s.getFecha(), productoConverter.modelToEntity(s.getProducto()), s.getCantidad(), 
					empleadoConverter.modelToEntity(s.getVendedor()), null, s.isAceptado());
		}
	}
	
	public SolicitudStockModel entityToModel(SolicitudStock s) {
		if(s.getColaborador()!=null) {
			return new SolicitudStockModel(s.getIdSolicitud(), s.getFecha(), productoConverter.entityToModel(s.getProducto()), s.getCantidad(), 
					empleadoConverter.entityToModel(s.getVendedor()), empleadoConverter.entityToModel(s.getColaborador()), s.isAceptado());
		}
		else {
			return new SolicitudStockModel(s.getIdSolicitud(), s.getFecha(), productoConverter.entityToModel(s.getProducto()), s.getCantidad(), 
					empleadoConverter.entityToModel(s.getVendedor()), null, s.isAceptado());
		}
	}
	
}
