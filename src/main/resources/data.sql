USE grupo15oo22020;

INSERT INTO `local` (id_local,direccion,latitud,longitud,telefono) VALUES(1,"Alto Avellaneda Shopping, Gral. Güemes 897",3.15,1,"1111111111");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Abasto Shopping, Av. Corrientes 3247",-3.15,1,"2222222222");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Nike Factory Store, Av. Pres. Hipólito Yrigoyen 9272",6.35,2.423,"3333333333");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Paseo Alcorta Shopping, Jerónimo Salguero 3172",3.145,2.1,"4444444444");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Nike Soho, Gurruchaga 1615",3.23,1.9,"5555555555");

INSERT INTO persona (id_persona,nombre,apellido,fecha_nacimiento,dni) VALUES(1,"Santiago","Gonzalez",'1990-09-01',11111111);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Mateo","Rodriguez",'1990-09-11',12222222);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Juan","Gomez",'1990-09-21',13333333);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Matias","Fernandez",'1990-09-21',14444444);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Nicolas","Lopez",'1990-09-22',11111111);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Benjamin","Diaz",'1990-09-01',22222222);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Pedro","Martinez",'1990-09-01',33333333);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Tomas","Perez",'1990-09-19',44444444);

INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(1,"09:30-17:30",true,1);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(2,"10:00-16:00",false,2);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(3,"09:30-17:30",false,3);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(4,"10:00-16:00",false,4);
INSERT INTO cliente (id_persona,email) VALUES(5,"nlopez@gmail.com");
INSERT INTO cliente (id_persona,email) VALUES(6,"bdiaz@gmail.com");
INSERT INTO cliente (id_persona,email) VALUES(7,"pmartinez@gmail.com");
INSERT INTO cliente (id_persona,email) VALUES(8,"tperez@gmail.com");

INSERT INTO stock (id_stock,local_id_local,codigo) VALUES (1,1,"AA001");
INSERT INTO stock (id_stock,local_id_local,codigo) VALUES (2,2,"AA002");
INSERT INTO stock (id_stock,local_id_local,codigo) VALUES (3,3,"AA003");
INSERT INTO stock (id_stock,local_id_local,codigo) VALUES (4,4,"AA004");

INSERT INTO producto (id_producto,descripcion,fecha_alta,nombre,precio) VALUES(1,"Las zapatillas Nike Nightgazer para hombre se caracterizan por su look juvenil y su estilo casual. Un calzado de los años 80 traído a nuestra época totalmente renovado.","2019-05-16","Nike Nightgazer",7600);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Las zapatillas deportivas de hombre Nike Odyssey React Flyknit 2 ofrece una combinación estratégica de Flyknit liviano y material sintético para brindar soporte justo cuando lo necesita.","2018-05-12","Nike Odyssey React 2",5500);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Las nuevas Nike Air Zoom Pegasus 36 se han diseñado para corredores de todos los niveles, ya seas un experimentado veterano o estés dando tus primeros pasos.","2020-03-22","Nike Air Zoom Pegasus 36",9900);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Capellada confeccionada en cuero para mayor confort y durabilidad. Unidad Air- Sole, encapsulada, que brinda una amortiguación excepcional. Suela en goma para una tracción superior y durabilidad.","2020-04-11","Zapatillas Air Force",9000);


INSERT INTO lote (id_lote,cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (1,20,20,1,"2020-05-17",1,1);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (5,5,1,"2020-05-17",4,1);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (45,45,1,"2020-05-17",2,2);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (11,11,1,"2019-12-01",3,4);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (30,30,1,"2019-05-17",2,3);


INSERT INTO pedido (id_pedido,aceptado,cantidad,fecha,subtotal,cliente_id_persona,local_id_local,producto_id_producto,id_vendedor) VALUES (1,true,3,'2020-05-22',22800,5,2,1,2);
INSERT INTO pedido (aceptado,cantidad,fecha,subtotal,cliente_id_persona,local_id_local,producto_id_producto,id_vendedor) VALUES (true,2,'2020-01-26',11000,6,3,2,1);
INSERT INTO pedido (aceptado,cantidad,fecha,subtotal,cliente_id_persona,local_id_local,producto_id_producto,id_vendedor) VALUES (true,5,'2020-03-05',45000,8,1,4,3);
INSERT INTO pedido (aceptado,cantidad,fecha,subtotal,cliente_id_persona,local_id_local,producto_id_producto,id_vendedor) VALUES (true,4,'2020-05-10',39600,7,1,3,4);