select sed.producto_id, p.nombre_comercial as xproducto, sum(seda.cantidad) as devuelto, sum(ad.cantidad) as entregado  from business.salida_entrega_detalle_almacen seda
inner join business.almacen_distribuidor ad on seda.salida_id = ad.salida_id and seda.salida_entrega_detalle_id = ad.salida_entrega_detalle_id and seda.salida_entrega_id = ad.salida_entrega_id and seda.almacen_id = ad.almacen_id
inner join business.salida_entrega_detalle sed on seda.salida_id = sed.salida_id and seda.salida_entrega_detalle_id = sed.id and seda.salida_entrega_id = sed.salida_entrega_id
inner join business.producto p on p.id = sed.producto_id
where seda.salida_id = 1
group by sed.producto_id, p.nombre_comercial;

-- DROP PROCEDURE business.save_aumentar_detalle_salida(int8, int8, varchar, _int4, _int4, _int4);

CREATE OR REPLACE PROCEDURE business.finalizar_salida(p_salida_id bigint, p_usuario_id bigint, p_obs character varying, p_efectivo_entregado numeric(10,2))
    LANGUAGE plpgsql
AS $procedure$
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
    update business.almacen set cantidad = cantidad + sq.cantidad from
        (select ad.almacen_id,ad.cantidad from business.almacen_distribuidor ad
         where ad.salida_id = p_salida_id and ad.cantidad > 0) sq
    where id = sq.almacen_id;
    insert into business.log_movimiento_almacen(almacen_id, tipo, cantidad, obs, fecha)
    select ad.almacen_id, '9', ad.cantidad, concat('Ingreso por finalizar salida #', p_salida_id::text), current_timestamp from business.almacen_distribuidor ad
    where ad.salida_id = p_salida_id and ad.cantidad > 0 ;
    update business.salida set obs_al_finalizar = p_obs, total_efectivo_entregado = p_efectivo_entregado,updated_by = p_usuario_id, updated_at = current_timestamp, estado_salida = 'f', total_general = total_venta_contado + salida.total_otro_tipo_pago
    where id = p_salida_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'ERROR procedimiento finalizar_salida %',sqlerrm;
END
$procedure$
;

select count(*) > 0 from business.venta where salida_id = 1 and status = true;


-- DROP PROCEDURE business.finalizar_salida(int8, int8, varchar, numeric);

CREATE OR REPLACE PROCEDURE business.eliminar_salida(p_salida_id bigint, p_usuario_id bigint, p_obs character varying)
    LANGUAGE plpgsql
AS $procedure$
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
$procedure$
;

select count(*) > 0 from
(select id.cantidad_total_unitaria,a.cantidad from business.ingreso_detalle id
inner join business.almacen a on a.id = id.almacen_id
where id.id =2 and id.cantidad_total_unitaria <> a.cantidad) sq;

select count(*) > 0 from
(SELECT almacen_id, a.cantidad, m.cantidad
FROM business.almacen_movimiento m
inner join business.almacen a on m.almacen_id = a.id
where m.movimiento_inventario_id = 2 and m.cantidad > a.cantidad) sq;

select count(*) > 0 from
(select m.almacen_id, m.cantidad, a.cantidad from business.almacen_movimiento m
inner join business.almacen a on m.almacen_id = a.id
where m.movimiento_inventario_id = 1 and m.cantidad > a.cantidad) sq;

select p.id,coalesce(sum(a.cantidad),0) as cantidad
from business.producto p
left join business.almacen a on p.id = a.producto_id
where p.status = true and a.branch_office_id = 1
group by p.id;

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
                 left join business.almacen a on p.id = a.producto_id
                where p.status = true and a.branch_office_id = SUCURSAL_X
                group by p.id;
    end loop;
    close v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '%',sqlerrm;
END
$procedure$
;

call business.procesamiento_resumen();

--obtener venta_detalle por venta_id
select vd.*,p.nombre_comercial,case when vd.tipo_cantidad = '1' then 'Unidad' else 'Caja' end as xtipo
from business.venta_detalle vd
inner join business.producto p on p.id = vd.producto_id
where vd.venta_id = 10;

-- obtener salida entre rango de fechas, junto con usuario y distribuidor
select s.id, concat(p1.name, ' ', p1.first_lastname, ' ', p1.second_lastname) as xdistribuidor,
concat(p2.name, ' ', p2.first_lastname, ' ', p2.second_lastname) as xusuario, s.created_at,
s.total_descuento, s.total_impuesto, s.total_general
from salida s
inner join casa_vieja.public.persons p1 on p1.id = s.distribuidor_id
inner join casa_vieja.public.persons p2 on p2.id = s.created_by
where s.created_at::date between to_date('2021-08-01','YYYY-MM-DD') and to_date('2021-08-01','YYYY-MM-DD') and s.status = true;


