package com.unla.Grupo15OO22020.helpers;

public class ViewRouteHelpers {
	//views
	public final static String INDEX = "home/index";
	public final static String INFO = "/informacion/information";
	
	public final static String CLIENT_UPDATE = "cliente/update";
	public final static String CLIENT_ADD = "cliente/new";
	public final static String CLIENT_INDEX = "cliente/index";
	
	//Local
	public final static String LOCAL_UPDATE = "local/update";
	public final static String LOCAL_ADD = "local/new";
	public final static String LOCAL_INDEX = "local/index";
	public final static String LOCAL_DISTANCE = "local/distanciaentrelocales";
	
	//Ranking
	public final static String RANKING_INDEX = "ranking/index";
	public final static String RANKING_PRODUCTO = "ranking/ranking";

	
	//Lote
	public final static String LOTE_UPDATE = "lote/update";
	public final static String LOTE_ADD = "lote/new";
	public final static String LOTE_INDEX = "lote/index";

	//Stock
	public final static String STOCK_UPDATE = "stock/update";
	public final static String STOCK_ADD = "stock/new";
	public final static String STOCK_INDEX = "stock/index";
	public final static String STOCK_CALCULOSTOCK = "stock/calcularStockPorProducto";
	
	
	//redirects	
	public final static String ROUTE_INDEX = "/index";
	public final static String CLIENT_ROOT = "/clientes";
	public final static String EMPLEADO_ROOT="/empleados";
	public final static String PRODUCTO_ROOT="/productos";
	public final static String LOCAL_ROOT = "/locales";
	public final static String PEDIDO_ROOT="/pedidos";
	public final static String LOTE_ROOT = "/lotes";
	public final static String STOCK_ROOT = "/stocks";

	
	//Empleado
	public final static String EMPLEADO_INDEX = "empleado/index";
	public final static String EMPLEADO_ADD = "empleado/new";
	public final static String EMPLEADO_UPDATE = "empleado/update";
	
	//Producto
	public final static String PRODUCTO_INDEX = "producto/index";
	public final static String PRODUCTO_ADD = "producto/new";
	public final static String PRODUCTO_UPDATE = "producto/update";
	
	//Pedido
	public final static String PEDIDO_INDEX = "pedido/index";
	public final static String PEDIDO_ADD = "pedido/new";
	public final static String PEDIDO_UPDATE = "pedido/update";
	public final static String PEDIDO_LOCALPARAPETICION = "pedido/localparapeticion";
	public final static String PEDIDO_DATE = "pedido/productosentrefechas";

}
