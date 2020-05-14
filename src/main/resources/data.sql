USE grupo15oo22020;

INSERT INTO `local` (id_local,direccion,latitud,longitud,telefono) VALUES(1,"Direccion1",3.15,1,"1111111111");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Direccion2",-3.15,1,"2222222222");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Direccion3",6.35,2.423,"3333333333");
INSERT INTO `local` (direccion,latitud,longitud,telefono) VALUES("Direccion4",3.145,2.1,"4444444444");

INSERT INTO persona (id_persona,nombre,apellido,fecha_nacimiento,dni) VALUES(1,"Empleado1","A",'1990-09-01',11111111);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Empleado2","A",'1990-09-11',12222222);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Empleado3","A",'1990-09-21',13333333);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Empleado4","A",'1990-09-21',14444444);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Cliente1","A",'1990-09-22',11111111);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Cliente2","A",'1990-09-01',22222222);
INSERT INTO persona (nombre,apellido,fecha_nacimiento,dni) VALUES("Cliente3","A",'1990-09-01',33333333);

INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(1,"09:30-17:30",true,1);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(2,"10:00-16:00",false,2);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(3,"09:30-17:30",false,3);
INSERT INTO empleado (id_persona,franja_horaria,gerente,id_local) VALUES(4,"10:00-16:00",false,4);
INSERT INTO cliente (id_persona,email) VALUES(5,"cliente1@gmail.com");
INSERT INTO cliente (id_persona,email) VALUES(6,"cliente2@gmail.com");
INSERT INTO cliente (id_persona,email) VALUES(7,"cliente3@gmail.com");

INSERT INTO stock (id_stock,cantidad,local_id_local) VALUES (1,25,1);
INSERT INTO stock (id_stock,cantidad,local_id_local) VALUES (2,45,2);
INSERT INTO stock (id_stock,cantidad,local_id_local) VALUES (3,200,3);
INSERT INTO stock (id_stock,cantidad,local_id_local) VALUES (4,11,4);

INSERT INTO producto (id_producto,descripcion,fecha_alta,nombre,precio) VALUES(1,"Este es el producto 1","2019-05-16","Producto1",500);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Este es el producto 2","2018-05-12","Producto2",351.32);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Este es el producto 3","2020-03-22","Producto3",144.99);
INSERT INTO producto (descripcion,fecha_alta,nombre,precio) VALUES("Este es el producto 4","2020-04-11","Producto4",1040);


INSERT INTO lote (id_lote,cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (1,20,20,1,"2020-05-17",1,1);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (5,5,1,"2020-05-17",4,1);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (45,45,1,"2020-05-17",2,2);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (11,11,1,"2019-12-01",3,4);
INSERT INTO lote (cantidad_inicial,cantidad_actual,estado,fecha_ingreso,producto_id_producto,stock_id_stock) VALUES (200,200,1,"2019-05-17",2,3);






