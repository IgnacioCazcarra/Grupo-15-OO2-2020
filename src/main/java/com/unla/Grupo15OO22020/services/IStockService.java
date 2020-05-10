package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.Stock;
import com.unla.Grupo15OO22020.models.StockModel;

public interface IStockService {

	public List<Stock> getAll();
	
	public StockModel insertOrUpdate(StockModel stockModel);
	
	public StockModel findByIdStock(long idStock);
	
	public boolean remove(long idStock);
	
}
