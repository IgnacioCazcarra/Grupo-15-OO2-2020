package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.SolicitudStock;
import com.unla.Grupo15OO22020.models.SolicitudStockModel;


public interface ISolicitudService {

public List<SolicitudStock> getAll();
	
	public SolicitudStockModel insertOrUpdate(SolicitudStockModel solicitudStockModel);
	
	public SolicitudStockModel findByIdSolicitud(long idSolicitud);
	
	public boolean remove(long idSolicitud);
}
