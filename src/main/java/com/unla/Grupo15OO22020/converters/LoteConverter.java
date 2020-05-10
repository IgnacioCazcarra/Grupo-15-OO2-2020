package com.unla.Grupo15OO22020.converters;

import java.sql.Date;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unla.Grupo15OO22020.entities.Lote;
import com.unla.Grupo15OO22020.models.LoteModel;


@Component("loteConverter")
public class LoteConverter {
	@Autowired
	@Qualifier("productoConverter")
	private ProductoConverter productoConverter;
	
public	LoteModel entityToModel(Lote lote) {
	
		return new LoteModel(lote.getIdLote(), lote.getCantidadActual(), lote.getFechaIngreso() , productoConverter.entityToModel(lote.getProducto()), lote.getStock());
	}
	
	public Lote modelToEntity(LoteModel loteModel) {
		return new Lote(loteModel.getIdLote(), loteModel.getCantidadActual(), loteModel.getFechaIngreso() , productoConverter.modelToEntity(loteModel.getProducto()), loteModel.getStock());

	}
	
}