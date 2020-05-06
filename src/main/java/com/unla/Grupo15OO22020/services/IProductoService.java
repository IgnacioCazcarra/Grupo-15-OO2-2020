package com.unla.Grupo15OO22020.services;

import java.util.List;

import com.unla.Grupo15OO22020.entities.Producto;
import com.unla.Grupo15OO22020.models.ProductoModel;

public interface IProductoService {

	public abstract List<Producto> getAll();

	public ProductoModel insertOrUpdate(ProductoModel productoModel);

	public ProductoModel findByIdProducto(long id);

	public boolean remove(long id);
}
