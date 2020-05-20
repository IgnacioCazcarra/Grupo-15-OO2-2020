package com.unla.Grupo15OO22020.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.Grupo15OO22020.converters.SolicitudConverter;
import com.unla.Grupo15OO22020.entities.SolicitudStock;
import com.unla.Grupo15OO22020.models.SolicitudStockModel;
import com.unla.Grupo15OO22020.repositories.ISolicitudRepository;
import com.unla.Grupo15OO22020.services.ISolicitudService;

@Service("solicitudService")
public class SolicitudService implements ISolicitudService{

	@Autowired
	@Qualifier("solicitudRepository")
	private ISolicitudRepository solicitudRepository;
	
	@Autowired
	@Qualifier("solicitudConverter")
	private SolicitudConverter solicitudConverter;
	
	@Override
	public List<SolicitudStock> getAll() {
		return solicitudRepository.findAll();
	}

	@Override
	public SolicitudStockModel insertOrUpdate(SolicitudStockModel solicitudStockModel) {
		SolicitudStock solicitudStock = solicitudRepository.save(solicitudConverter.modelToEntity(solicitudStockModel));
		return solicitudConverter.entityToModel(solicitudStock);
	}

	@Override
	public SolicitudStockModel findByIdSolicitud(long idSolicitud) {
		return solicitudConverter.entityToModel(solicitudRepository.findByIdSolicitud(idSolicitud));
	}

	@Override
	public boolean remove(long idSolicitud) {
		try {
			solicitudRepository.deleteById(idSolicitud);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
