--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2025-02-03 23:52:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 102079)
-- Name: business; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA business;


ALTER SCHEMA business OWNER TO postgres;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 285 (class 1255 OID 102080)
-- Name: delete_ingreso(bigint, bigint); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.delete_ingreso(IN p_ingreso_id bigint, IN p_user_id bigint)
    LANGUAGE plpgsql
    AS $$
declare
	DECLARE v_cursor CURSOR FOR select i.producto_id, i.cantidad_total_unitaria, i.almacen_id from business.ingreso_detalle i where i.ingreso_id = P_INGRESO_ID;
    DECLARE V_PRODUCTO_ID int4;
    DECLARE V_CAN_UNITARIA int4;
    DECLARE V_COD_ALM int8;
	DECLARE V_CANTIDAD_NEGATIVA int4;
	DECLARE V_TIPO_INGRESO_NORMAL character varying := '1';
	DECLARE ES_INVENTARIO_DIFERENTE boolean;
begin
	ES_INVENTARIO_DIFERENTE := (select count(*) > 0 from
		(select id.cantidad_total_unitaria,a.cantidad from business.ingreso_detalle id
		inner join business.almacen a on a.id = id.almacen_id
		where id.ingreso_id = p_ingreso_id and id.cantidad_total_unitaria > a.cantidad) sq);
	if ES_INVENTARIO_DIFERENTE then
		RAISE EXCEPTION 'No se puede eliminar, ya que el inventario a revertir es mayor a lo que se tiene en inventario';
	end if;
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
$$;


ALTER PROCEDURE business.delete_ingreso(IN p_ingreso_id bigint, IN p_user_id bigint) OWNER TO postgres;

--
-- TOC entry 286 (class 1255 OID 102081)
-- Name: delete_movimiento_egreso(bigint, bigint); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.delete_movimiento_egreso(IN p_movimiento_egreso_id bigint, IN p_user_id bigint)
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER PROCEDURE business.delete_movimiento_egreso(IN p_movimiento_egreso_id bigint, IN p_user_id bigint) OWNER TO postgres;

--
-- TOC entry 287 (class 1255 OID 102082)
-- Name: delete_movimiento_ingreso(bigint, bigint); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.delete_movimiento_ingreso(IN p_movimiento_ingreso_id bigint, IN p_user_id bigint)
    LANGUAGE plpgsql
    AS $$
declare
	DECLARE v_cursor CURSOR FOR SELECT almacen_id, cantidad FROM business.almacen_movimiento m where m.movimiento_inventario_id = p_movimiento_ingreso_id;
    DECLARE V_CAN_UNITARIA int4;
    DECLARE V_COD_ALM int8;
	DECLARE V_CANTIDAD_NEGATIVA int4;
	DECLARE V_TIPO_MOVIMIENTO_INGRESO character varying := '3';
	DECLARE ES_INVENTARIO_DIFERENTE boolean;
begin
	ES_INVENTARIO_DIFERENTE = (select count(*) > 0 from
		(SELECT almacen_id, a.cantidad, m.cantidad
		FROM business.almacen_movimiento m
		inner join business.almacen a on m.almacen_id = a.id
		where m.movimiento_inventario_id = 2 and m.cantidad > a.cantidad) sq);
	if ES_INVENTARIO_DIFERENTE then
		RAISE EXCEPTION 'No se puede eliminar, ya que el inventario a revertir es mayor a lo que se tiene en inventario';
	end if;
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
$$;


ALTER PROCEDURE business.delete_movimiento_ingreso(IN p_movimiento_ingreso_id bigint, IN p_user_id bigint) OWNER TO postgres;

--
-- TOC entry 272 (class 1255 OID 102083)
-- Name: delete_venta(bigint, bigint); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.delete_venta(IN p_venta_id bigint, IN p_salida_id bigint)
    LANGUAGE plpgsql
    AS $$
begin
	update business.almacen_distribuidor set cantidad = cantidad + subdetalle.cantidad_unitaria
from (select salida_id,salida_entrega_id,salida_entrega_detalle_id,almacen_id,distribuidor_id,cantidad_unitaria
      from business.venta_subdetalle where venta_id = p_venta_id) as subdetalle
where  almacen_distribuidor.salida_id = subdetalle.salida_id and
       almacen_distribuidor.salida_entrega_id = subdetalle.salida_entrega_id and
       almacen_distribuidor.salida_entrega_detalle_id = subdetalle.salida_entrega_detalle_id and
       almacen_distribuidor.almacen_id = subdetalle.almacen_id and
       almacen_distribuidor.distribuidor_id = subdetalle.distribuidor_id;

	update business.venta set status = false where id = p_venta_id;

	update business.salida set total_descuento= sq.vdescuento, total_impuesto = sq.vimpuesto, total_venta_contado = sq.vtotal
	from (select v.salida_id,coalesce(sum(v.descuento),0) vdescuento,coalesce(sum(v.impuesto),0) vimpuesto,coalesce(sum(v.total),0) vtotal from business.venta v
	where v.salida_id = p_salida_id and v.status = true group by v.salida_id) sq
	where sq.salida_id = business.salida.id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$$;


ALTER PROCEDURE business.delete_venta(IN p_venta_id bigint, IN p_salida_id bigint) OWNER TO postgres;

--
-- TOC entry 288 (class 1255 OID 102084)
-- Name: eliminar_salida(bigint, bigint, character varying); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.eliminar_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE v_cursor CURSOR FOR select sed.producto_id, sum(seda.cantidad) as devuelto, sum(ad.cantidad) as entregado  from business.salida_entrega_detalle_almacen seda
    inner join business.almacen_distribuidor ad on seda.salida_id = ad.salida_id and seda.salida_entrega_detalle_id = ad.salida_entrega_detalle_id and seda.salida_entrega_id = ad.salida_entrega_id and seda.almacen_id = ad.almacen_id
    inner join business.salida_entrega_detalle sed on seda.salida_id = sed.salida_id and seda.salida_entrega_detalle_id = sed.id and seda.salida_entrega_id = sed.salida_entrega_id
    inner join business.producto p on p.id = sed.producto_id
    where seda.salida_id = p_salida_id
    group by sed.producto_id;
    declare
        V_PRODUCTO_ID int8;
        V_DEVUELTO int4;
        V_ENTREGADO int4;
        V_CANTIDAD_PRODUCTOS_DIFERENTES int4 := 0;
begin

    OPEN v_cursor;
    loop
        FETCH v_cursor INTO V_PRODUCTO_ID,V_DEVUELTO,V_ENTREGADO;
        EXIT WHEN NOT FOUND;
        if V_ENTREGADO <> V_DEVUELTO then
            V_CANTIDAD_PRODUCTOS_DIFERENTES := V_CANTIDAD_PRODUCTOS_DIFERENTES + 1;
        end if;
    end loop;
    close v_cursor;

    if V_CANTIDAD_PRODUCTOS_DIFERENTES > 0 then
        RAISE EXCEPTION 'Existen cantidades diferentes en los productos entregados con los devueltos, para eliminar';
    else
        OPEN v_cursor;
        loop
            FETCH v_cursor INTO V_PRODUCTO_ID,V_DEVUELTO,V_ENTREGADO;
            EXIT WHEN NOT FOUND;
            insert into business.salida_resumen(salida_id, producto_id, cantidad_total_entregada, cantidad_total_devuelta, cantidad_total_guardada, cantidad_total_vendida)
            values(p_salida_id, V_PRODUCTO_ID, V_ENTREGADO, V_DEVUELTO, 0, 0);
        end loop;
        close v_cursor;
    end if;
    -- Actualizamos el almacen aumentando la cantidad de los productos devueltos
    update business.almacen set cantidad = cantidad + sq.cant from
        (select ad.almacen_id,ad.cantidad as cant from business.almacen_distribuidor ad
         where ad.salida_id = p_salida_id and ad.cantidad > 0) sq
    where id = sq.almacen_id;
    -- Actualizamos el almacen_distribuidor, dejandolo en cero, ya que se ha devuelto los productos
    update business.almacen_distribuidor set cantidad = 0 where salida_id = p_salida_id;
    -- Adicionamos el Log de movimiento del aumento de la cantidad al ALMACEN
    insert into business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
    select ad.almacen_id, '9', ad.cantidad, concat('Ingreso por eliminar salida #', p_salida_id::text), current_timestamp from business.almacen_distribuidor ad
    where ad.salida_id = p_salida_id and ad.cantidad > 0 ;
    -- Por ultimo actualizamos la salida, finalizando el estado
    update business.salida set obs_al_finalizar = p_obs, total_efectivo_entregado = 0,updated_by = p_usuario_id, updated_at = current_timestamp, estado_salida = 'f', total_general = total_venta_contado + salida.total_otro_tipo_pago
    where id = p_salida_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'ERROR procedimiento eliminar_salida %',sqlerrm;
END
$$;


ALTER PROCEDURE business.eliminar_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying) OWNER TO postgres;

--
-- TOC entry 289 (class 1255 OID 102085)
-- Name: finalizar_salida(bigint, bigint, character varying, numeric); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.finalizar_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying, IN p_efectivo_entregado numeric)
    LANGUAGE plpgsql
    AS $$
    DECLARE v_cursor CURSOR FOR select sed.producto_id, sum(seda.cantidad) as devuelto, sum(ad.cantidad) as entregado  from business.salida_entrega_detalle_almacen seda
        inner join business.almacen_distribuidor ad on seda.salida_id = ad.salida_id and seda.salida_entrega_detalle_id = ad.salida_entrega_detalle_id and seda.salida_entrega_id = ad.salida_entrega_id and seda.almacen_id = ad.almacen_id
        inner join business.salida_entrega_detalle sed on seda.salida_id = sed.salida_id and seda.salida_entrega_detalle_id = sed.id and seda.salida_entrega_id = sed.salida_entrega_id
        inner join business.producto p on p.id = sed.producto_id
        where seda.salida_id = p_salida_id
        group by sed.producto_id;
    declare
    V_PRODUCTO_ID int8;
    V_DEVUELTO int4;
    V_ENTREGADO int4;
    V_VENDIDO int4;
begin

    OPEN v_cursor;
    loop
        FETCH v_cursor INTO V_PRODUCTO_ID,V_DEVUELTO,V_ENTREGADO;
        EXIT WHEN NOT FOUND;
        V_VENDIDO := V_ENTREGADO - V_DEVUELTO;
        insert into business.salida_resumen(salida_id, producto_id, cantidad_total_entregada, cantidad_total_devuelta, cantidad_total_guardada, cantidad_total_vendida)
        values(p_salida_id, V_PRODUCTO_ID, V_ENTREGADO, V_DEVUELTO, 0, V_VENDIDO);
    end loop;
    close v_cursor;
	-- Actualizamos el almacen aumentando la cantidad de los productos devueltos
    update business.almacen set cantidad = cantidad + sq.cant from
        (select ad.almacen_id,ad.cantidad as cant from business.almacen_distribuidor ad
         where ad.salida_id = p_salida_id and ad.cantidad > 0) sq
    where id = sq.almacen_id;
	-- Actualizamos el almacen_distribuidor, dejandolo en cero, ya que se ha devuelto los productos
	update business.almacen_distribuidor set cantidad = 0 where salida_id = p_salida_id;
	-- Adicionamos el Log de movimiento del aumento de la cantidad al ALMACEN
    insert into business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
    select ad.almacen_id, '9', ad.cantidad, concat('Ingreso por finalizar salida #', p_salida_id::text), current_timestamp from business.almacen_distribuidor ad
    where ad.salida_id = p_salida_id and ad.cantidad > 0 ;
	-- Por ultimo actualizamos la salida, finalizando el estado
    update business.salida set obs_al_finalizar = p_obs, total_efectivo_entregado = p_efectivo_entregado,updated_by = p_usuario_id, updated_at = current_timestamp, estado_salida = 'f', total_general = total_venta_contado + salida.total_otro_tipo_pago
    where id = p_salida_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'ERROR procedimiento finalizar_salida %',sqlerrm;
END
$$;


ALTER PROCEDURE business.finalizar_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying, IN p_efectivo_entregado numeric) OWNER TO postgres;

--
-- TOC entry 290 (class 1255 OID 102086)
-- Name: procesamiento_resumen(); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.procesamiento_resumen()
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER PROCEDURE business.procesamiento_resumen() OWNER TO postgres;

--
-- TOC entry 291 (class 1255 OID 102087)
-- Name: save_aumentar_detalle_salida(bigint, bigint, character varying, integer[], integer[], integer[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_aumentar_detalle_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[])
    LANGUAGE plpgsql
    AS $$
declare
    v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_cantidad_detalle int4;
	v_cantidad_detalle_aux int4;
    v_cod_alm int8;
	array_almacen int8[];
	array_cantidad int4[];
	array_tam int4;
	array_index integer;
	cantidad_existente_almacen int4;
	v_distribuidor_id int8;
	v_sucursal_id int4;
	v_salida_entrega_detalle_id int2:=0;
	v_entrega_id int2;
	V_TIPO_SALIDA_MOVIMIENTO character varying := '8';
begin
	
	-- Obtener datos de la salida
	select branch_office_id, distribuidor_id into v_sucursal_id, v_distribuidor_id from business.salida where id = p_salida_id;
	-- Obtener siguiente codigo de Entrega
	select coalesce(max(id),0)+1 into v_entrega_id from business.salida_entrega where salida_id = p_salida_id; 
	-- ADICION DE SALIDA ENTREGA INICIAL
	INSERT INTO business.salida_entrega (id, created_by, created_at, obs, status, salida_id) VALUES(v_entrega_id, p_usuario_id, current_timestamp, p_obs, true, p_salida_id);
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
	    RAISE NOTICE 'producto: %',productos[v_index];
        select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
		-- Calcular el total de productos
		v_cantidad_detalle := (c_cajas[v_index] * v_unixcaja) + c_unidades[v_index];
	--INSERTAR SALIDA ENTREGA DETALLE
	v_salida_entrega_detalle_id := v_salida_entrega_detalle_id + 1;
      INSERT INTO business.salida_entrega_detalle (salida_id, salida_entrega_id, id, producto_id, cantidad_caja, cantidad_unitaria, cantidad_total, status) 
     VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, productos[v_index], c_cajas[v_index], c_unidades[v_index], v_cantidad_detalle, true);
	
	
		cantidad_existente_almacen := (select coalesce(sum(cantidad),0) from business.almacen where producto_id = productos[v_index] and branch_office_id = v_sucursal_id and cantidad > 0);
		--Validar si existe la cantidad suficiente en almacen
		RAISE NOTICE 'Cantidad en almacen: % y la cantidad de detalle: %',cantidad_existente_almacen,v_cantidad_detalle;
		if cantidad_existente_almacen >= v_cantidad_detalle then
			
			v_cantidad_detalle_aux := v_cantidad_detalle;
			--Consulta para buscar todos los almacenes con cantidad del producto
			SELECT array_agg(id),array_agg(cantidad) into array_almacen,array_cantidad FROM business.almacen where producto_id = productos[v_index] and branch_office_id = v_sucursal_id and cantidad > 0;
			array_tam := array_length(array_almacen, 1);
			array_index := 1;
			while (array_index <= array_tam and v_cantidad_detalle_aux > 0) loop
				if array_cantidad[array_index] >= v_cantidad_detalle_aux then --Si cantidad en almacen es mayor o igual se descuenta todo el detalle restante y termina el ciclo
					--Actualizamos almacen con la cantidad a descontar del detalle restante
					update business.almacen set cantidad = cantidad - v_cantidad_detalle_aux where id = array_almacen[array_index];
					--creacion de log de movimiento del producto
					INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_SALIDA_MOVIMIENTO, (-1 * v_cantidad_detalle_aux), 'Movimiento de inventario: Aumento de Salida', current_timestamp);
					
					INSERT INTO business.salida_entrega_detalle_almacen (salida_id, salida_entrega_detalle_id, salida_entrega_id, almacen_id, cantidad) 
					VALUES(p_salida_id, v_salida_entrega_detalle_id, v_entrega_id, array_almacen[array_index], v_cantidad_detalle_aux);
				
					if exists (select * from business.almacen_distribuidor where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id) then
						update business.almacen_distribuidor set cantidad = cantidad + v_cantidad_detalle_aux 
						where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id;
					else
						INSERT INTO business.almacen_distribuidor (salida_id, salida_entrega_id, salida_entrega_detalle_id, almacen_id, cantidad, distribuidor_id) 
						VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, array_almacen[array_index], v_cantidad_detalle_aux, v_distribuidor_id);
					end if;
				
					RAISE NOTICE 'el almacen es mayor al detalle % mayor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(array_cantidad[array_index]- v_cantidad_detalle_aux);
					v_cantidad_detalle_aux := 0;
				else
					--Actualizamos almacen a cero ya que hay mas en la cantidad de detalle
					update business.almacen set cantidad = 0 where id = array_almacen[array_index];
					INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_SALIDA_MOVIMIENTO, (-1 * array_cantidad[array_index]), 'Movimiento de inventario: Aumento de Salida', current_timestamp);
				
					INSERT INTO business.salida_entrega_detalle_almacen (salida_id, salida_entrega_detalle_id, salida_entrega_id, almacen_id, cantidad) 
					VALUES(p_salida_id, v_salida_entrega_detalle_id, v_entrega_id, array_almacen[array_index], array_cantidad[array_index]);
				
					if exists (select * from business.almacen_distribuidor where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id) then
						update business.almacen_distribuidor set cantidad = cantidad + array_cantidad[array_index] 
						where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id;
					else
						INSERT INTO business.almacen_distribuidor (salida_id, salida_entrega_id, salida_entrega_detalle_id, almacen_id, cantidad, distribuidor_id) 
						VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, array_almacen[array_index], array_cantidad[array_index], v_distribuidor_id);
					end if;
				
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
$$;


ALTER PROCEDURE business.save_aumentar_detalle_salida(IN p_salida_id bigint, IN p_usuario_id bigint, IN p_obs character varying, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[]) OWNER TO postgres;

--
-- TOC entry 292 (class 1255 OID 102089)
-- Name: save_detalles_ingreso(bigint, integer, integer[], character varying[], character varying[], character varying[], integer[], integer[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_detalles_ingreso(IN p_ingreso_id bigint, IN p_sucursal_id integer, IN productos integer[], IN lotes character varying[], IN elaboraciones character varying[], IN vencimientos character varying[], IN c_unidades integer[], IN c_cajas integer[])
    LANGUAGE plpgsql
    AS $$
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
			where producto_id = productos[v_index] and branch_office_id = p_sucursal_id and (fecha_vencimiento = to_date(vencimientos[v_index], 'DD/MM/YY') or (fecha_vencimiento is null and vencimientos[v_index] is null)) 
		and (lote = lotes[v_index] or (lote is null and lotes[v_index] is null));
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
$$;


ALTER PROCEDURE business.save_detalles_ingreso(IN p_ingreso_id bigint, IN p_sucursal_id integer, IN productos integer[], IN lotes character varying[], IN elaboraciones character varying[], IN vencimientos character varying[], IN c_unidades integer[], IN c_cajas integer[]) OWNER TO postgres;

--
-- TOC entry 293 (class 1255 OID 102090)
-- Name: save_detalles_pedido(bigint, integer[], integer[], numeric[], character varying[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_detalles_pedido(IN p_pedido_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN tipos character varying[])
    LANGUAGE plpgsql
    AS $$
declare
    v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_can_unitaria int4;
    v_subtotal numeric;
	v_total numeric :=0;
begin
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
        select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
		-- Calcular el total de productos
        if tipos[v_index] = '1' then
            v_can_unitaria := cantidades[v_index];
            else
            v_can_unitaria := cantidades[v_index] * v_unixcaja;
        end if;
        -- CALCULAR EL SUBTOTAL
        v_subtotal := cantidades[v_index] * precios[v_index];
		v_total := v_total + v_subtotal;
        -- REGISTRO EN DETALLE PEDIDO
        insert into business.pedido_detalle(pedido_id, producto_id, tipo_cantidad, cantidad, precio, subtotal)
        values (p_pedido_id, productos[v_index], tipos[v_index], cantidades[v_index], precios[v_index], v_subtotal);
        v_index := v_index + 1;
      end LOOP;
		--Actualizar pedido
		update business.pedido set total = v_total - descuento where id = p_pedido_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$$;


ALTER PROCEDURE business.save_detalles_pedido(IN p_pedido_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN tipos character varying[]) OWNER TO postgres;

--
-- TOC entry 273 (class 1255 OID 102091)
-- Name: save_detalles_venta(bigint, integer[], integer[], numeric[], numeric[], character varying[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_detalles_venta(IN p_venta_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN descuentos numeric[], IN tipos character varying[])
    LANGUAGE plpgsql
    AS $$
declare
    v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_subtotal numeric;
    v_total numeric;
    v_salida_id int8;
    v_cantidad_detalle int4;
	v_cantidad_detalle_aux int4;
    v_cod_alm int8;
	array_almacen int8[];
	array_cantidad int4[];
    array_salida int8[];
    array_salida_entrega int2[];
    array_salida_entrega_detalle int2[];
	array_tam int4;
	array_index integer;
	cantidad_existente_almacen int4;
	v_distribuidor_id int8;
begin
    select salida_id,usuario_id into v_salida_id,v_distribuidor_id from business.venta where id = p_venta_id;
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
        select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
		-- Calcular el total de productos
        if tipos[v_index] = '1' then
            v_cantidad_detalle := cantidades[v_index];
            else
            v_cantidad_detalle := cantidades[v_index] * v_unixcaja;
        end if;
		RAISE NOTICE 'distribuidor ID: %  y producto: %',v_distribuidor_id,productos[v_index];
        cantidad_existente_almacen := (select coalesce(sum(ad.cantidad),0) from business.almacen_distribuidor ad inner join business.almacen a on ad.almacen_id = a.id and a.producto_id = productos[v_index] where ad.distribuidor_id = v_distribuidor_id and ad.cantidad > 0);
		--Validar si existe la cantidad suficiente en almacen
		RAISE NOTICE 'Cantidad en almacen: % y la cantidad de detalle: %',cantidad_existente_almacen,v_cantidad_detalle;
		if cantidad_existente_almacen >= v_cantidad_detalle then

            -- CALCULAR EL SUBTOTAL
            v_subtotal := cantidades[v_index] * precios[v_index];
            v_total := v_subtotal - descuentos[v_index];
            -- REGISTRO EN DETALLE PEDIDO
            insert into business.venta_detalle(venta_id, producto_id, cantidad, precio, subtotal, descuento, total, cantidad_unitaria_total, tipo_cantidad) values
            (p_venta_id, productos[v_index],cantidades[v_index],precios[v_index],v_subtotal, descuentos[v_index],v_total, v_cantidad_detalle,tipos[v_index]);

			v_cantidad_detalle_aux := v_cantidad_detalle;
			--Consulta para buscar todos los almacenes con cantidad del producto
			SELECT array_agg(ad.almacen_id),array_agg(ad.cantidad),array_agg(ad.salida_id),array_agg(ad.salida_entrega_id),array_agg(ad.salida_entrega_detalle_id) into array_almacen,array_cantidad,array_salida,array_salida_entrega, array_salida_entrega_detalle FROM business.almacen_distribuidor ad inner join business.almacen a on ad.almacen_id = a.id and a.producto_id = productos[v_index] where ad.distribuidor_id = v_distribuidor_id and ad.cantidad > 0;
			array_tam := array_length(array_almacen, 1);
			array_index := 1;
			while (array_index <= array_tam and v_cantidad_detalle_aux > 0) loop
				if array_cantidad[array_index] >= v_cantidad_detalle_aux then --Si cantidad en almacen es mayor o igual se descuenta todo el detalle restante y termina el ciclo
					--Actualizamos almacen con la cantidad a descontar del detalle restante
					update business.almacen_distribuidor set cantidad = cantidad - v_cantidad_detalle_aux where almacen_id = array_almacen[array_index] and salida_id = array_salida[array_index] and salida_entrega_id = array_salida_entrega[array_index] and salida_entrega_detalle_id = array_salida_entrega_detalle[array_index];
					--creacion de venta subdetalle
					INSERT INTO business.venta_subdetalle(venta_id, producto_id, tipo_cantidad, id, almacen_id, cantidad_unitaria, salida_id, salida_entrega_id, salida_entrega_detalle_id, distribuidor_id)
					VALUES(p_venta_id, productos[v_index], tipos[v_index], array_index, array_almacen[array_index],v_cantidad_detalle_aux,array_salida[array_index],array_salida_entrega[array_index],array_salida_entrega_detalle[array_index], v_distribuidor_id);
					RAISE NOTICE 'el almacen es mayor al detalle % mayor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(array_cantidad[array_index]- v_cantidad_detalle_aux);
					v_cantidad_detalle_aux := 0;
				else
					--Actualizamos almacen a cero ya que hay mas en la cantidad de detalle
					update business.almacen_distribuidor set cantidad = 0 where almacen_id = array_almacen[array_index] and salida_id = array_salida[array_index] and salida_entrega_id = array_salida_entrega[array_index] and salida_entrega_detalle_id = array_salida_entrega_detalle[array_index];
					--creacion de venta subdetalle
					INSERT INTO business.venta_subdetalle(venta_id, producto_id, tipo_cantidad, id, almacen_id, cantidad_unitaria, salida_id, salida_entrega_id, salida_entrega_detalle_id, distribuidor_id)
					VALUES(p_venta_id, productos[v_index], tipos[v_index], array_index, array_almacen[array_index], array_cantidad[array_index],array_salida[array_index],array_salida_entrega[array_index],array_salida_entrega_detalle[array_index], v_distribuidor_id);

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
		--Una vez terminado actualizamos el resumen de ventas en salida
		update business.salida set total_descuento= sq.vdescuento, total_impuesto = sq.vimpuesto, total_venta_contado = sq.vtotal
		from (select v.salida_id,coalesce(sum(v.descuento),0) vdescuento,coalesce(sum(v.impuesto),0) vimpuesto,coalesce(sum(v.total),0) vtotal from business.venta v 
		where v.salida_id = v_salida_id and v.status = true group by v.salida_id) sq
		where sq.salida_id = business.salida.id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$$;


ALTER PROCEDURE business.save_detalles_venta(IN p_venta_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN descuentos numeric[], IN tipos character varying[]) OWNER TO postgres;

--
-- TOC entry 294 (class 1255 OID 102093)
-- Name: save_movimiento_egreso(bigint, integer, integer[], integer[], integer[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_movimiento_egreso(IN p_movimiento_id bigint, IN p_sucursal_id integer, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[])
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER PROCEDURE business.save_movimiento_egreso(IN p_movimiento_id bigint, IN p_sucursal_id integer, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[]) OWNER TO postgres;

--
-- TOC entry 295 (class 1255 OID 102094)
-- Name: save_movimiento_ingreso(bigint, integer, integer[], character varying[], character varying[], character varying[], integer[], integer[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_movimiento_ingreso(IN p_movimiento_id bigint, IN p_sucursal_id integer, IN productos integer[], IN lotes character varying[], IN elaboraciones character varying[], IN vencimientos character varying[], IN c_unidades integer[], IN c_cajas integer[])
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER PROCEDURE business.save_movimiento_ingreso(IN p_movimiento_id bigint, IN p_sucursal_id integer, IN productos integer[], IN lotes character varying[], IN elaboraciones character varying[], IN vencimientos character varying[], IN c_unidades integer[], IN c_cajas integer[]) OWNER TO postgres;

--
-- TOC entry 296 (class 1255 OID 102095)
-- Name: save_salida(bigint, integer[], integer[], integer[]); Type: PROCEDURE; Schema: business; Owner: postgres
--

CREATE PROCEDURE business.save_salida(IN p_salida_id bigint, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[])
    LANGUAGE plpgsql
    AS $$
declare
    v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_cantidad_detalle int4;
	v_cantidad_detalle_aux int4;
    v_cod_alm int8;
	array_almacen int8[];
	array_cantidad int4[];
	array_tam int4;
	array_index integer;
	cantidad_existente_almacen int4;
	v_distribuidor_id int8;
	v_sucursal_id int4;
	v_salida_entrega_detalle_id int2:=0;
	v_entrega_id int2 := 1;
	V_TIPO_SALIDA_MOVIMIENTO character varying := '8';
	v_usuario_id int8;
begin
	
	-- Obtener datos de la salida
	select distribuidor_id, branch_office_id, created_by into v_distribuidor_id, v_sucursal_id, v_usuario_id from business.salida where id = p_salida_id;
	-- ADICION DE SALIDA ENTREGA INICIAL
	INSERT INTO business.salida_entrega (id, created_by, created_at, obs, status, salida_id) VALUES(v_entrega_id, v_usuario_id, current_timestamp, 'Entrega inicial de la salida', true, p_salida_id);
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
	    RAISE NOTICE 'producto: %',productos[v_index];
        select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
		-- Calcular el total de productos
		v_cantidad_detalle := (c_cajas[v_index] * v_unixcaja) + c_unidades[v_index];
	--INSERTAR SALIDA ENTREGA DETALLE
	v_salida_entrega_detalle_id := v_salida_entrega_detalle_id + 1;
      INSERT INTO business.salida_entrega_detalle (salida_id, salida_entrega_id, id, producto_id, cantidad_caja, cantidad_unitaria, cantidad_total, status) 
     VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, productos[v_index], c_cajas[v_index], c_unidades[v_index], v_cantidad_detalle, true);
	
	
		cantidad_existente_almacen := (select coalesce(sum(cantidad),0) from business.almacen where producto_id = productos[v_index] and branch_office_id = v_sucursal_id and cantidad > 0);
		--Validar si existe la cantidad suficiente en almacen
		RAISE NOTICE 'Cantidad en almacen: % y la cantidad de detalle: %',cantidad_existente_almacen,v_cantidad_detalle;
		if cantidad_existente_almacen >= v_cantidad_detalle then
			
			v_cantidad_detalle_aux := v_cantidad_detalle;
			--Consulta para buscar todos los almacenes con cantidad del producto
			SELECT array_agg(id),array_agg(cantidad) into array_almacen,array_cantidad FROM business.almacen where producto_id = productos[v_index] and branch_office_id = v_sucursal_id and cantidad > 0;
			array_tam := array_length(array_almacen, 1);
			array_index := 1;
			while (array_index <= array_tam and v_cantidad_detalle_aux > 0) loop
				if array_cantidad[array_index] >= v_cantidad_detalle_aux then --Si cantidad en almacen es mayor o igual se descuenta todo el detalle restante y termina el ciclo
					--Actualizamos almacen con la cantidad a descontar del detalle restante
					update business.almacen set cantidad = cantidad - v_cantidad_detalle_aux where id = array_almacen[array_index];
					--creacion de log de movimiento del producto
					INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_SALIDA_MOVIMIENTO, (-1 * v_cantidad_detalle_aux), 'Movimiento de inventario: Salida', current_timestamp);
					
					INSERT INTO business.salida_entrega_detalle_almacen (salida_id, salida_entrega_detalle_id, salida_entrega_id, almacen_id, cantidad) 
					VALUES(p_salida_id, v_salida_entrega_detalle_id, v_entrega_id, array_almacen[array_index], v_cantidad_detalle_aux);
				
					if exists (select * from business.almacen_distribuidor where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id) then
						update business.almacen_distribuidor set cantidad = cantidad + v_cantidad_detalle_aux 
						where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id;
					else
						INSERT INTO business.almacen_distribuidor (salida_id, salida_entrega_id, salida_entrega_detalle_id, almacen_id, cantidad, distribuidor_id) 
						VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, array_almacen[array_index], v_cantidad_detalle_aux, v_distribuidor_id);
					end if;
				
					RAISE NOTICE 'el almacen es mayor al detalle % mayor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(array_cantidad[array_index]- v_cantidad_detalle_aux);
					v_cantidad_detalle_aux := 0;
				else
					--Actualizamos almacen a cero ya que hay mas en la cantidad de detalle
					update business.almacen set cantidad = 0 where id = array_almacen[array_index];
					INSERT INTO business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha) VALUES(array_almacen[array_index], V_TIPO_SALIDA_MOVIMIENTO, (-1 * array_cantidad[array_index]), 'Movimiento de inventario: Salida', current_timestamp);
				
					INSERT INTO business.salida_entrega_detalle_almacen (salida_id, salida_entrega_detalle_id, salida_entrega_id, almacen_id, cantidad) 
					VALUES(p_salida_id, v_salida_entrega_detalle_id, v_entrega_id, array_almacen[array_index], array_cantidad[array_index]);
				
					if exists (select * from business.almacen_distribuidor where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id) then
						update business.almacen_distribuidor set cantidad = cantidad + array_cantidad[array_index] 
						where salida_id = p_salida_id and salida_entrega_id = v_entrega_id and salida_entrega_detalle_id = v_salida_entrega_detalle_id and almacen_id = array_almacen[array_index] and distribuidor_id = v_distribuidor_id;
					else
						INSERT INTO business.almacen_distribuidor (salida_id, salida_entrega_id, salida_entrega_detalle_id, almacen_id, cantidad, distribuidor_id) 
						VALUES(p_salida_id, v_entrega_id, v_salida_entrega_detalle_id, array_almacen[array_index], array_cantidad[array_index], v_distribuidor_id);
					end if;
				
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
$$;


ALTER PROCEDURE business.save_salida(IN p_salida_id bigint, IN productos integer[], IN c_unidades integer[], IN c_cajas integer[]) OWNER TO postgres;

--
-- TOC entry 297 (class 1255 OID 102097)
-- Name: save_detalles_venta(bigint, integer[], integer[], numeric[], numeric[], character varying[]); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.save_detalles_venta(IN p_venta_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN descuentos numeric[], IN tipos character varying[])
    LANGUAGE plpgsql
    AS $$
declare
    v_length integer := array_length(productos,1);
    v_index integer := 1;
    v_unixcaja int4;
    v_subtotal numeric;
    v_total numeric;
    v_salida_id int8;
    v_cantidad_detalle int4;
	v_cantidad_detalle_aux int4;
    v_cod_alm int8;
	array_almacen int8[];
	array_cantidad int4[];
    array_salida int8[];
    array_salida_entrega int2[];
    array_salida_entrega_detalle int2[];
	array_tam int4;
	array_index integer;
	cantidad_existente_almacen int4;
	v_distribuidor_id int8;
begin
    RAISE NOTICE 'tstrttt';
    select salida_id,usuario_id into v_salida_id,v_distribuidor_id from business.venta where id = p_venta_id;
    while v_index <= v_length loop
        -- OBTENER PRODUCTO --
        select unidad_por_caja into v_unixcaja from business.producto where id = productos[v_index];
		-- Calcular el total de productos
        if tipos[v_index] = '1' then
            v_cantidad_detalle := cantidades[v_index];
            else
            v_cantidad_detalle := cantidades[v_index] * v_unixcaja;
        end if;




        cantidad_existente_almacen := (select coalesce(sum(ad.cantidad),0) from business.almacen_distribuidor ad inner join business.almacen a on ad.almacen_id = a.id and a.producto_id = productos[v_index] where ad.distribuidor_id = v_distribuidor_id and ad.cantidad > 0);
		--Validar si existe la cantidad suficiente en almacen
		RAISE NOTICE 'Cantidad en almacen: % y la cantidad de detalle: %',cantidad_existente_almacen,v_cantidad_detalle;
		if cantidad_existente_almacen >= v_cantidad_detalle then

            -- CALCULAR EL SUBTOTAL
            v_subtotal := cantidades[v_index] * precios[v_index];
            v_total := v_subtotal - descuentos[v_index];
            -- REGISTRO EN DETALLE PEDIDO
            insert into business.venta_detalle(venta_id, producto_id, cantidad, precio, subtotal, descuento, total, cantidad_unitaria_total, tipo_cantidad) values
            (p_venta_id, productos[v_index],cantidades[v_index],precios[v_index],v_subtotal, descuentos[v_index],v_total, v_cantidad_detalle,tipos[v_index]);

			v_cantidad_detalle_aux := v_cantidad_detalle;
			--Consulta para buscar todos los almacenes con cantidad del producto
			SELECT array_agg(ad.almacen_id),array_agg(ad.cantidad),array_agg(ad.salida_id),array_agg(ad.salida_entrega_id),array_agg(ad.salida_entrega_detalle_id) into array_almacen,array_cantidad,array_salida,array_salida_entrega, array_salida_entrega_detalle FROM business.almacen_distribuidor ad inner join business.almacen a on ad.almacen_id = a.id and a.producto_id = productos[v_index] where ad.distribuidor_id = v_distribuidor_id and ad.cantidad > 0;
			array_tam := array_length(array_almacen, 1);
			array_index := 1;
			while (array_index <= array_tam and v_cantidad_detalle_aux > 0) loop
				if array_cantidad[array_index] >= v_cantidad_detalle_aux then --Si cantidad en almacen es mayor o igual se descuenta todo el detalle restante y termina el ciclo
					--Actualizamos almacen con la cantidad a descontar del detalle restante
					update business.almacen_distribuidor set cantidad = cantidad - v_cantidad_detalle_aux where almacen_id = array_almacen[array_index] and salida_id = array_salida[array_index] and salida_entrega_id = array_salida_entrega[array_index] and salida_entrega_detalle_id = array_salida_entrega_detalle[array_index];
					--creacion de venta subdetalle
					INSERT INTO business.venta_subdetalle(venta_id, producto_id, tipo_cantidad, id, almacen_id, cantidad_unitaria, salida_id, salida_entrega_id, salida_entrega_detalle_id, distribuidor_id)
					VALUES(p_venta_id, productos[v_index], tipos[v_index], array_index, v_cantidad_detalle_aux,array_salida[array_index],array_salida_entrega[array_index],array_salida_entrega_detalle[array_index], v_distribuidor_id);
					RAISE NOTICE 'el almacen es mayor al detalle % mayor que %',array_cantidad[array_index], v_cantidad_detalle_aux;
					RAISE NOTICE 'Descontando almacen quedaria: %',(array_cantidad[array_index]- v_cantidad_detalle_aux);
					v_cantidad_detalle_aux := 0;
				else
					--Actualizamos almacen a cero ya que hay mas en la cantidad de detalle
					update business.almacen_distribuidor set cantidad = 0 where almacen_id = array_almacen[array_index] and salida_id = array_salida[array_index] and salida_entrega_id = array_salida_entrega[array_index] and salida_entrega_detalle_id = array_salida_entrega_detalle[array_index];
					--creacion de venta subdetalle
					INSERT INTO business.venta_subdetalle(venta_id, producto_id, tipo_cantidad, id, almacen_id, cantidad_unitaria, salida_id, salida_entrega_id, salida_entrega_detalle_id, distribuidor_id)
					VALUES(p_venta_id, productos[v_index], tipos[v_index], array_index, array_cantidad[array_index],array_salida[array_index],array_salida_entrega[array_index],array_salida_entrega_detalle[array_index], v_distribuidor_id);

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
        RAISE EXCEPTION '%',sqlerrm;
END
$$;


ALTER PROCEDURE public.save_detalles_venta(IN p_venta_id bigint, IN productos integer[], IN cantidades integer[], IN precios numeric[], IN descuentos numeric[], IN tipos character varying[]) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 102098)
-- Name: area_producto; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.area_producto (
    id smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion character varying(500),
    icono character varying(25) NOT NULL,
    status boolean DEFAULT true NOT NULL
);


ALTER TABLE business.area_producto OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 102104)
-- Name: area_producto_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.area_producto_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.area_producto_id_seq OWNER TO postgres;

--
-- TOC entry 5128 (class 0 OID 0)
-- Dependencies: 219
-- Name: area_producto_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.area_producto_id_seq OWNED BY business.area_producto.id;


--
-- TOC entry 220 (class 1259 OID 102105)
-- Name: barrio; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.barrio (
    id smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    zona_id smallint NOT NULL
);


ALTER TABLE business.barrio OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 102109)
-- Name: barrio_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.barrio_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.barrio_id_seq OWNER TO postgres;

--
-- TOC entry 5129 (class 0 OID 0)
-- Dependencies: 221
-- Name: barrio_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.barrio_id_seq OWNED BY business.barrio.id;


--
-- TOC entry 222 (class 1259 OID 102110)
-- Name: caracteristica; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.caracteristica (
    id smallint NOT NULL,
    nombre character varying(150) NOT NULL,
    tipo character varying(1) NOT NULL,
    status boolean DEFAULT true NOT NULL
);


ALTER TABLE business.caracteristica OWNER TO postgres;

--
-- TOC entry 5130 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN caracteristica.tipo; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON COLUMN business.caracteristica.tipo IS '1= presentacion en unidades, 2 = presentacion en cajas, 3= tipo cliente';


--
-- TOC entry 223 (class 1259 OID 102114)
-- Name: caracteristica_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.caracteristica_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.caracteristica_id_seq OWNER TO postgres;

--
-- TOC entry 5131 (class 0 OID 0)
-- Dependencies: 223
-- Name: caracteristica_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.caracteristica_id_seq OWNED BY business.caracteristica.id;


--
-- TOC entry 224 (class 1259 OID 102115)
-- Name: categoria_producto; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.categoria_producto (
    id smallint NOT NULL,
    nombre character varying(150) NOT NULL,
    descripcion character varying(500) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    tipo_producto_id smallint NOT NULL
);


ALTER TABLE business.categoria_producto OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 102121)
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.categoria_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.categoria_id_seq OWNER TO postgres;

--
-- TOC entry 5132 (class 0 OID 0)
-- Dependencies: 225
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.categoria_id_seq OWNED BY business.categoria_producto.id;


--
-- TOC entry 226 (class 1259 OID 102122)
-- Name: ciudad; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.ciudad (
    id smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    status boolean DEFAULT true NOT NULL
);


ALTER TABLE business.ciudad OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 102126)
-- Name: ciudad_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.ciudad_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.ciudad_id_seq OWNER TO postgres;

--
-- TOC entry 5133 (class 0 OID 0)
-- Dependencies: 227
-- Name: ciudad_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.ciudad_id_seq OWNED BY business.ciudad.id;


--
-- TOC entry 228 (class 1259 OID 102127)
-- Name: cliente; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.cliente (
    id bigint NOT NULL,
    alias character varying(50) NOT NULL,
    direccion character varying(200) NOT NULL,
    email character varying(100) NOT NULL,
    nombre_negocio character varying(150) NOT NULL,
    descripcion_negocio character varying(500) NOT NULL,
    tipo_cliente smallint NOT NULL,
    barrio_id smallint NOT NULL,
    status boolean DEFAULT true NOT NULL
);


ALTER TABLE business.cliente OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 102133)
-- Name: cliente_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.cliente_seq OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 102134)
-- Name: historico; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.historico (
    id integer NOT NULL,
    fecha date NOT NULL
);


ALTER TABLE business.historico OWNER TO postgres;

--
-- TOC entry 5134 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN historico.fecha; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON COLUMN business.historico.fecha IS 'Fecha de los datos historicos al que pertenece';


--
-- TOC entry 231 (class 1259 OID 102137)
-- Name: historico_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.historico_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.historico_id_seq OWNER TO postgres;

--
-- TOC entry 5135 (class 0 OID 0)
-- Dependencies: 231
-- Name: historico_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.historico_id_seq OWNED BY business.historico.id;


--
-- TOC entry 232 (class 1259 OID 102138)
-- Name: producto; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.producto (
    id integer NOT NULL,
    nombre_generico character varying(150) NOT NULL,
    nombre_comercial character varying(150) NOT NULL,
    descripcion character varying(1500),
    caracteristica character varying(250) NOT NULL,
    categoria_id smallint NOT NULL,
    foto character varying(50) NOT NULL,
    unidad_por_caja smallint,
    stock_medio smallint NOT NULL,
    stock_alto smallint NOT NULL,
    pc_unit numeric(10,2) NOT NULL,
    pc_caja numeric(10,2),
    pv_unit numeric(10,2) NOT NULL,
    pv_caja numeric(10,2) NOT NULL,
    pv_unit_descuento numeric(10,2) NOT NULL,
    pv_caja_descuento numeric(10,2) NOT NULL,
    pv_unit_promo numeric(10,2) NOT NULL,
    pv_caja_promo numeric(10,2) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    presentacion_unit_id smallint NOT NULL,
    presentacion_caja_id smallint NOT NULL
);


ALTER TABLE business.producto OWNER TO postgres;

--
-- TOC entry 5136 (class 0 OID 0)
-- Dependencies: 232
-- Name: TABLE producto; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON TABLE business.producto IS 'tabla que guarda el catalogo de productos de la empresa';


--
-- TOC entry 5137 (class 0 OID 0)
-- Dependencies: 232
-- Name: COLUMN producto.id; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON COLUMN business.producto.id IS 'id del producto, generado por el sistema';


--
-- TOC entry 5138 (class 0 OID 0)
-- Dependencies: 232
-- Name: COLUMN producto.foto; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON COLUMN business.producto.foto IS 'foto principal del producto';


--
-- TOC entry 5139 (class 0 OID 0)
-- Dependencies: 232
-- Name: COLUMN producto.status; Type: COMMENT; Schema: business; Owner: postgres
--

COMMENT ON COLUMN business.producto.status IS 'true=activo, false=inactivo';


--
-- TOC entry 233 (class 1259 OID 102144)
-- Name: producto_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.producto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.producto_id_seq OWNER TO postgres;

--
-- TOC entry 5140 (class 0 OID 0)
-- Dependencies: 233
-- Name: producto_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.producto_id_seq OWNED BY business.producto.id;


--
-- TOC entry 234 (class 1259 OID 102145)
-- Name: tipo_producto; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.tipo_producto (
    id smallint NOT NULL,
    nombre character varying(150) NOT NULL,
    descripcion character varying(500) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    area_producto_id smallint NOT NULL
);


ALTER TABLE business.tipo_producto OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 102151)
-- Name: tipo_producto_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.tipo_producto_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.tipo_producto_id_seq OWNER TO postgres;

--
-- TOC entry 5141 (class 0 OID 0)
-- Dependencies: 235
-- Name: tipo_producto_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.tipo_producto_id_seq OWNED BY business.tipo_producto.id;


--
-- TOC entry 236 (class 1259 OID 102152)
-- Name: venta_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.venta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.venta_id_seq OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 102153)
-- Name: zona; Type: TABLE; Schema: business; Owner: postgres
--

CREATE TABLE business.zona (
    id smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    ciudad_id smallint NOT NULL
);


ALTER TABLE business.zona OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 102157)
-- Name: zona_id_seq; Type: SEQUENCE; Schema: business; Owner: postgres
--

CREATE SEQUENCE business.zona_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE business.zona_id_seq OWNER TO postgres;

--
-- TOC entry 5142 (class 0 OID 0)
-- Dependencies: 238
-- Name: zona_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: postgres
--

ALTER SEQUENCE business.zona_id_seq OWNED BY business.zona.id;


--
-- TOC entry 239 (class 1259 OID 102158)
-- Name: access_key_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.access_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.access_key_seq OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 102159)
-- Name: access_keys; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.access_keys (
    id bigint DEFAULT nextval('public.access_key_seq'::regclass) NOT NULL,
    status boolean NOT NULL,
    system_user_id bigint NOT NULL,
    type_access character varying(15) NOT NULL,
    value_access character varying(1024) NOT NULL,
    is_verified_code boolean NOT NULL,
    code_verification character varying(150)
);


ALTER TABLE public.access_keys OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 102165)
-- Name: branch_office_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.branch_office_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.branch_office_seq OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 102166)
-- Name: branch_offices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.branch_offices (
    id integer DEFAULT nextval('public.branch_office_seq'::regclass) NOT NULL,
    address character varying(250),
    company_id integer,
    description character varying(500),
    name character varying(100),
    status boolean
);


ALTER TABLE public.branch_offices OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 102172)
-- Name: code_verify_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.code_verify_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.code_verify_seq OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 102173)
-- Name: code_verify; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.code_verify (
    id integer DEFAULT nextval('public.code_verify_seq'::regclass) NOT NULL,
    celular character varying(25) NOT NULL,
    codigo character varying(6) NOT NULL,
    fini timestamp without time zone NOT NULL,
    ffin timestamp without time zone NOT NULL,
    estado character varying(1) NOT NULL
);


ALTER TABLE public.code_verify OWNER TO postgres;

--
-- TOC entry 5143 (class 0 OID 0)
-- Dependencies: 244
-- Name: COLUMN code_verify.estado; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.code_verify.estado IS '0=expirado,1=aceptado,2=pendiente,';


--
-- TOC entry 245 (class 1259 OID 102177)
-- Name: company_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.company_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.company_seq OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 102178)
-- Name: companies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.companies (
    id integer DEFAULT nextval('public.company_seq'::regclass) NOT NULL,
    description character varying(250),
    email character varying(100),
    fax character varying(50),
    name character varying(100),
    nit character varying(25),
    phone character varying(100),
    place character varying(100),
    status boolean,
    type_company_id integer,
    web_page character varying(100)
);


ALTER TABLE public.companies OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 102184)
-- Name: management_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.management_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.management_seq OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 102185)
-- Name: managements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.managements (
    id integer DEFAULT nextval('public.management_seq'::regclass) NOT NULL,
    branch_office_id integer,
    end_date date,
    start_date date,
    status boolean,
    year_number smallint
);


ALTER TABLE public.managements OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 102189)
-- Name: menu_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menu_seq OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 102190)
-- Name: menu_submenu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_submenu (
    menu_id integer NOT NULL,
    submenu_id integer NOT NULL
);


ALTER TABLE public.menu_submenu OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 102193)
-- Name: menus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menus (
    id integer DEFAULT nextval('public.menu_seq'::regclass) NOT NULL,
    description character varying(150),
    icon character varying(50),
    name character varying(50),
    status boolean,
    url character varying(50)
);


ALTER TABLE public.menus OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 102197)
-- Name: module_system_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.module_system_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.module_system_seq OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 102198)
-- Name: modules_systems; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modules_systems (
    id integer DEFAULT nextval('public.module_system_seq'::regclass) NOT NULL,
    code_module character varying(25),
    description character varying(150),
    name character varying(50),
    status boolean
);


ALTER TABLE public.modules_systems OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 102202)
-- Name: notification_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notification_seq OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 102203)
-- Name: notifications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifications (
    id bigint DEFAULT nextval('public.notification_seq'::regclass) NOT NULL,
    title character varying(100) NOT NULL,
    description character varying(500) NOT NULL,
    url character varying(50),
    param_type_notification bigint NOT NULL,
    status_notification character(1) NOT NULL,
    date_register timestamp(0) without time zone NOT NULL,
    status boolean DEFAULT true NOT NULL,
    system_user_id bigint
);


ALTER TABLE public.notifications OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 102210)
-- Name: person_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.person_seq OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 102211)
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
    id bigint DEFAULT nextval('public.person_seq'::regclass) NOT NULL,
    ci character varying(25),
    first_lastname character varying(100),
    gender character varying(1),
    name character varying(100),
    second_lastname character varying(100),
    status boolean,
    codigo_celular character varying(7),
    numero_celular character varying(15)
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 102215)
-- Name: rol_acceso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol_acceso (
    rol_id integer NOT NULL,
    menu_id integer NOT NULL,
    submenu_id integer NOT NULL
);


ALTER TABLE public.rol_acceso OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 102218)
-- Name: rol_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol_menu (
    rol_id integer NOT NULL,
    menu_id integer NOT NULL
);


ALTER TABLE public.rol_menu OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 102221)
-- Name: rol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rol_seq OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 102222)
-- Name: rol_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol_task (
    rol_id integer NOT NULL,
    task_id integer NOT NULL
);


ALTER TABLE public.rol_task OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 102225)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer DEFAULT nextval('public.rol_seq'::regclass) NOT NULL,
    description character varying(150),
    icon character varying(50),
    name character varying(50),
    status boolean,
    authority character varying(255)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 102231)
-- Name: submenu_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.submenu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.submenu_seq OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 102232)
-- Name: submenus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.submenus (
    id integer DEFAULT nextval('public.submenu_seq'::regclass) NOT NULL,
    description character varying(150),
    icon character varying(50),
    name character varying(50),
    status boolean NOT NULL,
    url character varying(50)
);


ALTER TABLE public.submenus OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 102236)
-- Name: systems_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.systems_users (
    id bigint NOT NULL,
    alias character varying(25),
    avatar character varying(50),
    email character varying(100),
    status boolean,
    type_system_user character varying(6),
    username character varying(50),
    celular character varying(15)
);


ALTER TABLE public.systems_users OWNER TO postgres;

--
-- TOC entry 5144 (class 0 OID 0)
-- Dependencies: 265
-- Name: COLUMN systems_users.type_system_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.systems_users.type_system_user IS 'Root: Administrador de sistemas, Usuario: Encargado de sistemas, Admin: administrador o gerente de empresa, WORKER: trabajador, PUBLIC:cliente y otros usuarios de la ciudad';


--
-- TOC entry 266 (class 1259 OID 102239)
-- Name: task_controller_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.task_controller_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.task_controller_seq OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 102240)
-- Name: task_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.task_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.task_seq OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 102241)
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    id integer DEFAULT nextval('public.task_seq'::regclass) NOT NULL,
    description character varying(150),
    name character varying(50),
    status boolean,
    task_controller_id integer,
    url character varying(50)
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 102245)
-- Name: tasks_controllers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks_controllers (
    task_controller_id integer DEFAULT nextval('public.task_controller_seq'::regclass) NOT NULL,
    description character varying(150),
    module_system_id integer,
    name character varying(50),
    status boolean
);


ALTER TABLE public.tasks_controllers OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 102249)
-- Name: user_rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_rol (
    system_user_id bigint NOT NULL,
    rol_id integer NOT NULL
);


ALTER TABLE public.user_rol OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 102252)
-- Name: users_managements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_managements (
    user_management_id bigint NOT NULL,
    management_id integer,
    status boolean,
    system_user_id bigint,
    type_operation character varying(15)
);


ALTER TABLE public.users_managements OWNER TO postgres;

--
-- TOC entry 4796 (class 2604 OID 102255)
-- Name: area_producto id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.area_producto ALTER COLUMN id SET DEFAULT nextval('business.area_producto_id_seq'::regclass);


--
-- TOC entry 4798 (class 2604 OID 102256)
-- Name: barrio id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.barrio ALTER COLUMN id SET DEFAULT nextval('business.barrio_id_seq'::regclass);


--
-- TOC entry 4800 (class 2604 OID 102257)
-- Name: caracteristica id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.caracteristica ALTER COLUMN id SET DEFAULT nextval('business.caracteristica_id_seq'::regclass);


--
-- TOC entry 4802 (class 2604 OID 102258)
-- Name: categoria_producto id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.categoria_producto ALTER COLUMN id SET DEFAULT nextval('business.categoria_id_seq'::regclass);


--
-- TOC entry 4804 (class 2604 OID 102259)
-- Name: ciudad id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.ciudad ALTER COLUMN id SET DEFAULT nextval('business.ciudad_id_seq'::regclass);


--
-- TOC entry 4807 (class 2604 OID 102260)
-- Name: historico id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.historico ALTER COLUMN id SET DEFAULT nextval('business.historico_id_seq'::regclass);


--
-- TOC entry 4808 (class 2604 OID 102261)
-- Name: producto id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.producto ALTER COLUMN id SET DEFAULT nextval('business.producto_id_seq'::regclass);


--
-- TOC entry 4810 (class 2604 OID 102262)
-- Name: tipo_producto id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.tipo_producto ALTER COLUMN id SET DEFAULT nextval('business.tipo_producto_id_seq'::regclass);


--
-- TOC entry 4812 (class 2604 OID 102263)
-- Name: zona id; Type: DEFAULT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.zona ALTER COLUMN id SET DEFAULT nextval('business.zona_id_seq'::regclass);


--
-- TOC entry 5068 (class 0 OID 102098)
-- Dependencies: 218
-- Data for Name: area_producto; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.area_producto (id, nombre, descripcion, icono, status) FROM stdin;
1	Area General	Area generica	fa fa-user	t
\.


--
-- TOC entry 5070 (class 0 OID 102105)
-- Dependencies: 220
-- Data for Name: barrio; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.barrio (id, nombre, status, zona_id) FROM stdin;
1	barrio 4 julio	t	1
\.


--
-- TOC entry 5072 (class 0 OID 102110)
-- Dependencies: 222
-- Data for Name: caracteristica; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.caracteristica (id, nombre, tipo, status) FROM stdin;
1	Unidad	1	t
2	Caja	2	t
3	Cliente General	3	t
\.


--
-- TOC entry 5074 (class 0 OID 102115)
-- Dependencies: 224
-- Data for Name: categoria_producto; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.categoria_producto (id, nombre, descripcion, status, tipo_producto_id) FROM stdin;
1	Tradicionales	vino tradicionales	t	1
2	Varietal	vino varietal	t	1
3	Sin categoria	Sin categoria	t	1
\.


--
-- TOC entry 5076 (class 0 OID 102122)
-- Dependencies: 226
-- Data for Name: ciudad; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.ciudad (id, nombre, status) FROM stdin;
1	Tarija	t
\.


--
-- TOC entry 5078 (class 0 OID 102127)
-- Dependencies: 228
-- Data for Name: cliente; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.cliente (id, alias, direccion, email, nombre_negocio, descripcion_negocio, tipo_cliente, barrio_id, status) FROM stdin;
1	Cliente General	Sin direccion	sincorreo@gmail.com	Sin negocio	Sin descripcion	3	1	t
\.


--
-- TOC entry 5080 (class 0 OID 102134)
-- Dependencies: 230
-- Data for Name: historico; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.historico (id, fecha) FROM stdin;
3	2024-11-04
1	2024-11-06
2	2024-11-07
4	2024-11-12
5	2024-11-13
6	2024-11-14
7	2024-11-15
8	2024-11-16
9	2024-11-17
10	2024-11-18
11	2024-11-19
12	2024-11-29
13	2024-11-30
14	2024-12-01
15	2024-12-02
16	2024-12-03
17	2024-12-04
18	2024-12-05
19	2024-12-06
20	2024-12-07
21	2024-12-08
22	2024-12-09
23	2024-12-10
24	2024-12-11
25	2024-12-12
26	2024-12-13
27	2024-12-14
28	2024-12-15
29	2024-12-16
30	2024-12-17
31	2024-12-18
32	2024-12-19
33	2024-12-20
34	2024-12-21
35	2024-12-22
36	2024-12-23
37	2024-12-24
38	2024-12-25
39	2024-12-26
40	2024-12-27
41	2024-12-28
42	2024-12-29
43	2024-12-30
44	2024-12-31
45	2025-01-01
46	2025-01-02
47	2025-01-03
48	2025-01-04
49	2025-01-05
50	2025-01-06
51	2025-01-07
52	2025-01-08
53	2025-01-09
54	2025-01-10
55	2025-01-11
56	2025-01-12
57	2025-01-13
58	2025-01-14
59	2025-01-15
60	2025-01-16
61	2025-01-17
62	2025-01-18
63	2025-01-19
64	2025-01-20
65	2025-01-21
66	2025-01-22
67	2025-01-23
68	2025-01-24
69	2025-01-25
70	2025-01-26
71	2025-01-27
72	2025-01-28
73	2025-01-29
74	2025-01-30
75	2025-01-31
76	2025-02-01
77	2025-02-02
78	2025-02-03
\.


--
-- TOC entry 5082 (class 0 OID 102138)
-- Dependencies: 232
-- Data for Name: producto; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.producto (id, nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id) FROM stdin;
1	Uvas en singani	Uvas en singani	Sin descripcion	Sin caracteristicas	1	producto.jpg	12	50	100	1.00	1.00	15.00	180.00	0.00	0.00	0.00	0.00	t	1	2
2	Vinos 60ml	Vinos minis 60ml	Sin descripcion 	Sin caracteristica	1	producto.jpg	20	50	100	1.00	1.00	10.00	200.00	0.00	0.00	0.00	0.00	t	1	2
3	Vinos 300 cc	Vinos medianos 300 cc	Sin descripcion	Sin caracteristica	1	producto.jpg	12	20	60	1.00	1.00	20.00	240.00	0.00	0.00	0.00	0.00	t	1	2
4	Mistela	Mistela	Dulce	Mistela dulce	1	producto.jpg	6	50	100	1.00	1.00	50.00	300.00	0.00	0.00	0.00	0.00	t	1	2
5	Licor de uva	Licor de uva	Dulce	Licor dulce	1	producto.jpg	6	50	100	1.00	1.00	40.00	240.00	0.00	0.00	0.00	0.00	t	1	2
6	Licor de membrillo	Licor de membrillo	dulce	Licor ddulce	1	producto.jpg	6	50	100	1.00	1.00	40.00	240.00	0.00	0.00	0.00	0.00	t	1	2
7	Sigani pletorico	Singani pletorico	Sin descripcion	Sin caracteristica	1	producto.jpg	6	50	100	1.00	1.00	60.00	360.00	0.00	0.00	0.00	0.00	t	1	2
8	Vino 5 santos	5 santos	Seco	Vino seco	3	producto.jpg	6	50	100	1.00	1.00	45.00	270.00	0.00	0.00	0.00	0.00	t	1	2
9	Vino ugni blanc aventurado	Ugni blanc aventurado	Seco	Vino seco	3	producto.jpg	6	50	100	1.00	1.00	35.00	210.00	0.00	0.00	0.00	0.00	t	1	2
10	Vino chardonnay asr	Chardonnay asr	Seco	Vino seco	3	producto.jpg	6	50	100	1.00	1.00	60.00	360.00	0.00	0.00	0.00	0.00	t	1	2
11	Vino cabernet asuvignon shn	Cabernet asuvignon shn	seco	vino seco	3	producto.jpg	6	50	100	6.00	6.00	80.00	480.00	0.00	0.00	0.00	0.00	t	1	2
12	Vino tannat gran victoria	Tannat gran victoria	Seco	vino seco	3	producto.jpg	6	50	100	1.00	1.00	80.00	480.00	0.00	0.00	0.00	0.00	t	1	2
13	vino oporto rosado	Rosado oporto	dulce	vino dulce rosado	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
14	vino blanco oporto	Blanco oporto	dulce	vino dulce blanco	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
15	vino tinto choelro	Tinto cholero	semidulce	vino semidulce tinto	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
16	vino rosado cholero	Rosado cholero	semidulce	vino semidulce cholero	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
17	vino blanco cholero	Blanco cholero	semidulce	vino semidulce blanco	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
18	vino tinto mellicero	Tinto mellicero	semiseco	vino semiseco tinto	2	producto.jpg	6	50	100	1.00	1.00	50.00	150.00	0.00	0.00	0.00	0.00	t	1	2
19	Vino blanco mellicero	Blanco mellicero	semiseco	vino semiseco blanco	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
20	vino tinto aspero	Tinto aspero	seco	vino seco tinto	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
21	vino blanco aspero	Blanco aspero	seco	vino seco blanco	2	producto.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
22	Vino cholero rosado	Cholero rosado	semidulce	vino emidulce rosado	2	producto-d2b3da36-29ed-49d8-b0b8-098604a0b3cd.jpg	6	50	100	1.00	1.00	25.00	150.00	0.00	0.00	0.00	0.00	t	1	2
\.


--
-- TOC entry 5084 (class 0 OID 102145)
-- Dependencies: 234
-- Data for Name: tipo_producto; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.tipo_producto (id, nombre, descripcion, status, area_producto_id) FROM stdin;
1	Tipo General	Todas las categorias	t	1
\.


--
-- TOC entry 5087 (class 0 OID 102153)
-- Dependencies: 237
-- Data for Name: zona; Type: TABLE DATA; Schema: business; Owner: postgres
--

COPY business.zona (id, nombre, status, ciudad_id) FROM stdin;
1	zona centro	t	1
\.


--
-- TOC entry 5090 (class 0 OID 102159)
-- Dependencies: 240
-- Data for Name: access_keys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.access_keys (id, status, system_user_id, type_access, value_access, is_verified_code, code_verification) FROM stdin;
1	t	2	USER_PASS	$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32	t	\N
2	t	6	USER_PASS	$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32	t	\N
3	t	7	USER_PASS	$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32	t	\N
\.


--
-- TOC entry 5092 (class 0 OID 102166)
-- Dependencies: 242
-- Data for Name: branch_offices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.branch_offices (id, address, company_id, description, name, status) FROM stdin;
1	Ciudad el Valle	1	A unas cuadras de la plaza principal	Casa Matriz	t
\.


--
-- TOC entry 5094 (class 0 OID 102173)
-- Dependencies: 244
-- Data for Name: code_verify; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.code_verify (id, celular, codigo, fini, ffin, estado) FROM stdin;
1	59175136609	528857	2023-10-30 16:25:07.548	2023-10-30 16:27:07.548	2
2	59175136609	801091	2023-10-30 16:28:17.656	2023-10-30 16:30:17.656	2
3	59175136609	490411	2023-10-30 16:33:56.278	2023-10-30 16:35:56.278	2
5	59175136609	965857	2023-10-30 16:57:14.959	2023-10-30 16:59:14.959	1
6	59175136609	796865	2023-10-30 16:58:19.015	2023-10-30 17:00:19.015	2
7	59175136609	600008	2023-10-30 16:59:47.753	2023-10-30 17:01:47.753	1
8	59175136609	796925	2023-10-30 17:00:40.602	2023-10-30 17:02:40.602	2
9	59175136609	884153	2023-10-30 17:01:09.945	2023-10-30 17:03:09.945	2
10	59175136609	367689	2023-10-30 17:01:36.615	2023-10-30 17:03:36.615	2
11	59175136609	971572	2023-11-03 22:45:54.306	2023-11-03 22:47:54.306	1
12	59167997327	845343	2023-11-08 17:28:54.4	2023-11-08 17:30:54.4	2
13	59167997327	558605	2023-11-08 17:31:57.93	2023-11-08 17:33:57.93	2
14	59167997327	871770	2023-11-08 17:43:03.441	2023-11-08 17:45:03.441	2
15	59167997327	757268	2023-11-08 17:46:52.049	2023-11-08 17:48:52.049	1
16	59176198260	329369	2023-11-08 17:55:36.215	2023-11-08 17:57:36.215	1
17	59176198260	784726	2023-11-08 19:02:20.838	2023-11-08 19:04:20.838	1
18	59167997327	561811	2023-11-08 19:04:08.845	2023-11-08 19:06:08.845	1
19	59167997327	895366	2023-11-08 19:11:34.218	2023-11-08 19:13:34.218	1
20	59167997327	858851	2023-11-08 23:35:49.454	2023-11-09 00:05:49.454	1
21	59167997327	200187	2023-11-13 14:03:51.95	2023-11-13 14:33:51.95	1
22	59175136609	335575	2023-11-23 13:50:42.001	2023-11-23 14:20:42.001	1
23	59175119767	271412	2023-11-23 13:55:43.621	2023-11-23 14:25:43.621	1
24	59175136609	471771	2023-11-23 14:12:16.419	2023-11-23 14:42:16.419	1
25	59175136609	346907	2023-11-26 22:29:40.002	2023-11-26 22:59:40.002	1
26	59167997327	926538	2023-11-26 22:32:20.573	2023-11-26 23:02:20.573	1
27	59160281682	446634	2023-11-28 14:06:38.657	2023-11-28 14:36:38.657	1
28	59167997327	497284	2023-12-01 09:13:06.858	2023-12-01 09:43:06.858	1
29	59167997327	382549	2024-05-24 16:59:55.965	2024-05-24 17:29:55.965	2
30	59167997327	946705	2024-05-24 17:04:23.067	2024-05-24 17:34:23.067	1
31	59167997327	481188	2024-05-25 23:35:38.304	2024-05-26 00:05:38.304	1
\.


--
-- TOC entry 5096 (class 0 OID 102178)
-- Dependencies: 246
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.companies (id, description, email, fax, name, nit, phone, place, status, type_company_id, web_page) FROM stdin;
1	Venta de Vinos artesanales y sus derivados	casaviejatarija@gmail.com	0	Casa Vieja	0	Telf.:7777777 - 66-457874	Tarija - Bolivia	t	2	http://www.casaviejablabla.com
\.


--
-- TOC entry 5098 (class 0 OID 102185)
-- Dependencies: 248
-- Data for Name: managements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.managements (id, branch_office_id, end_date, start_date, status, year_number) FROM stdin;
1	1	2024-12-31	2024-01-01	t	2024
\.


--
-- TOC entry 5100 (class 0 OID 102190)
-- Dependencies: 250
-- Data for Name: menu_submenu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu_submenu (menu_id, submenu_id) FROM stdin;
3	8
3	9
3	10
3	11
1	1
1	2
1	3
1	7
3	20
\.


--
-- TOC entry 5101 (class 0 OID 102193)
-- Dependencies: 251
-- Data for Name: menus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menus (id, description, icon, name, status, url) FROM stdin;
1	Grupo de accesos para controlar el sistema	manage_accounts	Control de Sistema	t	#
2	Configura todos los parametros iniciales para la empresa	style	Configuración empresarial	t	#
3	Grupo de accesos del contador	card_travel	Accesos del Contador	t	#
\.


--
-- TOC entry 5103 (class 0 OID 102198)
-- Dependencies: 253
-- Data for Name: modules_systems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.modules_systems (id, code_module, description, name, status) FROM stdin;
\.


--
-- TOC entry 5105 (class 0 OID 102203)
-- Dependencies: 255
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifications (id, title, description, url, param_type_notification, status_notification, date_register, status, system_user_id) FROM stdin;
\.


--
-- TOC entry 5107 (class 0 OID 102211)
-- Dependencies: 257
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persons (id, ci, first_lastname, gender, name, second_lastname, status, codigo_celular, numero_celular) FROM stdin;
1	0000000	GENERAL	M	USUARIO		t	\N	\N
2	0000000	SYSTEM	M	ADMIN	PRO	t	\N	\N
6	7112945	LAZCANO	M	LUIS ARMANDO	 QUIROGA	t	\N	\N
7	7174924	LÓPEZ	F	CINTHIA SOLEDAD	RODRIGUEZ	t	\N	\N
\.


--
-- TOC entry 5108 (class 0 OID 102215)
-- Dependencies: 258
-- Data for Name: rol_acceso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rol_acceso (rol_id, menu_id, submenu_id) FROM stdin;
\.


--
-- TOC entry 5109 (class 0 OID 102218)
-- Dependencies: 259
-- Data for Name: rol_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rol_menu (rol_id, menu_id) FROM stdin;
1	1
1	2
1	3
3	2
3	3
2	1
2	2
2	3
\.


--
-- TOC entry 5111 (class 0 OID 102222)
-- Dependencies: 261
-- Data for Name: rol_task; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rol_task (rol_id, task_id) FROM stdin;
\.


--
-- TOC entry 5112 (class 0 OID 102225)
-- Dependencies: 262
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, description, icon, name, status, authority) FROM stdin;
1	Super usuario, con derecho a todo	warning	Super Usuario	t	ROLE_ROOT
2	Administrador del sistema	warning	Administrador del Sistema	t	ROLE_ADMIN
3	Encargado de una empresa	info	Gerente	t	ROLE_GERENT
4	Trabajador que ofrece sus servicios	fa fa-adn	Distribuidor	t	ROLE_WORKER
5	Gestionas las cuentas contables	arrow_upward	Contador(a)	t	ROLE_USER
6	Moderador del sistema	fa fa-linux	Moderador	t	ROLE_USER
7	Cliente final que busca servicios	center_focus_weak	Cliente	t	ROLE_CLIENT
8	Grupo de personas externas a la empresa que puede ver la pagina web	warning	Público General	t	ROLE_PUBLIC
\.


--
-- TOC entry 5114 (class 0 OID 102232)
-- Dependencies: 264
-- Data for Name: submenus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.submenus (id, description, icon, name, status, url) FROM stdin;
20	Gestiona los usuarios del sistema	account_circle	Usuarios Sucursal	t	../../main/usuarioSucursal
9	Gestiona las ciudades, zonas y barrios	person_pin_circle	Lugares	t	../../business/ciudad
8	Gestiona las caracteristicas que se usan en la empresa	widgets	Caracteristicas	t	../../business/caracteristica
10	Gestiona los clientes de la empresa	emoji_people	Clientes	t	../../business/cliente
11	Gestiona las categorias de productos	category	Categoria	t	../../business/categoriaProductoGestion
7	Agrupa los usuarios del sistema	account_circle	Usuarios	t	../../main/usuarioGestion
5	Controla los diferentes tipo de accesos 	admin_panel_settings	Control de accesos	t	../../main/controlDeAccesos
6	Configuracion general del sistema.	settings	Configuracion del sistema	t	../../main/configuracion
1	Gestiona los submenus	menu_open	Submenus	t	../../main/submenuGestion
2	Roles del sistema	work	Roles	t	../../main/rolGestion
3	Menus del sistema	list	Menus	t	../../main/menuGestion
4	Agrupa las empresas del sistema	store	Empresa	t	../../main/empresaGestion
\.


--
-- TOC entry 5115 (class 0 OID 102236)
-- Dependencies: 265
-- Data for Name: systems_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.systems_users (id, alias, avatar, email, status, type_system_user, username, celular) FROM stdin;
2	admin7167968	avatar_1.png	admin@sibol.net	t	ROOT	admin	\N
6	luchito	avatar1.png	luchitolazcano30@gmail.com	t	ADMIN	llazcano	\N
7	CLopez	avatar2.png	lcinthia040@gmail.com	t	USER	clopez	\N
\.


--
-- TOC entry 5118 (class 0 OID 102241)
-- Dependencies: 268
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (id, description, name, status, task_controller_id, url) FROM stdin;
\.


--
-- TOC entry 5119 (class 0 OID 102245)
-- Dependencies: 269
-- Data for Name: tasks_controllers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks_controllers (task_controller_id, description, module_system_id, name, status) FROM stdin;
\.


--
-- TOC entry 5120 (class 0 OID 102249)
-- Dependencies: 270
-- Data for Name: user_rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_rol (system_user_id, rol_id) FROM stdin;
2	1
6	1
7	5
\.


--
-- TOC entry 5121 (class 0 OID 102252)
-- Dependencies: 271
-- Data for Name: users_managements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_managements (user_management_id, management_id, status, system_user_id, type_operation) FROM stdin;
1	1	t	2	READ_WRITE
2	1	t	6	READ_WRITE
3	1	t	7	READ_WRITE
\.


--
-- TOC entry 5145 (class 0 OID 0)
-- Dependencies: 219
-- Name: area_producto_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.area_producto_id_seq', 1, true);


--
-- TOC entry 5146 (class 0 OID 0)
-- Dependencies: 221
-- Name: barrio_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.barrio_id_seq', 1, true);


--
-- TOC entry 5147 (class 0 OID 0)
-- Dependencies: 223
-- Name: caracteristica_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.caracteristica_id_seq', 3, true);


--
-- TOC entry 5148 (class 0 OID 0)
-- Dependencies: 225
-- Name: categoria_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.categoria_id_seq', 3, true);


--
-- TOC entry 5149 (class 0 OID 0)
-- Dependencies: 227
-- Name: ciudad_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.ciudad_id_seq', 1, true);


--
-- TOC entry 5150 (class 0 OID 0)
-- Dependencies: 229
-- Name: cliente_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.cliente_seq', 1, false);


--
-- TOC entry 5151 (class 0 OID 0)
-- Dependencies: 231
-- Name: historico_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.historico_id_seq', 1, false);


--
-- TOC entry 5152 (class 0 OID 0)
-- Dependencies: 233
-- Name: producto_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.producto_id_seq', 22, true);


--
-- TOC entry 5153 (class 0 OID 0)
-- Dependencies: 235
-- Name: tipo_producto_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.tipo_producto_id_seq', 1, true);


--
-- TOC entry 5154 (class 0 OID 0)
-- Dependencies: 236
-- Name: venta_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.venta_id_seq', 35, true);


--
-- TOC entry 5155 (class 0 OID 0)
-- Dependencies: 238
-- Name: zona_id_seq; Type: SEQUENCE SET; Schema: business; Owner: postgres
--

SELECT pg_catalog.setval('business.zona_id_seq', 1, true);


--
-- TOC entry 5156 (class 0 OID 0)
-- Dependencies: 239
-- Name: access_key_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.access_key_seq', 3, true);


--
-- TOC entry 5157 (class 0 OID 0)
-- Dependencies: 241
-- Name: branch_office_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.branch_office_seq', 1, true);


--
-- TOC entry 5158 (class 0 OID 0)
-- Dependencies: 243
-- Name: code_verify_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.code_verify_seq', 1, false);


--
-- TOC entry 5159 (class 0 OID 0)
-- Dependencies: 245
-- Name: company_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.company_seq', 1, true);


--
-- TOC entry 5160 (class 0 OID 0)
-- Dependencies: 247
-- Name: management_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.management_seq', 1, true);


--
-- TOC entry 5161 (class 0 OID 0)
-- Dependencies: 249
-- Name: menu_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menu_seq', 3, true);


--
-- TOC entry 5162 (class 0 OID 0)
-- Dependencies: 252
-- Name: module_system_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.module_system_seq', 1, false);


--
-- TOC entry 5163 (class 0 OID 0)
-- Dependencies: 254
-- Name: notification_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notification_seq', 1, false);


--
-- TOC entry 5164 (class 0 OID 0)
-- Dependencies: 256
-- Name: person_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_seq', 2, true);


--
-- TOC entry 5165 (class 0 OID 0)
-- Dependencies: 260
-- Name: rol_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rol_seq', 8, true);


--
-- TOC entry 5166 (class 0 OID 0)
-- Dependencies: 263
-- Name: submenu_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.submenu_seq', 1, false);


--
-- TOC entry 5167 (class 0 OID 0)
-- Dependencies: 266
-- Name: task_controller_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.task_controller_seq', 1, false);


--
-- TOC entry 5168 (class 0 OID 0)
-- Dependencies: 267
-- Name: task_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.task_seq', 1, false);


--
-- TOC entry 4829 (class 2606 OID 102265)
-- Name: area_producto area_producto_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.area_producto
    ADD CONSTRAINT area_producto_pk PRIMARY KEY (id);


--
-- TOC entry 4831 (class 2606 OID 102267)
-- Name: barrio barrio_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.barrio
    ADD CONSTRAINT barrio_pk PRIMARY KEY (id);


--
-- TOC entry 4833 (class 2606 OID 102269)
-- Name: caracteristica caracteristica_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.caracteristica
    ADD CONSTRAINT caracteristica_pk PRIMARY KEY (id);


--
-- TOC entry 4835 (class 2606 OID 102271)
-- Name: categoria_producto categoria_pkey; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.categoria_producto
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 4837 (class 2606 OID 102273)
-- Name: ciudad ciudad_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.ciudad
    ADD CONSTRAINT ciudad_pk PRIMARY KEY (id);


--
-- TOC entry 4839 (class 2606 OID 102275)
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (id);


--
-- TOC entry 4841 (class 2606 OID 102277)
-- Name: historico historico_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.historico
    ADD CONSTRAINT historico_pk PRIMARY KEY (id);


--
-- TOC entry 4843 (class 2606 OID 102279)
-- Name: producto producto_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.producto
    ADD CONSTRAINT producto_pk PRIMARY KEY (id);


--
-- TOC entry 4845 (class 2606 OID 102281)
-- Name: tipo_producto tipo_pkey; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.tipo_producto
    ADD CONSTRAINT tipo_pkey PRIMARY KEY (id);


--
-- TOC entry 4847 (class 2606 OID 102283)
-- Name: zona zona_pk; Type: CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.zona
    ADD CONSTRAINT zona_pk PRIMARY KEY (id);


--
-- TOC entry 4849 (class 2606 OID 102285)
-- Name: access_keys access_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.access_keys
    ADD CONSTRAINT access_keys_pkey PRIMARY KEY (id);


--
-- TOC entry 4851 (class 2606 OID 102287)
-- Name: branch_offices branch_offices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.branch_offices
    ADD CONSTRAINT branch_offices_pkey PRIMARY KEY (id);


--
-- TOC entry 4853 (class 2606 OID 102289)
-- Name: code_verify code_verify_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.code_verify
    ADD CONSTRAINT code_verify_pk PRIMARY KEY (id);


--
-- TOC entry 4855 (class 2606 OID 102291)
-- Name: companies companies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- TOC entry 4857 (class 2606 OID 102293)
-- Name: managements managements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managements
    ADD CONSTRAINT managements_pkey PRIMARY KEY (id);


--
-- TOC entry 4859 (class 2606 OID 102295)
-- Name: menu_submenu menu_submenu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_submenu
    ADD CONSTRAINT menu_submenu_pkey PRIMARY KEY (menu_id, submenu_id);


--
-- TOC entry 4861 (class 2606 OID 102297)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 4863 (class 2606 OID 102299)
-- Name: modules_systems modules_systems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modules_systems
    ADD CONSTRAINT modules_systems_pkey PRIMARY KEY (id);


--
-- TOC entry 4865 (class 2606 OID 102301)
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- TOC entry 4867 (class 2606 OID 102303)
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- TOC entry 4869 (class 2606 OID 102305)
-- Name: rol_acceso rol_acceso_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_acceso
    ADD CONSTRAINT rol_acceso_pk PRIMARY KEY (rol_id, menu_id, submenu_id);


--
-- TOC entry 4871 (class 2606 OID 102307)
-- Name: rol_menu rol_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_menu
    ADD CONSTRAINT rol_menu_pkey PRIMARY KEY (rol_id, menu_id);


--
-- TOC entry 4873 (class 2606 OID 102309)
-- Name: rol_task rol_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_task
    ADD CONSTRAINT rol_task_pkey PRIMARY KEY (rol_id, task_id);


--
-- TOC entry 4875 (class 2606 OID 102311)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4877 (class 2606 OID 102313)
-- Name: submenus submenus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.submenus
    ADD CONSTRAINT submenus_pkey PRIMARY KEY (id);


--
-- TOC entry 4879 (class 2606 OID 102315)
-- Name: systems_users systems_users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.systems_users
    ADD CONSTRAINT systems_users_email_key UNIQUE (email);


--
-- TOC entry 4881 (class 2606 OID 102317)
-- Name: systems_users systems_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.systems_users
    ADD CONSTRAINT systems_users_pkey PRIMARY KEY (id);


--
-- TOC entry 4883 (class 2606 OID 102319)
-- Name: systems_users systems_users_un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.systems_users
    ADD CONSTRAINT systems_users_un UNIQUE (celular);


--
-- TOC entry 4885 (class 2606 OID 102321)
-- Name: systems_users systems_users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.systems_users
    ADD CONSTRAINT systems_users_username_key UNIQUE (username);


--
-- TOC entry 4889 (class 2606 OID 102323)
-- Name: tasks_controllers tasks_controllers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks_controllers
    ADD CONSTRAINT tasks_controllers_pkey PRIMARY KEY (task_controller_id);


--
-- TOC entry 4887 (class 2606 OID 102325)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 4891 (class 2606 OID 102327)
-- Name: user_rol user_rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_rol
    ADD CONSTRAINT user_rol_pkey PRIMARY KEY (system_user_id, rol_id);


--
-- TOC entry 4893 (class 2606 OID 102329)
-- Name: users_managements users_managements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_managements
    ADD CONSTRAINT users_managements_pkey PRIMARY KEY (user_management_id);


--
-- TOC entry 4895 (class 2606 OID 102330)
-- Name: categoria_producto categoria_tipo_id_fkey; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.categoria_producto
    ADD CONSTRAINT categoria_tipo_id_fkey FOREIGN KEY (tipo_producto_id) REFERENCES business.tipo_producto(id);


--
-- TOC entry 4894 (class 2606 OID 102335)
-- Name: barrio ciudad_zona_id_fkey; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.barrio
    ADD CONSTRAINT ciudad_zona_id_fkey FOREIGN KEY (zona_id) REFERENCES business.zona(id);


--
-- TOC entry 4896 (class 2606 OID 102340)
-- Name: cliente cliente_barrio_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.cliente
    ADD CONSTRAINT cliente_barrio_fk FOREIGN KEY (barrio_id) REFERENCES business.barrio(id);


--
-- TOC entry 4897 (class 2606 OID 102345)
-- Name: cliente cliente_persons_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.cliente
    ADD CONSTRAINT cliente_persons_fk FOREIGN KEY (id) REFERENCES public.persons(id);


--
-- TOC entry 4898 (class 2606 OID 102350)
-- Name: cliente cliente_tipo_cliente_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.cliente
    ADD CONSTRAINT cliente_tipo_cliente_fk FOREIGN KEY (tipo_cliente) REFERENCES business.caracteristica(id);


--
-- TOC entry 4899 (class 2606 OID 102355)
-- Name: producto producto_categoria_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.producto
    ADD CONSTRAINT producto_categoria_fk FOREIGN KEY (categoria_id) REFERENCES business.categoria_producto(id);


--
-- TOC entry 4900 (class 2606 OID 102360)
-- Name: producto producto_presentacion_caja_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.producto
    ADD CONSTRAINT producto_presentacion_caja_fk FOREIGN KEY (presentacion_caja_id) REFERENCES business.caracteristica(id);


--
-- TOC entry 4901 (class 2606 OID 102365)
-- Name: producto producto_presentacion_unidad_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.producto
    ADD CONSTRAINT producto_presentacion_unidad_fk FOREIGN KEY (presentacion_unit_id) REFERENCES business.caracteristica(id);


--
-- TOC entry 4902 (class 2606 OID 102370)
-- Name: tipo_producto tipo_producto_area_producto_fk; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.tipo_producto
    ADD CONSTRAINT tipo_producto_area_producto_fk FOREIGN KEY (area_producto_id) REFERENCES business.area_producto(id);


--
-- TOC entry 4903 (class 2606 OID 102375)
-- Name: zona zona_ciudad_id_fkey; Type: FK CONSTRAINT; Schema: business; Owner: postgres
--

ALTER TABLE ONLY business.zona
    ADD CONSTRAINT zona_ciudad_id_fkey FOREIGN KEY (ciudad_id) REFERENCES business.ciudad(id);


--
-- TOC entry 4904 (class 2606 OID 102380)
-- Name: access_keys access_keys_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.access_keys
    ADD CONSTRAINT access_keys_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES public.systems_users(id);


--
-- TOC entry 4905 (class 2606 OID 102385)
-- Name: branch_offices branch_office_company_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.branch_offices
    ADD CONSTRAINT branch_office_company_id_fkey FOREIGN KEY (company_id) REFERENCES public.companies(id);


--
-- TOC entry 4906 (class 2606 OID 102390)
-- Name: managements managements_branch_office_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.managements
    ADD CONSTRAINT managements_branch_office_id_fkey FOREIGN KEY (branch_office_id) REFERENCES public.branch_offices(id);


--
-- TOC entry 4907 (class 2606 OID 102395)
-- Name: menu_submenu menu_submenu_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_submenu
    ADD CONSTRAINT menu_submenu_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES public.menus(id);


--
-- TOC entry 4908 (class 2606 OID 102400)
-- Name: menu_submenu menu_submenu_submenu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_submenu
    ADD CONSTRAINT menu_submenu_submenu_id_fkey FOREIGN KEY (submenu_id) REFERENCES public.submenus(id);


--
-- TOC entry 4909 (class 2606 OID 102405)
-- Name: notifications notifications_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES public.systems_users(id);


--
-- TOC entry 4910 (class 2606 OID 102410)
-- Name: rol_acceso rol_acceso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_acceso
    ADD CONSTRAINT rol_acceso_fk FOREIGN KEY (rol_id) REFERENCES public.roles(id);


--
-- TOC entry 4911 (class 2606 OID 102415)
-- Name: rol_acceso rol_acceso_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_acceso
    ADD CONSTRAINT rol_acceso_fk_1 FOREIGN KEY (submenu_id) REFERENCES public.submenus(id);


--
-- TOC entry 4912 (class 2606 OID 102420)
-- Name: rol_menu rol_menu_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_menu
    ADD CONSTRAINT rol_menu_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES public.menus(id);


--
-- TOC entry 4913 (class 2606 OID 102425)
-- Name: rol_menu rol_menu_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_menu
    ADD CONSTRAINT rol_menu_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roles(id);


--
-- TOC entry 4914 (class 2606 OID 102430)
-- Name: rol_task rol_task_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_task
    ADD CONSTRAINT rol_task_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roles(id);


--
-- TOC entry 4915 (class 2606 OID 102435)
-- Name: rol_task rol_task_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol_task
    ADD CONSTRAINT rol_task_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(id);


--
-- TOC entry 4916 (class 2606 OID 102440)
-- Name: systems_users systems_users_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.systems_users
    ADD CONSTRAINT systems_users_system_user_id_fkey FOREIGN KEY (id) REFERENCES public.persons(id);


--
-- TOC entry 4918 (class 2606 OID 102445)
-- Name: tasks_controllers tasks_controllers_module_system_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks_controllers
    ADD CONSTRAINT tasks_controllers_module_system_id_fkey FOREIGN KEY (module_system_id) REFERENCES public.modules_systems(id);


--
-- TOC entry 4917 (class 2606 OID 102450)
-- Name: tasks tasks_task_controller_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_task_controller_id_fkey FOREIGN KEY (task_controller_id) REFERENCES public.tasks_controllers(task_controller_id);


--
-- TOC entry 4919 (class 2606 OID 102455)
-- Name: user_rol user_rol_rol_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_rol
    ADD CONSTRAINT user_rol_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roles(id);


--
-- TOC entry 4920 (class 2606 OID 102460)
-- Name: user_rol user_rol_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_rol
    ADD CONSTRAINT user_rol_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES public.systems_users(id);


--
-- TOC entry 4921 (class 2606 OID 102465)
-- Name: users_managements users_managements_management_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_managements
    ADD CONSTRAINT users_managements_management_id_fkey FOREIGN KEY (management_id) REFERENCES public.managements(id);


--
-- TOC entry 4922 (class 2606 OID 102470)
-- Name: users_managements users_managements_system_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_managements
    ADD CONSTRAINT users_managements_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES public.systems_users(id);


--
-- TOC entry 5127 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2025-02-03 23:52:18

--
-- PostgreSQL database dump complete
--

