##############################################################################
#						CREACION DE LA BASE DE DATOS
##############################################################################

CREATE DATABASE vuelos;

# Selecciono la base de datos sobre la cual voy a hacer modificaciones
USE vuelos;



##############################################################################
#								CREACION DE TABLAS
##############################################################################

CREATE TABLE ubicaciones(
	pais VARCHAR(20) NOT NULL,
	estado VARCHAR(20) NOT NULL,
	ciudad VARCHAR(20) NOT NULL,
	huso INT NOT NULL,

	CONSTRAINT pk_ubicaciones
	PRIMARY KEY (pais,estado,ciudad),

	CONSTRAINT chk_husos CHECK (huso>=-12 AND huso<=12)

) ENGINE=InnoDB;


CREATE TABLE aeropuertos(	
	pais VARCHAR(20) NOT NULL,
	estado VARCHAR(20) NOT NULL,
	ciudad VARCHAR(20) NOT NULL,
	codigo VARCHAR(45) NOT NULL,
	nombre VARCHAR(40) NOT NULL,
	telefono VARCHAR(15) NOT NULL,
	direccion VARCHAR(30) NOT NULL,

	CONSTRAINT pk_instancias_vuelo
	PRIMARY KEY (codigo),
	
	CONSTRAINT FK_aeropuertos
	FOREIGN KEY (pais,estado,ciudad) REFERENCES ubicaciones (pais,estado,ciudad)

) ENGINE=InnoDB;


CREATE TABLE vuelos_programados (
	numero VARCHAR(10) NOT NULL,
	aeropuerto_salida VARCHAR(45) NOT NULL,
	aeropuerto_llegada VARCHAR(45) NOT NULL,

	CONSTRAINT pk_vuelos_programados
  	PRIMARY KEY (numero),

  	CONSTRAINT FK_vuelos_programados
	FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos (codigo),

  	CONSTRAINT FK_vuelos_programados_aeropuertos
	FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos (codigo)

) ENGINE=InnoDB;


CREATE TABLE modelos_avion(
	modelo VARCHAR(20) NOT NULL,
	fabricante VARCHAR(20) NOT NULL, 
	cabinas INT UNSIGNED NOT NULL,
	cant_asientos INT UNSIGNED NOT NULL,

	CONSTRAINT pk_modelos_avion
	PRIMARY KEY (modelo)

) ENGINE=InnoDB;


CREATE TABLE salidas (
	vuelo VARCHAR(10) NOT NULL,
	dia ENUM ('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	hora_sale TIME NOT NULL,
	hora_llega TIME NOT NULL,
	modelo_avion VARCHAR(20) NOT NULL,

	CONSTRAINT pk_salidas
	PRIMARY KEY (vuelo, dia),

	CONSTRAINT FK_salidas
	FOREIGN KEY (modelo_avion) REFERENCES modelos_avion (modelo),

	CONSTRAINT FK_salidas_vuelos_programados
	FOREIGN KEY (vuelo) REFERENCES vuelos_programados (numero)

) ENGINE=InnoDB;


CREATE TABLE instancias_vuelo(
	estado VARCHAR(15),
	fecha DATE NOT NULL,
	vuelo VARCHAR(10) NOT NULL,
	dia ENUM ('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,

	CONSTRAINT pk_instancias_vuelo
	PRIMARY KEY (vuelo, fecha),

	CONSTRAINT FK_instancias_salidas
	FOREIGN KEY (vuelo,dia) REFERENCES salidas (vuelo,dia)

) ENGINE=InnoDB;


CREATE TABLE clases(
	nombre VARCHAR(20) NOT NULL,
 	porcentaje DECIMAL (2,2) UNSIGNED NOT NULL,

	CONSTRAINT pk_clases
	PRIMARY KEY (nombre),

	CONSTRAINT chk_porcentaje CHECK (porcentaje>=0 AND porcentaje<=0.99)

) ENGINE=InnoDB;


CREATE TABLE comodidades(
	codigo INT UNSIGNED NOT NULL,
	descripcion TEXT NOT NULL,

	CONSTRAINT pk_comodidades
	PRIMARY KEY (codigo)

) ENGINE=InnoDB;


CREATE TABLE pasajeros(
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	direccion VARCHAR(40) NOT NULL,
	telefono VARCHAR(15) NOT NULL,
	nacionalidad VARCHAR(20) NOT NULL,

	CONSTRAINT pk_pasajeros
	PRIMARY KEY (doc_tipo,doc_nro)

) ENGINE=InnoDB;


CREATE TABLE empleados(
	legajo INT UNSIGNED NOT NULL,
	password VARCHAR(32) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	nombre VARCHAR(20) NOT NULL, 
	direccion VARCHAR(40) NOT NULL,
	telefono VARCHAR(15) NOT NULL,

	CONSTRAINT pk_empleados
	PRIMARY KEY (legajo)

) ENGINE=InnoDB;


CREATE TABLE reservas(
	numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	vencimiento DATE NOT NULL,
	estado VARCHAR(15) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	legajo INT UNSIGNED NOT NULL,

	CONSTRAINT pk_reservas
	PRIMARY KEY (numero),

	CONSTRAINT FK_reservas
	FOREIGN KEY (doc_tipo, doc_nro) REFERENCES pasajeros (doc_tipo, doc_nro),

	CONSTRAINT FK_reservas_empleados
	FOREIGN KEY (legajo) REFERENCES empleados (legajo)

) ENGINE=InnoDB;


CREATE TABLE brinda(
	vuelo VARCHAR(10) NOT NULL,
	dia ENUM ('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	clase VARCHAR(20) NOT NULL,
	precio DECIMAL(7,2) UNSIGNED NOT NULL,
	cant_asientos INT UNSIGNED NOT NULL,

	CONSTRAINT pk_brinda
	PRIMARY KEY (vuelo,dia,clase),

	CONSTRAINT FK_brinda
	FOREIGN KEY (vuelo,dia) REFERENCES salidas (vuelo,dia),

	CONSTRAINT FK_brinda_nombre
	FOREIGN KEY (clase) REFERENCES clases (nombre)

) ENGINE=InnoDB;


CREATE TABLE posee(
	clase VARCHAR(20) NOT NULL,
	comodidad INT UNSIGNED NOT NULL,

	CONSTRAINT pk_brinda
	PRIMARY KEY (clase,comodidad),

	CONSTRAINT FK_posee
	FOREIGN KEY (clase) REFERENCES clases (nombre),

	CONSTRAINT FK_posee_comodidades
	FOREIGN KEY (comodidad) REFERENCES comodidades (codigo)

) ENGINE=InnoDB;


CREATE TABLE reserva_vuelo_clase(
	numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
	vuelo VARCHAR(10) NOT NULL,
	fecha_vuelo DATE NOT NULL,
	clase VARCHAR(20) NOT NULL,

	CONSTRAINT pk_reserva_vuelo_clase
	PRIMARY KEY (numero,vuelo,fecha_vuelo),

	CONSTRAINT FK_reserva_vuelo_clase
	FOREIGN KEY (numero) REFERENCES reservas (numero),

	CONSTRAINT FK_reserva_vuelo_clase_instancias_vuelo
	FOREIGN KEY (vuelo, fecha_vuelo) REFERENCES instancias_vuelo (vuelo, fecha),

	CONSTRAINT FK_reserva_vuelo_clase_clases
	FOREIGN KEY (clase) REFERENCES clases (nombre)

) ENGINE=InnoDB;


CREATE TABLE asientos_reservados(
	vuelo VARCHAR(10) NOT NULL,
	fecha DATE NOT NULL,
	clase VARCHAR(20) NOT NULL,
	cantidad INT UNSIGNED NOT NULL,

	CONSTRAINT pk_asientos_reservados
	PRIMARY KEY (vuelo,fecha,clase),

	CONSTRAINT FK_asientos_reservados_instancias_vuelo
	FOREIGN KEY (vuelo, fecha) REFERENCES instancias_vuelo (vuelo, fecha),

	CONSTRAINT FK_asientos_reservados_clases
	FOREIGN KEY (clase) REFERENCES clases (nombre)

) ENGINE=InnoDB;



##############################################################################
#							CREACION DE VISTAS
##############################################################################

	CREATE VIEW vuelos_disponibles AS
	SELECT DISTINCT
		s.vuelo AS nro_vuelo,
		s.modelo_avion AS modelo,
		iv.fecha,
		s.dia AS dia_sale,
		s.hora_sale,
		s.hora_llega,
		TIMEDIFF(s.hora_llega, s.hora_sale) AS tiempo_estimado,
		vp.aeropuerto_salida AS codigo_aero_sale,
		ae_s.nombre AS nombre_aero_sale,
		ae_s.ciudad AS ciudad_sale,
		ae_s.estado AS estado_sale,
		ae_s.pais AS pais_sale,
		vp.aeropuerto_llegada AS codigo_aero_llega,
		ae_ll.nombre AS nombre_aero_llega,
		ae_ll.ciudad AS ciudad_llega,
		ae_ll.estado AS estado_llega,
		ae_ll.pais AS pais_llega,
		b.precio,
		b.clase,
		ROUND ((b.cant_asientos * (1 + c.porcentaje)- ( SELECT COUNT(*) 
														FROM reserva_vuelo_clase
														WHERE reserva_vuelo_clase.vuelo = b.vuelo AND
															  reserva_vuelo_clase.clase = b.clase AND
															  reserva_vuelo_clase.fecha_vuelo = iv.fecha
														)

				)
			) AS asientos_disponibles
	FROM
		instancias_vuelo AS iv,
		salidas AS s,
		vuelos_programados AS vp,
		aeropuertos AS ae_s,
		aeropuertos AS ae_ll,
		brinda AS b,
		clases AS c
	WHERE
		iv.vuelo = s.vuelo AND
		iv.dia = s.dia AND
		s.vuelo = vp.numero AND
		vp.aeropuerto_salida = ae_s.codigo AND
		vp.aeropuerto_llegada = ae_ll.codigo AND
		b.vuelo = s.vuelo AND
		b.dia = s.dia AND
		b.clase = c.nombre
	;



##############################################################################
#							CREACION DE USUARIOS
##############################################################################

# Creamos el usuario admin 
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

# Otorgamos privilegios
# El usuario 'admin' tiene acceso total a todas las tablas de vuelos
# y puede crear nuevos usuarios y otorgar privilegios.
GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@'localhost' WITH GRANT OPTION;


# Creamos el usuario empleado
CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';

# El usuario empleado tiene acceso de lectura sobre todas las tablas
GRANT SELECT ON vuelos.* TO 'empleado'@'%';

# El usuario empleado tiene privilegios sobres ciertas tablas
GRANT INSERT,DELETE,UPDATE ON vuelos.reservas TO 'empleado'@'%';
GRANT INSERT,DELETE,UPDATE ON vuelos.pasajeros TO 'empleado'@'%';
GRANT INSERT,DELETE,UPDATE ON vuelos.reserva_vuelo_clase TO  'empleado'@'%';
 

# Creamos el usuario cliente
CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';
GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente'@'%';



##############################################################################
#							STORED PROCEDURES								 #
##############################################################################

delimiter !
CREATE PROCEDURE reservaSoloIda (IN vuelo VARCHAR(10), IN fecha DATE, IN clase VARCHAR(20), IN doc_tipo VARCHAR(45), IN doc_nro INT, IN legajo INT)
BEGIN

	DECLARE estado_reserva VARCHAR(15) DEFAULT 'En Espera';
	DECLARE cant_de_reservas INT;
	DECLARE vencimiento DATE;

	# Manejo de excepciones
	DECLARE codigo_SQL CHAR(5) DEFAULT '00000';
	DECLARE codigo_MYSQL INT DEFAULT 0;
	DECLARE mensaje_error TEXT;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			GET DIAGNOSTICS CONDITION 1
				codigo_MYSQL = MYSQL_ERRNO,
				codigo_SQL = RETURNED_SQLSTATE,
				mensaje_error = MESSAGE_TEXT;
			SELECT 'SQLEXCEPTION: transaccion abortada' AS resultado, codigo_MYSQL, codigo_SQL, mensaje_error;
			ROLLBACK;
		END;	

	START TRANSACTION;

		# Verificamos que existan los datos recibidos por parámetro
		IF EXISTS (SELECT * FROM instancias_vuelo AS iv WHERE (iv.vuelo = vuelo) AND (iv.fecha = fecha)) THEN
			IF EXISTS (SELECT * FROM brinda AS b WHERE (b.clase = clase) AND (b.vuelo = vuelo)) THEN
				IF EXISTS (SELECT * FROM pasajeros AS p WHERE (p.doc_tipo = doc_tipo) AND (p.doc_nro = doc_nro)) THEN
					IF EXISTS (SELECT * FROM empleados AS e WHERE (e.legajo = legajo)) THEN

						# Bloqueamos en modo exclusivo la fila en cuestion
						SELECT *		
						FROM asientos_reservados AS a_reservados
						WHERE ((a_reservados.clase = clase) AND
								(a_reservados.fecha = fecha) AND
								(a_reservados.vuelo = vuelo))
						FOR UPDATE;
						
						# Obtenemos los asientos disponibles de la VISTA que creamos
						SET @asientos_disponibles = (SELECT asientos_disponibles 
														FROM vuelos_disponibles AS vuelos_disp
														WHERE ((vuelos_disp.nro_vuelo = vuelo) AND 
																(vuelos_disp.fecha = fecha) AND 
																(vuelos_disp.clase = clase)));

						IF (@asientos_disponibles > 0) THEN			
							SET cant_de_reservas = (SELECT cantidad 
														FROM asientos_reservados AS a_reservados
														WHERE ((a_reservados.clase = clase) AND
																(a_reservados.fecha = fecha) AND
																(a_reservados.vuelo = vuelo)));
							
							# Seteamos el estado de la reserva
							IF (cant_de_reservas < @asientos_disponibles) THEN
								SET estado_reserva = 'Confirmada';
							END IF;							


							SET vencimiento = (SELECT DATE_SUB(fecha, INTERVAL 15 DAY));
							# Insertamos en las tablas correspondientes (recordar es solo viaje de ida)
							INSERT INTO reservas (fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) 
									VALUES (CURDATE(), vencimiento, estado_reserva, doc_tipo, doc_nro, legajo);

							INSERT INTO reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
									VALUES (LAST_INSERT_ID(), vuelo, fecha, clase);							
							
							UPDATE asientos_reservados AS a_reservados
							SET cantidad = cantidad + 1
							WHERE ((a_reservados.clase = clase) AND
									(a_reservados.fecha = fecha) AND
									(a_reservados.vuelo = vuelo));


							SELECT 'La reserva se ha registrado exitosamente' AS resultado;
							SELECT LAST_INSERT_ID() AS numero_reserva;

						ELSE
							SELECT 'ERROR: no hay disponibilidad para ese vuelo y clase' AS resultado, @asientos_disponibles;
						END IF;		

					ELSE
						SELECT 'ERROR: no existe un empleado con ese legajo' AS resultado;
					END IF;	
				ELSE
					SELECT 'ERROR: no existe un pasajero con ese tipo y numero de DNI' AS resultado;
				END IF;	
			ELSE
				SELECT 'ERROR: no existe la clase en ese vuelo' AS resultado;
			END IF;	
		ELSE
			SELECT 'ERROR: no existe un numero de vuelo en esa fecha' AS resultado;
		END IF;
	COMMIT;

END;
!



CREATE PROCEDURE reservaIdaVuelta (IN vuelo_ida VARCHAR(10), IN fecha_ida DATE, IN clase_ida VARCHAR(20), 
									IN vuelo_vuelta VARCHAR(10), IN fecha_vuelta DATE, IN clase_vuelta VARCHAR(20),
									IN doc_tipo VARCHAR(45), IN doc_nro INT, IN nro_legajo INT)
BEGIN

	DECLARE estado_reserva VARCHAR(15) DEFAULT 'En Espera';
	DECLARE cant_de_reservas_ida INT;
	DECLARE cant_de_reservas_vuelta INT;
	DECLARE vencimiento_ida DATE;
	DECLARE vencimiento_vuelta DATE;

	# Manejo de excepciones
	DECLARE codigo_SQL CHAR(5) DEFAULT '00000';
	DECLARE codigo_MYSQL INT DEFAULT 0;
	DECLARE mensaje_error TEXT;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			GET DIAGNOSTICS CONDITION 1
				codigo_MYSQL = MYSQL_ERRNO,
				codigo_SQL = RETURNED_SQLSTATE,
				mensaje_error = MESSAGE_TEXT;
			SELECT 'SQLEXCEPTION: transaccion abortada' AS resultado, codigo_MYSQL, codigo_SQL, mensaje_error;
			ROLLBACK;
		END;	

	START TRANSACTION;

		# Verificamos que existan los datos recibidos por parámetro
		IF EXISTS (SELECT * FROM instancias_vuelo AS iv WHERE (iv.vuelo = vuelo_ida) AND (iv.fecha = fecha_ida)) AND
		   EXISTS (SELECT * FROM instancias_vuelo AS iv WHERE (iv.vuelo = vuelo_vuelta) AND (iv.fecha = fecha_vuelta)) THEN

			IF EXISTS (SELECT * FROM brinda AS b WHERE (b.clase = clase_ida) AND (b.vuelo = vuelo_ida)) AND
			   EXISTS (SELECT * FROM brinda AS b WHERE (b.clase = clase_vuelta) AND (b.vuelo = vuelo_vuelta)) THEN

				IF EXISTS (SELECT * FROM pasajeros AS p WHERE (p.doc_tipo = doc_tipo) AND (p.doc_nro = doc_nro)) THEN

					IF EXISTS (SELECT * FROM empleados AS e WHERE (e.legajo = legajo)) THEN


						# Bloqueamos en modo exclusivo los asientos reservados de IDA
						SELECT *		
						FROM asientos_reservados AS a_reservados_ida
						WHERE ((a_reservados.clase = clase_ida) AND
								(a_reservados.fecha = fecha_ida) AND
								(a_reservados.vuelo = vuelo_ida))
						FOR UPDATE;

						# Bloqueamos en modo exclusivo los asientos reservados de VUELTA
						SELECT *		
						FROM asientos_reservados AS a_reservados_vuelta
						WHERE ((a_reservados.clase = clase_vuelta) AND
								(a_reservados.fecha = fecha_vuelta) AND
								(a_reservados.vuelo = vuelo_vuelta))
						FOR UPDATE;

						

						# Obtenemos los asientos disponibles de IDA de la VISTA que creamos
						SET @asientos_disponibles_ida = (SELECT asientos_disponibles 
														FROM vuelos_disponibles AS vuelos_disp
														WHERE ((vuelos_disp.nro_vuelo = vuelo_ida) AND 
																(vuelos_disp.fecha = fecha_ida) AND 
																(vuelos_disp.clase = clase_ida)));

						# Obtenemos los asientos disponibles de VUELTA de la VISTA que creamos
						SET @asientos_disponibles_vuelta = (SELECT asientos_disponibles 
															FROM vuelos_disponibles AS vuelos_disp
															WHERE ((vuelos_disp.nro_vuelo = vuelo_vuelta) AND 
																	(vuelos_disp.fecha = fecha_vuelta) AND 
																	(vuelos_disp.clase = clase_vuelta)));



						IF (@asientos_disponibles_ida > 0) THEN	
							IF (@asientos_disponibles_vuelta > 0) THEN		

								SET cant_de_reservas_ida = (SELECT cantidad 
															FROM asientos_reservados AS a_reservados
															WHERE ((a_reservados.clase = clase_ida) AND
																	(a_reservados.fecha = fecha_ida) AND
																	(a_reservados.vuelo = vuelo_ida)));

								SET cant_de_reservas_vuelta = (SELECT cantidad 
																FROM asientos_reservados AS a_reservados
																WHERE ((a_reservados.clase = clase_vuelta) AND
																		(a_reservados.fecha = fecha_vuelta) AND
																		(a_reservados.vuelo = vuelo_vuelta)));

							
								# Seteamos el estado de la reserva
								IF ((cant_de_reservas_ida < @asientos_disponibles_ida) AND (cant_de_reservas_vuelta < @asientos_disponibles_vuelta)) THEN
									SET estado_reserva = 'Confirmada';
								END IF;		


								SET vencimiento_ida = (SELECT DATE_SUB(fecha_ida, INTERVAL 15 DAY));


								# Insertamos en las tablas correspondientes (recordar es viaje de ida y de vuelta)
								INSERT INTO reservas (fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) 
										VALUES (CURDATE(), vencimiento_ida, estado_reserva, doc_tipo, doc_nro, legajo);

								INSERT INTO reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
										VALUES (LAST_INSERT_ID(), vuelo_ida, fecha_ida, clase_ida);

								INSERT INTO reserva_vuelo_clase (numero, vuelo, fecha_vuelo, clase)
										VALUES (LAST_INSERT_ID(), vuelo_vuelta, fecha_vuelta, clase_vuelta);


								# Actualizamos los asientos reservados de ida y de vuelta
								UPDATE asientos_reservados AS a_reservados_ida
								SET cantidad = cantidad + 1
								WHERE ((a_reservados_ida.clase = clase_ida) AND
										(a_reservados_ida.fecha = fecha_ida) AND
										(a_reservados_ida.vuelo = vuelo_ida));

								UPDATE asientos_reservados AS a_reservados_vuelta
								SET cantidad = cantidad + 1
								WHERE ((a_reservados_vuelta.clase = clase_vuelta) AND
										(a_reservados_vuelta.fecha = fecha_vuelta) AND
										(a_reservados_vuelta.vuelo = vuelo_vuelta));


								SELECT 'La reserva se ha registrado exitosamente' AS resultado;
								SELECT LAST_INSERT_ID() AS numero_reserva;


							ELSE
								SELECT 'ERROR: no hay disponibilidad para ese vuelo de vuelta y clase' AS resultado;
							END IF;
						ELSE
							SELECT 'ERROR: no hay disponibilidad para ese vuelo de ida y clase' AS resultado;
						END IF;	
					ELSE
						SELECT 'ERROR: no existe un empleado con ese legajo' AS resultado;
					END IF;	
				ELSE
					SELECT 'ERROR: no existe un pasajero con ese tipo y numero de DNI' AS resultado;
				END IF;	
			ELSE
				SELECT 'ERROR: no existe la clase en ese vuelo' AS resultado;
			END IF;	
		ELSE
			SELECT 'ERROR: no existe un numero de vuelo en esa fecha' AS resultado;
		END IF;
	COMMIT;
END;!

DELIMITER ; # FIN STORED PROCEDURES



GRANT EXECUTE ON PROCEDURE vuelos.reservaSoloIda TO 'empleado'@'%';
GRANT EXECUTE ON PROCEDURE vuelos.reservaIdaVuelta TO 'empleado'@'%';