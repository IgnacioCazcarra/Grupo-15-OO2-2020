package com.unla.Grupo15OO22020.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.unla.Grupo15OO22020.entities.Local;
import com.unla.Grupo15OO22020.entities.Lote;

public class StockModel {

	private long idStock;
	private List<LoteModel> lotes = new ArrayList<LoteModel>();
	private LocalModel local;
	private String codigo;
	
	public StockModel() {
		
	}
	
	public StockModel(long idStock, LocalModel local,String codigo) {
		super();
		this.idStock = idStock;
		this.local = local;
		this.codigo = codigo;

	}

	public StockModel(long idStock, LocalModel local, List<LoteModel> lotes) {
		super();
		this.idStock = idStock;
		this.local = local;
		this.lotes = lotes;


	}
	
	
	public long getIdStock() {
		return idStock;
	}



	public void setIdStock(long idStock) {
		this.idStock = idStock;
	}



	


	public LocalModel getLocal() {
		return local;
	}

	public void setLocal(LocalModel local) {
		this.local = local;
	}

	public void setLotes(List<LoteModel> lotes) {
		this.lotes = lotes;
	}

	public List<LoteModel> getLotes() {
		return lotes;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<LoteModel> lotesDelProducto(ProductoModel producto){
		List<LoteModel> lotesActivos = new ArrayList<LoteModel>();
		System.out.println("cantidad LOTE: "  + lotes.size());
		for(LoteModel l : lotes) {
		//	if(l.getProducto().equals(producto) && l.isEstado()) {
			if(l.getProducto().getIdProducto() == producto.getIdProducto() && l.isEstado()) {				
				System.out.println("ENTRO BUCLE INTERNO: " );
				lotesActivos.add(l);
				
			}
		}
		return lotesActivos;
	}
	
	public int calcularStock(ProductoModel producto) {
		int total = 0;
		for(LoteModel l : lotesDelProducto(producto)) {
			total += l.getCantidadActual();
		}
		System.out.println("TOTAL DESDE CALCULAR STOCK: " + total);
		return total;
	}

	

	public boolean stockValido(ProductoModel producto, int cantidad) {
		return (calcularStock(producto)>=cantidad)? true:false; //SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}															//SOLICITADA RETORNA TRUE
	
	
	
	public boolean consumoStock(ProductoModel producto, int cantidad) throws Exception{
		if (!stockValido(producto, cantidad)) throw new Exception("No alcanza el stock");
		
		int aux = cantidad;
		int x = 0;
	
			while (x < lotesDelProducto(producto).size() && aux > 0) {
				LoteModel l = lotesDelProducto(producto).get(x);
				
				if (l.getCantidadActual() > aux) {
					l.setCantidadActual(l.getCantidadActual() - cantidad);
					aux = 0;
				}
				else if (l.getCantidadActual() < aux) {
					aux -= l.getCantidadActual();
					l.setCantidadActual(0);
					l.setEstado(false);
				}
				else if (l.getCantidadActual() == aux) {
					aux = 0;
					l.setCantidadActual(0);
					l.setEstado(false);
				}
				x++;
				
			}
			 
			return true;   // hay suficiente stock 
			
	}
	
	
	
	
	

}