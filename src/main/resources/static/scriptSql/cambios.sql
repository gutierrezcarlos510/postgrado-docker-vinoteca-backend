-- 4/09/2024
INSERT INTO business.area_producto (nombre,descripcion,icono) VALUES ('Area General','Area generica','fa fa-user');
INSERT INTO business.tipo_producto (nombre,descripcion) VALUES ('Tipo General','Todas las categorias');

--05/09/2024
COMMENT ON COLUMN business.caracteristica.tipo IS '1= presentacion en unidades, 2 = presentacion en cajas, 3= tipo cliente';
INSERT INTO business.caracteristica (nombre,tipo) VALUES ('Unidad','1');
INSERT INTO business.caracteristica (nombre,tipo) VALUES ('Caja','2');
INSERT INTO business.caracteristica (nombre,tipo) VALUES ('Cliente General','3');

ALTER TABLE business.ciudad RENAME COLUMN estado TO status;
ALTER TABLE business.zona RENAME COLUMN estado TO status;
ALTER TABLE business.barrio RENAME COLUMN estado TO status;
ALTER TABLE business.caracteristica RENAME COLUMN estado TO status;
ALTER TABLE business.categoria_producto RENAME COLUMN estado TO status;
ALTER TABLE business.tipo_producto RENAME COLUMN estado TO status;

--06/09/2024 insercion de submenus

INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(43, 'Gestiona todo lo referente a productos', 'gradient', 'Producto', true, '../business/producto');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(45, 'Gestiona las categorias de productos', 'close', 'Categoria', true, '../business/categoria');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(44, 'Vista de todos los productos que hay en el inventario', 'style', 'Inventario', true, '../business/inventario');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(42, 'Gestiona los clientes de la empresa', 'emoji_people', 'Clientes', true, '../business/cliente');

--11/09/2024
UPDATE public.submenus SET url='../business/ingreso' WHERE id=29;
-- DROP PROCEDURE business.delete_ingreso(int8, int8);

CREATE OR REPLACE PROCEDURE business.delete_ingreso(p_ingreso_id bigint, p_user_id bigint)
 LANGUAGE plpgsql
AS $procedure$
declare
	DECLARE v_cursor CURSOR FOR select i.producto_id, i.cantidad_total_unitaria, i.almacen_id from business.ingreso_detalle i where i.ingreso_id = P_INGRESO_ID;
DECLARE V_PRODUCTO_ID int4;
    DECLARE V_CAN_UNITARIA int4;
    DECLARE V_COD_ALM int8;
	DECLARE V_CANTIDAD_NEGATIVA int4;
	DECLARE V_TIPO_INGRESO_NORMAL character varying := '1';
begin
update business.ingreso set status = false, updated_at = current_timestamp,updated_by = P_USER_ID where id = P_INGRESO_ID;
OPEN v_cursor;
loop
FETCH v_cursor INTO V_PRODUCTO_ID,V_CAN_UNITARIA,V_COD_ALM;
        EXIT WHEN NOT FOUND;
update business.almacen set cantidad = cantidad - V_CAN_UNITARIA where id = V_COD_ALM;
V_CANTIDAD_NEGATIVA := -1 * V_CAN_UNITARIA;
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
VALUES(V_COD_ALM, V_TIPO_INGRESO_NORMAL, V_CANTIDAD_NEGATIVA, 'Reversion de ingreso normal', current_timestamp);
end loop;
close v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;

-- DROP PROCEDURE business.delete_movimiento_egreso(int8, int8);

CREATE OR REPLACE PROCEDURE business.delete_movimiento_egreso(p_movimiento_egreso_id bigint, p_user_id bigint)
 LANGUAGE plpgsql
AS $procedure$
declare
	DECLARE v_cursor CURSOR FOR select almacen_id, cantidad from business.almacen_movimiento where movimiento_inventario_id = p_movimiento_egreso_id;
DECLARE V_CAN_UNITARIA int4;
    DECLARE V_COD_ALM int8;
	DECLARE V_TIPO_MOVIMIENTO_EGRESO character varying := '4';
begin
update business.movimiento_inventario set status = false, updated_at = current_timestamp,updated_by = P_USER_ID where id = p_movimiento_egreso_id;
OPEN v_cursor;
loop
FETCH v_cursor INTO V_COD_ALM,V_CAN_UNITARIA;
        EXIT WHEN NOT FOUND;
update business.almacen set cantidad = cantidad + V_CAN_UNITARIA where id = V_COD_ALM;
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
VALUES(V_COD_ALM, V_TIPO_MOVIMIENTO_EGRESO, V_CAN_UNITARIA, 'Reversion de movimiento de inventario para egreso', current_timestamp);
end loop;
close v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;

-- DROP PROCEDURE business.delete_movimiento_ingreso(int8, int8);

CREATE OR REPLACE PROCEDURE business.delete_movimiento_ingreso(p_movimiento_ingreso_id bigint, p_user_id bigint)
 LANGUAGE plpgsql
AS $procedure$
declare
	DECLARE v_cursor CURSOR FOR SELECT almacen_id, cantidad FROM business.almacen_movimiento m where m.movimiento_inventario_id = p_movimiento_ingreso_id;
DECLARE V_CAN_UNITARIA int4;
    DECLARE V_COD_ALM int8;
	DECLARE V_CANTIDAD_NEGATIVA int4;
	DECLARE V_TIPO_MOVIMIENTO_INGRESO character varying := '3';
begin
update business.movimiento_inventario set status = false, updated_at = current_timestamp,updated_by = P_USER_ID where id = p_movimiento_ingreso_id;
OPEN v_cursor;
loop
FETCH v_cursor INTO V_COD_ALM,V_CAN_UNITARIA;
        EXIT WHEN NOT FOUND;
update business.almacen set cantidad = cantidad - V_CAN_UNITARIA where id = V_COD_ALM;
V_CANTIDAD_NEGATIVA := -1 * V_CAN_UNITARIA;
RAISE NOTICE '%', V_COD_ALM;
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
VALUES(V_COD_ALM, V_TIPO_MOVIMIENTO_INGRESO, V_CANTIDAD_NEGATIVA, 'Reversion de movimiento de inventario para ingreso', current_timestamp);
end loop;
close v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;

-- DROP PROCEDURE business.save_detalles_ingreso(int8, int4, _int4, _varchar, _varchar, _varchar, _int4, _int4);

CREATE OR REPLACE PROCEDURE business.save_detalles_ingreso(p_ingreso_id bigint, p_sucursal_id integer, productos integer[], lotes character varying[], elaboraciones character varying[], vencimientos character varying[], c_unidades integer[], c_cajas integer[])
 LANGUAGE plpgsql
AS $procedure$
declare
v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_can_unitaria int4;
    v_cod_alm int8;
	V_TIPO_INGRESO_NORMAL character varying := '1';
begin
    while v_index <= v_length loop
		if vencimientos[v_index] = '-' then
			vencimientos[v_index] = null;
end if;
		if elaboraciones[v_index] = '-' then
			elaboraciones[v_index] = null;
end if;
		if lotes[v_index] = '-' then
			lotes[v_index] = null;
end if;
        -- OBTENER PRODUCTO --
select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
-- Calcular el total de productos
v_can_unitaria := (c_cajas[v_index] * v_unixcaja) + c_unidades[v_index];
		--Consulta para buscar codigo de almacen si existe
SELECT coalesce(max(id),0) into v_cod_alm FROM business.almacen
where producto_id = productos[v_index] and branch_office_id = p_sucursal_id and fecha_vencimiento = to_date(vencimientos[v_index], 'DD/MM/YY') and lote = lotes[v_index];
--REGISTRO EN ALMACEN
if v_cod_alm = 0 then --Si no se tiene el producto en almacen, segun vencimiento, lote y sucursal
			v_cod_alm = (select nextval('business.almacen_id_seq'::regclass));
INSERT INTO business.almacen(id, branch_office_id, cantidad, producto_id, fecha_vencimiento, lote)
VALUES(v_cod_alm, p_sucursal_id, v_can_unitaria, productos[v_index], to_date(vencimientos[v_index], 'DD/MM/YY'), lotes[v_index]);

INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(v_cod_alm, V_TIPO_INGRESO_NORMAL, v_can_unitaria, 'Nuevo detalle en almacen', current_timestamp);
else --en el caso de tener se actualiza el almacen
update business.almacen set cantidad = cantidad + v_can_unitaria where id = v_cod_alm;
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(v_cod_alm, V_TIPO_INGRESO_NORMAL, v_can_unitaria, 'Actualizando detalle en almacen', current_timestamp);
end if;
INSERT INTO business.ingreso_detalle(id, ingreso_id, producto_id, lote, fecha_elaboracion, fecha_vencimiento, cantidad_unitaria, cantidad_caja, cantidad_total_unitaria, almacen_id)
VALUES(v_index, p_ingreso_id, productos[v_index], lotes[v_index],to_date(elaboraciones[v_index], 'DD/MM/YY'),to_date(vencimientos[v_index], 'DD/MM/YY'),c_unidades[v_index],c_cajas[v_index],v_can_unitaria, v_cod_alm);
v_index := v_index + 1;
end LOOP;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;
--14/09/2024 script de la vista de ciudad
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(49, 'Gestiona ciudad zona y barrio', 'tune', 'Ciudad', true, '../../business/ciudad');

-- DROP PROCEDURE business.save_movimiento_egreso(int8, int4, _int4, _int4, _int4);

CREATE OR REPLACE PROCEDURE business.save_movimiento_egreso(p_movimiento_id bigint, p_sucursal_id integer, productos integer[], c_unidades integer[], c_cajas integer[])
 LANGUAGE plpgsql
AS $procedure$
declare
v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_cantidad_detalle int4;
	v_cantidad_detalle_aux int4;
    v_cod_alm int8;
	array_almacen int8[];
	array_cantidad int4[];
	array_fvencimiento date[];
	array_tam int4;
	array_index integer;
	cantidad_existente_almacen int4;
	V_TIPO_EGRESO_MOVIMIENTO character varying := '4';
begin
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
	    RAISE NOTICE 'producto: %',productos[v_index];
select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
-- Calcular el total de productos
v_cantidad_detalle := (c_cajas[v_index] * v_unixcaja) + c_unidades[v_index];
		cantidad_existente_almacen := (select coalesce(sum(cantidad),0) from business.almacen where producto_id = productos[v_index] and branch_office_id = p_sucursal_id and cantidad > 0);
		--Validar si existe la cantidad suficiente en almacen
		RAISE NOTICE 'Cantidad en almacen: % y la cantidad de detalle: %',cantidad_existente_almacen,v_cantidad_detalle;
		if cantidad_existente_almacen >= v_cantidad_detalle then
			INSERT INTO business.movimiento_inventario_detalle(id, movimiento_inventario_id, producto_id, lote, fecha_elaboracion, fecha_vencimiento, cantidad_unidad, cantidad_caja, cantidad_total_unidad)
		VALUES(v_index, p_movimiento_id, productos[v_index], null,null,null,c_unidades[v_index],c_cajas[v_index],v_cantidad_detalle);
			v_cantidad_detalle_aux := v_cantidad_detalle;
			--Consulta para buscar todos los almacenes con cantidad del producto
SELECT array_agg(id),array_agg(cantidad) into array_almacen,array_cantidad,array_fvencimiento FROM business.almacen where producto_id = productos[v_index] and branch_office_id = p_sucursal_id and cantidad > 0;
array_tam := array_length(array_almacen, 1);
			array_index := 1;
			while (array_index <= array_tam and v_cantidad_detalle_aux > 0) loop
				if array_cantidad[array_index] >= v_cantidad_detalle_aux then --Si cantidad en almacen es mayor o igual se descuenta todo el detalle restante y termina el ciclo
					--Actualizamos almacen con la cantidad a descontar del detalle restante
update business.almacen set cantidad = cantidad - v_cantidad_detalle_aux where id = array_almacen[array_index];
--creacion de log de movimiento del producto
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_EGRESO_MOVIMIENTO, (-1 * v_cantidad_detalle_aux), 'Movimiento de inventario: Egreso', current_timestamp);
INSERT INTO business.almacen_movimiento(almacen_id, movimiento_inventario_id, movimiento_inventario_detalle_id, cantidad)VALUES(array_almacen[array_index], p_movimiento_id, v_index, v_cantidad_detalle_aux);
RAISE NOTICE 'el almacen es mayor al detalle % mayor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(array_cantidad[array_index]- v_cantidad_detalle_aux);
					v_cantidad_detalle_aux := 0;
else
					--Actualizamos almacen a cero ya que hay mas en la cantidad de detalle
update business.almacen set cantidad = 0 where id = array_almacen[array_index];
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_EGRESO_MOVIMIENTO, (-1 * array_cantidad[array_index]), 'Movimiento de inventario: Egreso', current_timestamp);
INSERT INTO business.almacen_movimiento(almacen_id, movimiento_inventario_id, movimiento_inventario_detalle_id, cantidad)VALUES(array_almacen[array_index], p_movimiento_id, v_index, array_cantidad[array_index]);
RAISE NOTICE 'el almacen es menor al detalle % menor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(v_cantidad_detalle_aux - array_cantidad[array_index]);
					v_cantidad_detalle_aux := (v_cantidad_detalle_aux - array_cantidad[array_index]);
end if;
				array_index := array_index +1;
end loop;
else
			RAISE EXCEPTION 'No Existe la cantidad necesaria para disminuir del producto %',productos[v_index];
end if;
        v_index := v_index + 1;
end LOOP;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'ERROR procedimiento %',sqlerrm;
END
$procedure$
;

-- DROP PROCEDURE business.save_movimiento_ingreso(int8, int4, _int4, _varchar, _varchar, _varchar, _int4, _int4);

CREATE OR REPLACE PROCEDURE business.save_movimiento_ingreso(p_movimiento_id bigint, p_sucursal_id integer, productos integer[], lotes character varying[], elaboraciones character varying[], vencimientos character varying[], c_unidades integer[], c_cajas integer[])
 LANGUAGE plpgsql
AS $procedure$
declare
v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_can_unitaria int4;
    v_cod_alm int8;
	V_TIPO_INGRESO_MOVIMIENTO character varying := '3';
begin
    while v_index <= v_length loop
		if vencimientos[v_index] = '-' then
			vencimientos[v_index] = null;
end if;
		if elaboraciones[v_index] = '-' then
			elaboraciones[v_index] = null;
end if;
		if lotes[v_index] = '-' then
			lotes[v_index] = null;
end if;
        -- OBTENER PRODUCTO --
select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
-- Calcular el total de productos
v_can_unitaria := (c_cajas[v_index] * v_unixcaja) + c_unidades[v_index];
		--Consulta para buscar codigo de almacen si existe
SELECT coalesce(max(id),0) into v_cod_alm FROM business.almacen
where producto_id = productos[v_index] and branch_office_id = p_sucursal_id and fecha_vencimiento = to_date(vencimientos[v_index], 'DD/MM/YY') and lote = lotes[v_index];
--REGISTRO EN ALMACEN
if v_cod_alm = 0 then --Si no se tiene el producto en almacen, segun vencimiento, lote y sucursal
			v_cod_alm = (select nextval('business.almacen_id_seq'::regclass));
INSERT INTO business.almacen(id, branch_office_id, cantidad, producto_id, fecha_vencimiento, lote)
VALUES(v_cod_alm, p_sucursal_id, v_can_unitaria, productos[v_index], to_date(vencimientos[v_index], 'DD/MM/YY'), lotes[v_index]);

INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(v_cod_alm, V_TIPO_INGRESO_MOVIMIENTO, v_can_unitaria, 'Movimiento de inventario: INGRESO', current_timestamp);
else --en el caso de tener se actualiza el almacen
update business.almacen set cantidad = cantidad + v_can_unitaria where id = v_cod_alm;
INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(v_cod_alm, V_TIPO_INGRESO_MOVIMIENTO, v_can_unitaria, 'Actualizando detalle en almacen por: Movimiento de inventario: INGRESO', current_timestamp);
end if;
INSERT INTO business.movimiento_inventario_detalle(id, movimiento_inventario_id, producto_id, lote, fecha_elaboracion, fecha_vencimiento, cantidad_unidad, cantidad_caja, cantidad_total_unidad)
VALUES(v_index, p_movimiento_id, productos[v_index], lotes[v_index],to_date(elaboraciones[v_index], 'DD/MM/YY'),to_date(vencimientos[v_index], 'DD/MM/YY'),c_unidades[v_index],c_cajas[v_index],v_can_unitaria);

INSERT INTO business.almacen_movimiento(almacen_id, movimiento_inventario_id, movimiento_inventario_detalle_id, cantidad)VALUES(v_cod_alm, p_movimiento_id, v_index, v_can_unitaria);
v_index := v_index + 1;
end LOOP;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;

--13/09/2024
-- Auto-generated SQL script #202409132225
UPDATE public.submenus SET "name"='Movimientos',description='Gestiona los movimientos de inventario',url='../business/movimientoInventario' WHERE id=34;
-- business.almacen definition

-- Drop table

-- DROP TABLE business.almacen;

CREATE TABLE business.almacen (
                                  id bigserial NOT NULL,
                                  branch_office_id int4 NOT NULL,
                                  cantidad int4 NOT NULL,
                                  producto_id int4 NULL,
                                  fecha_vencimiento date NULL,
                                  lote varchar(25) NULL,
                                  CONSTRAINT almacen_pk PRIMARY KEY (id)
);


-- business.area_producto definition

-- Drop table

-- DROP TABLE business.area_producto;

CREATE TABLE business.area_producto (
                                        id smallserial NOT NULL,
                                        nombre varchar(100) NOT NULL,
                                        descripcion varchar(500) NULL,
                                        icono varchar(25) NOT NULL,
                                        status bool NOT NULL DEFAULT true,
                                        CONSTRAINT area_producto_pk PRIMARY KEY (id)
);


-- business.caracteristica definition

-- Drop table

-- DROP TABLE business.caracteristica;

CREATE TABLE business.caracteristica (
                                         id smallserial NOT NULL,
                                         nombre varchar(150) NOT NULL,
                                         tipo varchar(1) NOT NULL,
                                         status bool NOT NULL DEFAULT true,
                                         CONSTRAINT caracteristica_pk PRIMARY KEY (id)
);


-- business.ciudad definition

-- Drop table

-- DROP TABLE business.ciudad;

CREATE TABLE business.ciudad (
                                 id smallserial NOT NULL,
                                 nombre varchar(100) NOT NULL,
                                 status bool NOT NULL DEFAULT true,
                                 CONSTRAINT ciudad_pk PRIMARY KEY (id)
);


-- business.historico definition

-- Drop table

-- DROP TABLE business.historico;

CREATE TABLE business.historico (
                                    id serial4 NOT NULL,
                                    fecha date NOT NULL,
                                    CONSTRAINT historico_pk PRIMARY KEY (id)
);


-- business.salida definition

-- Drop table

-- DROP TABLE business.salida;

CREATE TABLE business.salida (
                                 id bigserial NOT NULL,
                                 fecha date NOT NULL,
                                 distribuidor_id int8 NOT NULL,
                                 obs varchar(200) NULL,
                                 estado_salida varchar(1) NOT NULL,
                                 branch_office_id int4 NOT NULL,
                                 obs_al_finalizar varchar(200) NULL,
                                 created_by int8 NOT NULL,
                                 created_at timestamp NOT NULL,
                                 updated_by int8 NULL,
                                 updated_at timestamp NULL,
                                 status bool NOT NULL DEFAULT true,
                                 total_venta_contado numeric(10, 2) NOT NULL,
                                 total_otro_tipo_pago numeric(10, 2) NOT NULL,
                                 total_gastos numeric(10, 2) NOT NULL,
                                 total_efectivo_entregado numeric(10, 2) NOT NULL,
                                 total_descuento numeric(10, 2) NOT NULL,
                                 total_impuesto numeric(10, 2) NOT NULL,
                                 total_general numeric(10, 2) NOT NULL,
                                 CONSTRAINT salida_pk PRIMARY KEY (id)
);


-- business.log_movimiento_almacen definition

-- Drop table

-- DROP TABLE business.log_movimiento_almacen;

CREATE TABLE business.log_movimiento_almacen (
                                                 almacen_id int8 NOT NULL,
                                                 tipo varchar(1) NOT NULL,
                                                 cantidad int4 NOT NULL,
                                                 obs varchar(50) NOT NULL,
                                                 fecha timestamp NOT NULL,
                                                 CONSTRAINT log_movimiento_almacen_almacen_fk FOREIGN KEY (almacen_id) REFERENCES business.almacen(id)
);


-- business.salida_entrega definition

-- Drop table

-- DROP TABLE business.salida_entrega;

CREATE TABLE business.salida_entrega (
                                         id int2 NOT NULL,
                                         created_by int4 NOT NULL,
                                         created_at timestamp NOT NULL,
                                         updated_by int8 NULL,
                                         updated_at timestamp NULL,
                                         obs varchar(200) NULL,
                                         status bool NULL DEFAULT true,
                                         salida_id int8 NOT NULL,
                                         CONSTRAINT salida_entrega_pk PRIMARY KEY (id, salida_id),
                                         CONSTRAINT salida_entrega_salida_fk FOREIGN KEY (salida_id) REFERENCES business.salida(id)
);


-- business.salida_entrega_detalle definition

-- Drop table

-- DROP TABLE business.salida_entrega_detalle;

CREATE TABLE business.salida_entrega_detalle (
                                                 salida_id int8 NOT NULL,
                                                 salida_entrega_id int2 NOT NULL,
                                                 id int2 NOT NULL,
                                                 producto_id int4 NOT NULL,
                                                 cantidad_caja int4 NOT NULL,
                                                 cantidad_unidad int4 NOT NULL,
                                                 cantidad_total int4 NOT NULL,
                                                 status bool NOT NULL,
                                                 CONSTRAINT salida_entrega_detalle_unique UNIQUE (salida_id, salida_entrega_id, id),
                                                 CONSTRAINT salida_entrega_detalle_salida_entrega_fk FOREIGN KEY (salida_entrega_id,salida_id) REFERENCES business.salida_entrega(id,salida_id)
);


-- business.salida_entrega_detalle_almacen definition

-- Drop table

-- DROP TABLE business.salida_entrega_detalle_almacen;

CREATE TABLE business.salida_entrega_detalle_almacen (
                                                         salida_id int8 NOT NULL,
                                                         salida_entrega_detalle_id int2 NOT NULL,
                                                         salida_entrega_id int2 NOT NULL,
                                                         almacen_id int8 NOT NULL,
                                                         cantidad int4 NOT NULL,
                                                         CONSTRAINT salida_entrega_detalle_almacen_unique UNIQUE (salida_id, salida_entrega_detalle_id, salida_entrega_id, almacen_id),
                                                         CONSTRAINT salida_entrega_detalle_almacen_salida_entrega_detalle_fk FOREIGN KEY (salida_id,salida_entrega_id,salida_entrega_detalle_id) REFERENCES business.salida_entrega_detalle(salida_id,salida_entrega_id,id)
);


-- business.salida_resumen definition

-- Drop table

-- DROP TABLE business.salida_resumen;

CREATE TABLE business.salida_resumen (
                                         salida_id int8 NOT NULL,
                                         producto_id int4 NOT NULL,
                                         cantidad_total_entregada int4 NOT NULL,
                                         cantidad_total_devuelta int4 NOT NULL,
                                         cantidad_total_guardada int4 NOT NULL,
                                         cantidad_total_vendida int4 NOT NULL,
                                         CONSTRAINT salida_resumen_pk PRIMARY KEY (salida_id, producto_id),
                                         CONSTRAINT salida_resumen_salida_fk FOREIGN KEY (salida_id) REFERENCES business.salida(id)
);


-- business.tipo_producto definition

-- Drop table

-- DROP TABLE business.tipo_producto;

CREATE TABLE business.tipo_producto (
                                        id smallserial NOT NULL,
                                        nombre varchar(150) NOT NULL,
                                        descripcion varchar(500) NOT NULL,
                                        status bool NOT NULL DEFAULT true,
                                        area_producto_id int2 NOT NULL,
                                        CONSTRAINT tipo_pkey PRIMARY KEY (id),
                                        CONSTRAINT tipo_producto_area_producto_fk FOREIGN KEY (area_producto_id) REFERENCES business.area_producto(id)
);


-- business.visita definition

-- Drop table

-- DROP TABLE business.visita;

CREATE TABLE business.visita (
                                 id bigserial NOT NULL,
                                 salida_id int8 NOT NULL,
                                 fecha timestamp NOT NULL,
                                 cliente_id int8 NOT NULL,
                                 motivo varchar(50) NOT NULL,
                                 status bool NOT NULL DEFAULT true,
                                 CONSTRAINT visita_pk PRIMARY KEY (id),
                                 CONSTRAINT visita_salida_fk FOREIGN KEY (salida_id) REFERENCES business.salida(id)
);


-- business.zona definition

-- Drop table

-- DROP TABLE business.zona;

CREATE TABLE business.zona (
                               id smallserial NOT NULL,
                               nombre varchar(100) NOT NULL,
                               status bool NOT NULL DEFAULT true,
                               ciudad_id int2 NOT NULL,
                               CONSTRAINT zona_pk PRIMARY KEY (id),
                               CONSTRAINT zona_ciudad_id_fkey FOREIGN KEY (ciudad_id) REFERENCES business.ciudad(id)
);


-- business.almacen_distribuidor definition

-- Drop table

-- DROP TABLE business.almacen_distribuidor;

CREATE TABLE business.almacen_distribuidor (
                                               salida_id int8 NOT NULL,
                                               salida_entrega_id int2 NOT NULL,
                                               salida_entrega_detalle_id int2 NOT NULL,
                                               almacen_id int8 NOT NULL,
                                               cantidad int4 NOT NULL,
                                               distribuidor_id int8 NOT NULL,
                                               CONSTRAINT almacen_distribuidor_unique UNIQUE (salida_id, salida_entrega_id, salida_entrega_detalle_id, almacen_id, distribuidor_id),
                                               CONSTRAINT almacen_distribuidor_salida_entrega_detalle_almacen_fk FOREIGN KEY (salida_id,salida_entrega_detalle_id,salida_entrega_id,almacen_id) REFERENCES business.salida_entrega_detalle_almacen(salida_id,salida_entrega_detalle_id,salida_entrega_id,almacen_id)
);


-- business.barrio definition

-- Drop table

-- DROP TABLE business.barrio;

CREATE TABLE business.barrio (
                                 id smallserial NOT NULL,
                                 nombre varchar(100) NOT NULL,
                                 status bool NOT NULL DEFAULT true,
                                 zona_id int2 NOT NULL,
                                 CONSTRAINT barrio_pk PRIMARY KEY (id),
                                 CONSTRAINT ciudad_zona_id_fkey FOREIGN KEY (zona_id) REFERENCES business.zona(id)
);


-- business.categoria_producto definition

-- Drop table

-- DROP TABLE business.categoria_producto;

CREATE TABLE business.categoria_producto (
                                             id int2 NOT NULL DEFAULT nextval('business.categoria_id_seq'::regclass),
                                             nombre varchar(150) NOT NULL,
                                             descripcion varchar(500) NOT NULL,
                                             status bool NOT NULL DEFAULT true,
                                             tipo_producto_id int2 NOT NULL,
                                             CONSTRAINT categoria_pkey PRIMARY KEY (id),
                                             CONSTRAINT categoria_tipo_id_fkey FOREIGN KEY (tipo_producto_id) REFERENCES business.tipo_producto(id)
);


-- business.producto definition

-- Drop table

-- DROP TABLE business.producto;

CREATE TABLE business.producto (
                                   id serial4 NOT NULL,
                                   nombre_generico varchar(150) NOT NULL,
                                   nombre_comercial varchar(150) NOT NULL,
                                   descripcion varchar(1500) NULL,
                                   caracteristica varchar(250) NOT NULL,
                                   categoria_id int2 NOT NULL,
                                   foto varchar(30) NOT NULL,
                                   unidad_por_caja int2 NULL,
                                   stock_medio int2 NOT NULL,
                                   stock_alto int2 NOT NULL,
                                   pc_unit numeric(10, 2) NOT NULL,
                                   pc_caja numeric(10, 2) NULL,
                                   pv_unit numeric(10, 2) NOT NULL,
                                   pv_caja numeric(10, 2) NOT NULL,
                                   pv_unit_descuento numeric(10, 2) NOT NULL,
                                   pv_caja_descuento numeric(10, 2) NOT NULL,
                                   pv_unit_promo numeric(10, 2) NOT NULL,
                                   pv_caja_promo numeric(10, 2) NOT NULL,
                                   status bool NOT NULL DEFAULT true,
                                   presentacion_unit_id int2 NOT NULL,
                                   presentacion_caja_id int2 NOT NULL,
                                   CONSTRAINT producto_pk PRIMARY KEY (id),
                                   CONSTRAINT producto_categoria_fk FOREIGN KEY (categoria_id) REFERENCES business.categoria_producto(id),
                                   CONSTRAINT producto_presentacion_caja_fk FOREIGN KEY (presentacion_caja_id) REFERENCES business.caracteristica(id),
                                   CONSTRAINT producto_presentacion_unidad_fk FOREIGN KEY (presentacion_unit_id) REFERENCES business.caracteristica(id)
);


-- business.historico_inventario definition

-- Drop table

-- DROP TABLE business.historico_inventario;

CREATE TABLE business.historico_inventario (
                                               branch_office_id int4 NOT NULL,
                                               producto_id int4 NOT NULL,
                                               cantidad int4 NOT NULL,
                                               historico_id int4 NOT NULL,
                                               CONSTRAINT historico_inventario_unique UNIQUE (branch_office_id, producto_id, historico_id),
                                               CONSTRAINT historico_inventario_historico_fk FOREIGN KEY (historico_id) REFERENCES business.historico(id),
                                               CONSTRAINT historico_inventario_producto_fk FOREIGN KEY (producto_id) REFERENCES business.producto(id)
);


-- business.almacen_movimiento definition

-- Drop table

-- DROP TABLE business.almacen_movimiento;

CREATE TABLE business.almacen_movimiento (
                                             almacen_id int8 NOT NULL,
                                             movimiento_inventario_id int8 NOT NULL,
                                             movimiento_inventario_detalle_id int2 NOT NULL,
                                             cantidad int4 NULL,
                                             CONSTRAINT almacen_movimiento_pk PRIMARY KEY (almacen_id, movimiento_inventario_id, movimiento_inventario_detalle_id)
);


-- business.cliente definition

-- Drop table

-- DROP TABLE business.cliente;

CREATE TABLE business.cliente (
                                  id int8 NOT NULL,
                                  alias varchar(50) NOT NULL,
                                  direccion varchar(200) NOT NULL,
                                  email varchar(100) NOT NULL,
                                  nombre_negocio varchar(150) NOT NULL,
                                  descripcion_negocio varchar(500) NOT NULL,
                                  tipo_cliente int2 NOT NULL,
                                  barrio_id int2 NOT NULL,
                                  status bool NOT NULL DEFAULT true,
                                  CONSTRAINT cliente_pk PRIMARY KEY (id)
);


-- business.ingreso definition

-- Drop table

-- DROP TABLE business.ingreso;

CREATE TABLE business.ingreso (
                                  id bigserial NOT NULL,
                                  numero int8 NOT NULL,
                                  fecha date NOT NULL,
                                  usuario_recepcion_id int8 NOT NULL,
                                  usuario_entrega_id int8 NOT NULL,
                                  observacion varchar(500) NOT NULL,
                                  branch_office_id int4 NULL,
                                  created_at timestamp(0) NOT NULL DEFAULT now(),
                                  created_by int8 NOT NULL,
                                  updated_at timestamp(0) NULL,
                                  updated_by int8 NULL,
                                  status bool NOT NULL DEFAULT true,
                                  CONSTRAINT ingreso_pkey PRIMARY KEY (id)
);


-- business.ingreso_detalle definition

-- Drop table

-- DROP TABLE business.ingreso_detalle;

CREATE TABLE business.ingreso_detalle (
                                          id int2 NOT NULL,
                                          ingreso_id int8 NOT NULL,
                                          producto_id int4 NOT NULL,
                                          lote varchar(25) NULL,
                                          fecha_elaboracion date NULL,
                                          fecha_vencimiento date NULL,
                                          cantidad_unitaria int4 NULL,
                                          cantidad_caja int4 NOT NULL,
                                          cantidad_total_unitaria int4 NOT NULL,
                                          almacen_id int8 NULL,
                                          CONSTRAINT ingreso_detalle_pk PRIMARY KEY (id, ingreso_id)
);


-- business.movimiento_inventario definition

-- Drop table

-- DROP TABLE business.movimiento_inventario;

CREATE TABLE business.movimiento_inventario (
                                                id bigserial NOT NULL,
                                                usuario_id int8 NOT NULL,
                                                tipo varchar(1) NOT NULL,
                                                motivo varchar(500) NOT NULL,
                                                fecha date NOT NULL,
                                                created_by int8 NOT NULL,
                                                created_at timestamp NOT NULL,
                                                updated_by int8 NULL,
                                                updated_at timestamp NULL,
                                                status bool NOT NULL,
                                                sucursal_origen int4 NOT NULL,
                                                sucursal_destino int4 NULL,
                                                CONSTRAINT movimiento_inventario_pk PRIMARY KEY (id)
);


-- business.movimiento_inventario_detalle definition

-- Drop table

-- DROP TABLE business.movimiento_inventario_detalle;

CREATE TABLE business.movimiento_inventario_detalle (
                                                        id int2 NOT NULL,
                                                        movimiento_inventario_id int8 NOT NULL,
                                                        producto_id int4 NOT NULL,
                                                        lote varchar(25) NULL,
                                                        fecha_elaboracion date NULL,
                                                        fecha_vencimiento date NULL,
                                                        cantidad_unidad int4 NOT NULL,
                                                        cantidad_caja int4 NOT NULL,
                                                        cantidad_total_unidad int4 NOT NULL,
                                                        CONSTRAINT movimiento_inventario_detalle_pk PRIMARY KEY (id, movimiento_inventario_id)
);


-- business.pedido definition

-- Drop table

-- DROP TABLE business.pedido;

CREATE TABLE business.pedido (
                                 id bigserial NOT NULL,
                                 fecha timestamp NOT NULL,
                                 fecha_entrega timestamp NOT NULL,
                                 cliente_id int8 NOT NULL,
                                 observacion varchar(200) NOT NULL,
                                 tipo varchar(1) NOT NULL,
                                 subtotal numeric(10, 2) NOT NULL,
                                 impuesto numeric(10, 2) NOT NULL,
                                 descuento numeric(10, 2) NOT NULL,
                                 total numeric(10, 2) NOT NULL,
                                 distribuidor_id int8 NOT NULL,
                                 status bool NOT NULL DEFAULT true,
                                 CONSTRAINT pedido_pk PRIMARY KEY (id)
);


-- business.pedido_detalle definition

-- Drop table

-- DROP TABLE business.pedido_detalle;

CREATE TABLE business.pedido_detalle (
                                         pedido_id int8 NOT NULL,
                                         producto_id int4 NOT NULL,
                                         tipo_cantidad varchar(1) NOT NULL,
                                         cantidad int4 NOT NULL,
                                         precio numeric(10, 2) NOT NULL,
                                         subtotal numeric(10, 2) NOT NULL,
                                         CONSTRAINT pedido_detalle_pk PRIMARY KEY (pedido_id, producto_id, tipo_cantidad)
);


-- business.venta definition

-- Drop table

-- DROP TABLE business.venta;

CREATE TABLE business.venta (
                                id int8 NOT NULL,
                                fecha timestamp NOT NULL,
                                cliente_id int8 NOT NULL,
                                forma_pago int2 NOT NULL,
                                tipo_venta varchar(2) NOT NULL,
                                impuesto numeric(10, 2) NOT NULL,
                                descuento numeric(10, 2) NOT NULL,
                                subtotal numeric(10, 2) NOT NULL,
                                total numeric(10, 2) NOT NULL,
                                status bool NOT NULL DEFAULT true,
                                salida_id int8 NULL,
                                branch_office_id int4 NOT NULL,
                                usuario_id int8 NOT NULL,
                                CONSTRAINT venta_pk PRIMARY KEY (id)
);


-- business.venta_detalle definition

-- Drop table

-- DROP TABLE business.venta_detalle;

CREATE TABLE business.venta_detalle (
                                        venta_id int8 NOT NULL,
                                        producto_id int4 NOT NULL,
                                        cantidad int4 NOT NULL,
                                        precio numeric(10, 2) NOT NULL,
                                        subtotal numeric(10, 2) NOT NULL,
                                        descuento numeric(10, 2) NOT NULL,
                                        total numeric(10, 2) NOT NULL,
                                        cantidad_unitaria_total int4 NOT NULL,
                                        tipo_cantidad varchar(1) NOT NULL,
                                        CONSTRAINT detalle_venta_pk PRIMARY KEY (venta_id, producto_id, tipo_cantidad)
);


-- business.venta_subdetalle definition

-- Drop table

-- DROP TABLE business.venta_subdetalle;

CREATE TABLE business.venta_subdetalle (
                                           venta_id int8 NOT NULL,
                                           producto_id int4 NOT NULL,
                                           tipo_cantidad varchar(1) NOT NULL,
                                           id int2 NOT NULL,
                                           almacen_id int8 NOT NULL,
                                           cantidad_unitaria int4 NOT NULL,
                                           salida_id int8 NULL,
                                           salida_entrega_id int2 NULL,
                                           salida_entrega_detalle_id int2 NULL,
                                           distribuidor_id int8 NULL,
                                           CONSTRAINT venta_subdetalle_pk PRIMARY KEY (venta_id, producto_id, tipo_cantidad, id)
);


-- business.almacen_movimiento foreign keys

ALTER TABLE business.almacen_movimiento ADD CONSTRAINT almacen_movimiento_almacen_fk FOREIGN KEY (almacen_id) REFERENCES business.almacen(id);
ALTER TABLE business.almacen_movimiento ADD CONSTRAINT almacen_movimiento_movimiento_inventario_detalle_fk FOREIGN KEY (movimiento_inventario_detalle_id,movimiento_inventario_id) REFERENCES business.movimiento_inventario_detalle(id,movimiento_inventario_id);


-- business.cliente foreign keys

ALTER TABLE business.cliente ADD CONSTRAINT cliente_barrio_fk FOREIGN KEY (barrio_id) REFERENCES business.barrio(id);
ALTER TABLE business.cliente ADD CONSTRAINT cliente_persons_fk FOREIGN KEY (id) REFERENCES public.persons(id);
ALTER TABLE business.cliente ADD CONSTRAINT cliente_tipo_cliente_fk FOREIGN KEY (tipo_cliente) REFERENCES business.caracteristica(id);


-- business.ingreso foreign keys

ALTER TABLE business.ingreso ADD CONSTRAINT ingreso_branch_offices_fk FOREIGN KEY (branch_office_id) REFERENCES public.branch_offices(id);
ALTER TABLE business.ingreso ADD CONSTRAINT ingreso_user_entrega_fk FOREIGN KEY (usuario_entrega_id) REFERENCES public.systems_users(id);
ALTER TABLE business.ingreso ADD CONSTRAINT ingreso_user_recepcion_fk FOREIGN KEY (usuario_recepcion_id) REFERENCES public.systems_users(id);


-- business.ingreso_detalle foreign keys

ALTER TABLE business.ingreso_detalle ADD CONSTRAINT ingreso_detalle_almacen_fk FOREIGN KEY (almacen_id) REFERENCES business.almacen(id);
ALTER TABLE business.ingreso_detalle ADD CONSTRAINT ingreso_detalle_ingreso_fk FOREIGN KEY (ingreso_id) REFERENCES business.ingreso(id);
ALTER TABLE business.ingreso_detalle ADD CONSTRAINT ingreso_detalle_producto_fk FOREIGN KEY (producto_id) REFERENCES business.producto(id);


-- business.movimiento_inventario foreign keys

ALTER TABLE business.movimiento_inventario ADD CONSTRAINT movimiento_inventario_branch_offices_fk FOREIGN KEY (sucursal_origen) REFERENCES public.branch_offices(id);
ALTER TABLE business.movimiento_inventario ADD CONSTRAINT movimiento_inventario_systems_users_fk FOREIGN KEY (usuario_id) REFERENCES public.systems_users(id);


-- business.movimiento_inventario_detalle foreign keys

ALTER TABLE business.movimiento_inventario_detalle ADD CONSTRAINT detalle_movimiento_inventario_fk FOREIGN KEY (movimiento_inventario_id) REFERENCES business.movimiento_inventario(id);


-- business.pedido foreign keys

ALTER TABLE business.pedido ADD CONSTRAINT pedido_cliente_fk FOREIGN KEY (cliente_id) REFERENCES business.cliente(id);


-- business.pedido_detalle foreign keys

ALTER TABLE business.pedido_detalle ADD CONSTRAINT pedido_detalle_pedido_fk FOREIGN KEY (pedido_id) REFERENCES business.pedido(id);


-- business.venta foreign keys

ALTER TABLE business.venta ADD CONSTRAINT venta_cliente_fk FOREIGN KEY (cliente_id) REFERENCES business.cliente(id);


-- business.venta_detalle foreign keys

ALTER TABLE business.venta_detalle ADD CONSTRAINT detalle_venta_venta_fk FOREIGN KEY (venta_id) REFERENCES business.venta(id);


-- business.venta_subdetalle foreign keys

ALTER TABLE business.venta_subdetalle ADD CONSTRAINT venta_subdetalle_almacen_distribuidor_fk FOREIGN KEY (salida_id,salida_entrega_id,salida_entrega_detalle_id,almacen_id,distribuidor_id) REFERENCES business.almacen_distribuidor(salida_id,salida_entrega_id,salida_entrega_detalle_id,almacen_id,distribuidor_id);
ALTER TABLE business.venta_subdetalle ADD CONSTRAINT venta_subdetalle_venta_detalle_fk FOREIGN KEY (venta_id,producto_id,tipo_cantidad) REFERENCES business.venta_detalle(venta_id,producto_id,tipo_cantidad);


CREATE TABLE public.resumen (
                                id int4 NOT NULL,
                                sucursal_id int4 NOT NULL,
                                fecha timestamp NOT NULL,
                                CONSTRAINT resumen_pk PRIMARY KEY (id, sucursal_id)
);

CREATE TABLE public.resumen_almacen (
                                        resumen_id int4 NOT NULL,
                                        sucursal_id int4 NOT NULL,
                                        codpro int8 NOT NULL,
                                        cantidad numeric(10, 3) NOT NULL,
                                        CONSTRAINT resumen_almacen_pk PRIMARY KEY (resumen_id, sucursal_id, codpro)
);


-- public.resumen_almacen foreign keys

ALTER TABLE public.resumen_almacen ADD CONSTRAINT resumen_almacen_resumen_fk FOREIGN KEY (resumen_id,sucursal_id) REFERENCES public.resumen(id,sucursal_id);

--28/11/2024
ALTER TABLE business.historico_inventario DROP CONSTRAINT historico_inventario_unique;
ALTER TABLE business.historico_inventario ADD CONSTRAINT historico_inventario_pk PRIMARY KEY (branch_office_id,historico_id,producto_id);
ALTER SEQUENCE business.historico_id_seq RESTART 20;
CREATE OR REPLACE PROCEDURE business.procesamiento_resumen()
    LANGUAGE plpgsql
AS $procedure$
DECLARE v_cursor CURSOR FOR select id from public.branch_offices where status = true;
    declare SUCURSAL_X int4;
    DECLARE FECHA_ACTUAL timestamp;
    DECLARE RESUMEN_X int4;
BEGIN
    FECHA_ACTUAL = (select CURRENT_TIMESTAMP);
    OPEN v_cursor;
    loop
        FETCH v_cursor INTO SUCURSAL_X;
        EXIT WHEN NOT FOUND;
        --verificar si existe historico del dia
        RESUMEN_X = (select coalesce(max(id),0) from business.historico where fecha::date = CURRENT_DATE);
        if(RESUMEN_X > 0) then
            --Eliminacion de detalles del resumen
            delete from business.historico_inventario where historico_id = RESUMEN_X;
            update business.historico set fecha = FECHA_ACTUAL where id = RESUMEN_X;
        else
            RESUMEN_X = (select nextval('business.historico_id_seq'::regclass));
            insert into business.historico(id, fecha) values (RESUMEN_X, CURRENT_DATE);
        end if;
        --Adicion de detalles
        --Adicion de resumen almacen
        insert into business.historico_inventario(historico_id, branch_office_id, producto_id, cantidad)
        select RESUMEN_X, SUCURSAL_X, p.id, coalesce(sum(a.cantidad),0)
        from business.producto p
                 left join business.almacen a on p.id = a.producto_id and a.branch_office_id = SUCURSAL_X
        where p.status = true
        group by p.id;
    end loop;
    close v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;