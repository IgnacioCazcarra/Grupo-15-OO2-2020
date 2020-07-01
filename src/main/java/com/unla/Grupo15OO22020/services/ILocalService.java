package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.models.LocalModel;

public interface ILocalService {

	public List<Local> getAll();
	
	public LocalModel insertOrUpdate(LocalModel localModel);
	
	public LocalModel findByIdLocal(long idLocal);
	
	public boolean remove(long idLocal);
	
}