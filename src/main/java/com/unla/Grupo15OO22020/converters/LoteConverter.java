package com.unla.Grupo15OO22020.converters;

import java.util.ArrayList;
import java.util.List;

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

	public List<LoteModel> loteToLoteModel (List<Lote> lote) {
		List<LoteModel> nuevoLotes = new ArrayList<LoteModel>();
		for(int i = 0 ; i < lote.size(); i ++) {
			LoteModel loteEntidad = new LoteModel();
			loteEntidad.setIdLote(lote.get(i).getIdLote());
			loteEntidad.setCantidadActual(lote.get(i).getCantidadActual());
			loteEntidad.setCantidadInicial(lote.get(i).getCantidadInicial());
			loteEntidad.setEstado(lote.get(i).isEstado());
			loteEntidad.setFechaIngreso(lote.get(i).getFechaIngreso());
			loteEntidad.setProducto(productoConverter.entityToModel(lote.get(i).getProducto()));
			loteEntidad.setStock(lote.get(i).getStock());
			nuevoLotes.add(loteEntidad);
		}


		return nuevoLotes;
	}


}