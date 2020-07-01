package com.unla.Grupo15OO22020.models;

import java.sql.Date;

public class EmpleadoModel extends PersonaModel {
	private String franjaHoraria;
	private boolean gerente; 
	private LocalModel local;
	private long idEmpleado;

	public EmpleadoModel() {
	}

	public EmpleadoModel(long idPersona, String nombre, String apellido, Date fechaNacimiento, long dni, String franjaHoraria, boolean gerente, LocalModel local) {
		super(idPersona, nombre, apellido, fechaNacimiento, dni);
		this.franjaHoraria = franjaHoraria;
		this.gerente = gerente;
		this.local = local;
	}

	public long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getFranjaHoraria() {
		return franjaHoraria;
	}

	public void setFranjaHoraria(String franjaHoraria) {
		this.franjaHoraria = franjaHoraria;
	}

	public LocalModel getLocal() {
		return local;
	}

	public void setLocal(LocalModel local) {
		this.local = local;
	}

	public boolean isGerente() {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}
/*
	public float calcularSueldo(List<CarritoModel> listaCarrito, EmpleadoModel empleado, int mes) {
		float sueldo = 0;
		double porcentajeSueldo = 0;
		for (CarritoModel carrito : listaCarrito) {

			for (PedidoModel pedido : carrito.getListaPedidos()) {

				porcentajeSueldo = 0;

				if (carrito.getFecha().getMonthValue() == mes) {

					if (pedido.getVendedorOriginal().getDni() == empleado.getDni()
							&& pedido.getVendedorAuxiliar() == null) {
						porcentajeSueldo = pedido.getTotal() * 0.05;
						sueldo += porcentajeSueldo;
					} else if (pedido.getVendedorAuxiliar() != null) {

						if (pedido.getVendedorOriginal().getDni() == empleado.getDni()) {
							porcentajeSueldo = pedido.getTotal() * 0.03;
							sueldo += porcentajeSueldo;
						}

						if (pedido.getVendedorAuxiliar().getDni() == empleado.getDni()) {
							porcentajeSueldo = pedido.getTotal() * 0.02;
							sueldo += porcentajeSueldo;
						}
					}

				}
			}

		}
		return sueldo;
	}
*/
}
