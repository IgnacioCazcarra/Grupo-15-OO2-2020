package com.unla.Grupo15OO22020.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.Grupo15OO22020.converters.StockConverter;
import com.unla.Grupo15OO22020.entities.Stock;
import com.unla.Grupo15OO22020.models.StockModel;
import com.unla.Grupo15OO22020.repositories.IStockRepository;
import com.unla.Grupo15OO22020.services.IStockService;


@Service("stockService")
public class StockService implements IStockService{

	@Autowired
	@Qualifier("stockRepository")
	private IStockRepository stockRepository;
	
	@Autowired
	@Qualifier("stockConverter")
	private StockConverter stockConverter;
	
	@Autowired
	@Qualifier("localService")
	private LocalService localService;
	
	@Override
	public List<Stock> getAll(){
		
		return stockRepository.findAll();
		
	}
	
	@Override
	public StockModel findByIdStock(long idStock) {
		
		return stockConverter.entityToModel(stockRepository.findByIdStock(idStock));
		
	}
	
	@Override
	public StockModel insertOrUpdate(StockModel stockModel) {
		stockModel.setLocal(localService.findByIdLocal(stockModel.getLocal().getIdLocal()));
		Stock stock = stockRepository.save(stockConverter.modelToEntity(stockModel));
		return stockConverter.entityToModel(stock);
		
	}
	
	@Override
	public boolean remove(long idStock) {
		
		try {
			
			stockRepository.deleteById(idStock);;
			return true;
		}catch(Exception e) {
			
			return false;
			
		}
		
	}
}