package com.unla.Grupo15OO22020.models;


import java.util.ArrayList;
import java.util.List;

public class LocalModel {

	private long idLocal;
	private String direccion;
	private double latitud;
	private double longitud;
	private String telefono;
	private StockModel stock;
	private List<ClienteModel> listaClientes = new ArrayList<ClienteModel>();
	private List<EmpleadoModel> listaEmpleados = new ArrayList<EmpleadoModel>();
	
	public LocalModel() {
	}
	
	public LocalModel(long idLocal, String direccion, double latitud, double longitud, String telefono) {
		super();
		this.idLocal = idLocal;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.telefono = telefono;
	}

	public long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(long idLocal) {
		this.idLocal = idLocal;
	}

	public StockModel getStock() {
		return stock;
	}

	public void setStock(StockModel stock) {
		this.stock = stock;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<ClienteModel> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClienteModel> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<EmpleadoModel> getListaEmpleados() {
		return listaEmpleados;
	}


	public void setListaEmpleados(List<EmpleadoModel> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalModel other = (LocalModel) obj;
		if (idLocal != other.idLocal)
			return false;
		return true;
	}

	public double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371; //en kilómetros
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
		+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		return radioTierra * va2;
		}



}