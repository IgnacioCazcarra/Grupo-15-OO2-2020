package com.unla.Grupo15OO22020.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.unla.Grupo15OO22020.entities.Local;

public class StockModel {

	private long idStock;
	private Set<LoteModel> lotes = new HashSet<LoteModel>();
	private int cantidad;
	//private long idLocal;
	private LocalModel local;
	
	//REVISAR SI IDLOCAL VA, Y SI VA EN EL CONSTRUCTOR.
	
	public StockModel() {
		
	}
	
	public StockModel(long idStock, LocalModel local) {
		super();
		this.idStock = idStock;
		this.cantidad = 0;
		this.local = local;

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

	public void setLotes(Set<LoteModel> lotes) {
		this.lotes = lotes;
	}

	public Set<LoteModel> getLotes() {
		return lotes;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<LoteModel> lotesDelProducto(ProductoModel producto){
		List<LoteModel> lotesActivos = new ArrayList<LoteModel>();
		for(LoteModel l : lotes) {
			if(l.getProducto().equals(producto) && l.isEstado()) {
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
		return total;
	}
	

	public boolean stockValido(ProductoModel producto, int cantidad) {
		return (calcularStock(producto)>=cantidad)? true:false; //SI EL STOCK DISPONIBLE ES MAYOR O IGUAL A LA CANTIDAD
	}															//SOLICITADA RETORNA TRUE
	
	/*public Lote traerLote(int idLote) {
        int i = 0;
        Lote loteEncontrado = null;

        while(i<lotes.size() && loteEncontrado == null) {
            Lote l = lotes.get(i);
            if(l.getIdLote()==idLote) {
                loteEncontrado = l;
            }
            i++;
        }

        return loteEncontrado;
    }
	*/ //Error: de nuevo los get de las listas al cambiar a set
    /**/

    /*public void altaStock(int idLote) throws Exception {

        if(traerLote(idLote)==null) throw new Exception("Lote inexistente");

        setCantidad(cantidad+traerLote(idLote).getCantidadInicial());


    }/* ERROR: el traerLote esta comentado
    /**/

    /*public boolean agregarLote(int cantidadInicial, LocalDate fechaIngreso, Producto producto) {
        int id =1;
        if(!lotes.isEmpty()) {
            id = lotes.get(lotes.size()-1).getIdLote()+1;
        }

        return lotes.add(new Lote(id,cantidadInicial,cantidadInicial,fechaIngreso,producto));
    }*///ERROR: de nuevo error con el Set y en el add del set lotes
	
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