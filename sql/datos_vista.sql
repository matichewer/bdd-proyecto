# Carga de datos de la BD vuelos


USE vuelos;


DELETE FROM  reserva_vuelo_clase;
DELETE FROM  reservas;
DELETE FROM  posee;
DELETE FROM  asientos_reservados;
DELETE FROM  brinda;
DELETE FROM  pasajeros;
DELETE FROM  empleados;
DELETE FROM  comodidades;
DELETE FROM  clases;
DELETE FROM  instancias_vuelo; 
DELETE FROM  salidas;
DELETE FROM  vuelos_programados;
DELETE FROM  modelos_avion;
DELETE FROM  aeropuertos;
DELETE FROM  ubicaciones;

DELIMITER !
 
 CREATE FUNCTION dia(fecha DATE) RETURNS CHAR(2)
 DETERMINISTIC
 BEGIN
   DECLARE i INT;   
   SELECT DAYOFWEEK(fecha) INTO i;
   CASE i
		WHEN 1 THEN RETURN 'Do';
		WHEN 2 THEN RETURN 'Lu';
		WHEN 3 THEN RETURN 'Ma';
		WHEN 4 THEN RETURN 'Mi';
		WHEN 5 THEN RETURN 'Ju';
		WHEN 6 THEN RETURN 'Vi';
		WHEN 7 THEN RETURN 'Sa';
	END CASE; 	
 END; !
 
 DELIMITER ;

#---------------------------------------------------------------------------------------------------------------------------
# UBICACIONES
        
        INSERT INTO ubicaciones(pais,estado,ciudad,huso)
        VALUES ('Argentina', 'Buenos Aires', 'Buenos Aires', 0);
        INSERT INTO ubicaciones(pais,estado,ciudad,huso) 
        VALUES ('Argentina', 'Cordoba', 'Cordoba', 0);
        INSERT INTO ubicaciones(pais,estado,ciudad,huso)
        VALUES ('Argentina', 'Buenos Aires', 'Ezeiza', 0);
        INSERT INTO ubicaciones(pais,estado,ciudad,huso) 
        VALUES ('Argentina', 'Buenos Aires', 'Mar del Plata', 0);
        INSERT INTO ubicaciones(pais,estado,ciudad,huso)
        VALUES ('Argentina', 'Mendoza', 'Mendoza', 0);
        
#---------------------------------------------------------------------------------------------------------------------------
# AEROPUERTOS



INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
 VALUES ('AEP', 'Aeroparque Jorge Newbery', '(54)45765300', 'direaep', 'Argentina', 'Buenos Aires', 'Buenos Aires');
INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
 VALUES ('COR', 'Ing.Aer.A.L.V. Taravella', '(54)03514750874', 'direcor', 'Argentina', 'Cordoba', 'Cordoba');
INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
 VALUES ('EZE', 'Ministro Pistarini', '(54)44802514', 'direeze', 'Argentina', 'Buenos Aires', 'Ezeiza');
INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
 VALUES ('MDQ', 'Brig. Gral. Bartolome de Colina', '(54)02234785811', 'diremdq', 'Argentina', 'Buenos Aires', 'Mar del Plata');
INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad) 
VALUES ('MDZ', 'Gdor. Francisco Gabrielli', '(54)02614480017', 'diremdz', 'Argentina', 'Mendoza', 'Mendoza');



#---------------------------------------------------------------------------------------------------------------------------
# VUELOS_PROGRAMADOS
        # un vuelo de Buenos aires a Cordoba
        INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
        VALUES ('BC1', 'AEP', 'COR');
        
        # un vuelo de Cordoba a Buenos Aires
        INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
        VALUES ('CB2', 'COR', 'AEP');
            
        # un vuelo de Buenos aires a Mar del Plata
        INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
        VALUES ('BM', 'AEP', 'MDQ');


        # un vuelo de  Mar del Plata a Buenos aires
        INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
        VALUES ('MB', 'MDQ', 'AEP');
        
                
#---------------------------------------------------------------------------------------------------------------------------
# MODELOS_AVION


        INSERT INTO modelos_avion(modelo, fabricante, cabinas, cant_asientos)
        VALUES ('B-737', 'Boeing', 2, 60);
        INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
        VALUES ('B-747', 'Boeing', 2, 90);
        INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
        VALUES ('B-757', 'Boeing', 2, 120);
        INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
        VALUES ('B-767', 'Boeing', 2, 150);

#---------------------------------------------------------------------------------------------------------------------------
# CLASES


        INSERT INTO clases(nombre, porcentaje) VALUES ('Turista', 0.67);
        INSERT INTO clases(nombre, porcentaje) VALUES ('Ejecutiva', 0.5);
        INSERT INTO clases(nombre, porcentaje) VALUES ('Primera', 0.34);

		
#---------------------------------------------------------------------------------------------------------------------------
# SALIDAS, BRINDA e INSTACIAS_VUELO    

#..............................................................................
#2 salidas para BC1	con diferentes clases brindadas y precios. 
#Una instancia vuelo para cada salida. La instancia del 202X-01-02 tiene reservas 
# y la instancia 202X-01-04 no tiene reservas

    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02')), '08:00:00', '09:00:00', 'B-737');
 
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02')), 'Turista',  10000.00, 3);    
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02')), 'Primera',  20000.00, 3);    
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02')), 'Ejecutiva', 30000.00, 2);   
    	

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
    VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02')), 'a tiempo');
        
    
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 	
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04')), '12:00:00', '13:00:00', 'B-737');
 
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04')), 'Turista',  15000.00, 10);    
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BC1', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04')), 'Ejecutiva',  35000.00, 4);            
    
	
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
    VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04')), 'a tiempo');
    	
	
#..............................................................................
#1 salida para CB2 con 2 instancias de vuelo.
# la instancia del 202X-01-05 tiene reservas y 202X-01-12 no tiene

    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
    VALUES ('CB2', 	dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05')), '17:00:00', '18:10:00', 'B-737');


    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
    VALUES ('CB2', 	dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05')), 'Turista',  10000.00, 3);    
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
    VALUES ('CB2', 	dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05')), 'Primera',  20000.00, 3);    

	
    INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
    VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05')), 'demorado');

    INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
    VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-12'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05')), 'demorado');


#..............................................................................


    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
    VALUES ('BM', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03')), '8:00:00', '9:00:00', 'B-737');

    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BM', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03')), 'Turista',  10000.00, 6);     
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BM', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03')), 'Primera',  20000.00, 7);     
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('BM', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03')), 'Ejecutiva', 30000.00, 6);    

    INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
    VALUES ('BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03')), 'a tiempo');

		
#..............................................................................


    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
    VALUES ('MB', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06')), '17:00:00', '18:10:00', 'B-737');

		
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('MB', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06')), 'Turista',  10000.00, 5);     
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('MB', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06')), 'Primera',  20000.00, 6);     
    INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
    VALUES ('MB', dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06')), 'Ejecutiva', 30000.00, 4);    


     INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
     VALUES ('MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), dia(CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06')), 'a tiempo');



#---------------------------------------------------------------------------------------------------------------------------
# COMODIDADES


        INSERT INTO comodidades(codigo,descripcion) VALUES (1, 'Asientos grandes');
        INSERT INTO comodidades(codigo,descripcion) VALUES (2, 'Musica');
        INSERT INTO comodidades(codigo,descripcion) VALUES (3, 'Peliculas');
        INSERT INTO comodidades(codigo,descripcion) VALUES (4, 'Internet');


#---------------------------------------------------------------------------------------------------------------------------
# PASAJEROS


        INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad) 
        VALUES ('DNI', 1, 'Julieta', 'Marcos', 'Roca 850', '02932 424565', 'Argentina');
        INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
        VALUES ('DNI', 2, 'Luciano', 'Tamargo', 'Alem 222', '0291 45222', 'Argentino');
        INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
        VALUES ('DNI', 3, 'Diego', 'Garcia', '12 de Octubre 333', '0291 45333', 'Argentino');
        INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
        VALUES ('DNI', 4, 'Juan', 'Perez', 'Belgrano 14', '0291 4556733', 'Argentino');
        INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
        VALUES ('DNI', 5, 'Julian', 'Gomez', 'Donado 533', '0291 4543563', 'Argentino');
        

#---------------------------------------------------------------------------------------------------------------------------
# EMPLEADOS
    
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (101,md5('emp101'),'DNI', 101, 'Nombre_Emp101', 'Apellido_Emp101', 'Dir_Emp101' , '0291-4540101');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (102,md5('emp102'),'DNI', 102, 'Nombre_Emp102', 'Apellido_Emp102', 'Dir_Emp102' , '0291-4540102');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (103,md5('emp103'),'DNI', 103, 'Nombre_Emp103', 'Apellido_Emp103', 'Dir_Emp103' , '0291-4540103');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (104,md5('emp104'),'DNI', 104, 'Nombre_Emp104', 'Apellido_Emp104', 'Dir_Emp104' , '0291-4540104');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (105,md5('emp105'),'DNI', 105, 'Nombre_Emp105', 'Apellido_Emp105', 'Dir_Emp105' , '0291-4540105');


        
#---------------------------------------------------------------------------------------------------------------------------
# POSEE


        INSERT INTO posee(clase, comodidad) VALUES ('Turista', 3);
        
        INSERT INTO posee(clase, comodidad) VALUES ('Ejecutiva', 3);
        INSERT INTO posee(clase, comodidad) VALUES ('Ejecutiva', 4);    
        
        INSERT INTO posee(clase, comodidad) VALUES ('Primera', 1);
        INSERT INTO posee(clase, comodidad) VALUES ('Primera', 2);
        INSERT INTO posee(clase, comodidad) VALUES ('Primera', 3);
        INSERT INTO posee(clase, comodidad) VALUES ('Primera', 4);
        
#---------------------------------------------------------------------------------------------------------------------------
# RESERVAS
        # reservas de BC1
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (1, 'DNI', 1, 101, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (2, 'DNI', 2, 102, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada'); 
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (3, 'DNI', 3, 103, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada'); 
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (4, 'DNI', 4, 104, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'En Espera');  


        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (5, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');


        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (11, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        
        # reservas de CB2
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (6, 'DNI', 1, 101, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (7, 'DNI', 2, 102, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada'); 
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (8, 'DNI', 3, 103, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada'); 
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (9, 'DNI', 4, 104, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'En Espera');  


        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (10, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (12, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');


        

        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (13, 'DNI', 1, 101, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (14, 'DNI', 2, 101, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (15, 'DNI', 3, 103, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (16, 'DNI', 4, 104, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (17, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');        
        
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (18, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (19, 'DNI', 5, 105, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');


      

        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (20, 'DNI', 1, 101, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');


        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (21, 'DNI', 2, 102, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');
        
 
        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (22, 'DNI', 2, 102, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'Confirmada');

        INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (23, 'DNI', 2, 102, CONCAT(YEAR(NOW()),'-10-01'), CONCAT(YEAR(NOW()),'-12-01'), 'En Espera');

        
#---------------------------------------------------------------------------------------------------------------------------
# RESERVA_VUELO_CLASE


        # reservas de BC1 del 202X-01-02  (la instancia del 202X-01-04 no tiene reservas)     
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (1, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (2, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (3, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (4, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Turista');


        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (5, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Primera');


        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (11, 'BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Ejecutiva');



        # reservas de CB2 del 202X-01-05 (la instancia del 202X-01-12 no tiene reservas)
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (6, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (7, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (8, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (9, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Turista');


        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (10, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Primera');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (12, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Primera');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (22, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Primera');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (23, 'CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Primera');


        

        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (13, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (14, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Turista');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (15, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Turista');
        


        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (16, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Primera');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (17, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Primera');


        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (18, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Ejecutiva');
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (19, 'BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Ejecutiva');


        #Reservas de MB  -202X-01-06

        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (20, 'MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), 'Turista');
        
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (21, 'MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), 'Primera');
        
#---------------------------------------------------------------------------------------------------------------------------
# ASIENTOS RESERVADOS
# Elimina, si las hubiera, las tuplas insertadas por el trigger con cantidad= 0 
DELETE FROM  asientos_reservados;

	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Turista', 4); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Primera', 1); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-02'), 'Ejecutiva', 1);
	
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04'), 'Turista', 0); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BC1', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-04'), 'Ejecutiva', 0);
 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad)
	VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Turista', 4); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-05'), 'Primera', 4); 
	
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad)
	VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-12'), 'Turista', 0); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('CB2', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-12'), 'Primera', 0); 

	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Turista', 3); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Primera', 2); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('BM', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-03'), 'Ejecutiva', 2); 

	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad) 
	VALUES ('MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), 'Turista', 1); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad)
	VALUES ('MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), 'Primera', 1); 
	INSERT INTO asientos_reservados( vuelo, fecha, clase, cantidad)
	VALUES ('MB', CONCAT(YEAR(DATE_ADD(NOW(), INTERVAL 1 YEAR)),'-01-06'), 'Ejecutiva', 0); 
        
#---------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION dia;