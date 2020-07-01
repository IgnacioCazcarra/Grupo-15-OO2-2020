package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.Lote;
import com.unla.Grupo15OO22020.models.LoteModel;

public interface ILoteService {

	public List<Lote> getAll();
	
	public LoteModel insertOrUpdate(LoteModel loteModel);
	
	public LoteModel findByIdLote(long idLote);
	
	public boolean remove(long idLote);
	
}
