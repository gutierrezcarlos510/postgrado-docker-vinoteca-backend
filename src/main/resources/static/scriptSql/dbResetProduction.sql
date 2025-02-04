ALTER SEQUENCE  business.almacen_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.area_producto_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.barrio_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.caracteristica_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.categoria_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.ciudad_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.cliente_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.ingreso_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.movimiento_inventario_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.producto_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.tipo_producto_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.zona_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.pedido_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.visita_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.salida_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.historico_id_seq
    RESTART WITH 1;

-- ELIMINACION DE DATOS DE LAS TABLAS BUSINESS --
delete from business.almacen_movimiento ;
delete from business.venta_subdetalle;
delete from business.venta_detalle ;
delete from business.venta;
delete from business.almacen_distribuidor;
delete from business.movimiento_inventario_detalle;
delete from business.movimiento_inventario ;
delete from business.ingreso_detalle ;
delete from business.ingreso ;
delete from business.visita;
delete from business.salida_entrega_detalle_almacen;
delete from business.salida_entrega_detalle;
delete from business.salida_entrega;
delete from business.salida_resumen;
delete from business.salida;
delete from business.log_movimiento_almacen ;
delete from business.almacen ;
delete from business.pedido_detalle;
delete from business.pedido;
delete from business.historico_inventario;
delete from business.producto;
delete from business.cliente;
delete from business.caracteristica ;
delete from business.barrio;
delete from business.zona;
delete from business.ciudad;
delete from business.categoria_producto;
delete from business.tipo_producto;
delete from business.area_producto;

-- ESQUEMA PUBLIC - PLATAFORMA --

--RESET SECUENCIAS DE PUBLIC PLATAFORMA
ALTER SEQUENCE public.access_key_seq
    RESTART WITH 1;
ALTER SEQUENCE public.branch_office_seq
    RESTART WITH 1;
ALTER SEQUENCE public.code_verify_seq
    RESTART WITH 1;
ALTER SEQUENCE public.company_seq
    RESTART WITH 1;
ALTER SEQUENCE public.management_seq
    RESTART WITH 1;
ALTER SEQUENCE public.menu_seq
    RESTART WITH 1;
ALTER SEQUENCE public.module_system_seq
    RESTART WITH 1;
ALTER SEQUENCE public.notification_seq
    RESTART WITH 1;
ALTER SEQUENCE public.person_seq
    RESTART WITH 1;
ALTER SEQUENCE public.rol_seq
    RESTART WITH 1;
ALTER SEQUENCE public.submenu_seq
    RESTART WITH 1;
ALTER SEQUENCE public.task_controller_seq
    RESTART WITH 1;
ALTER SEQUENCE public.task_seq
    RESTART WITH 1;

-- ELIMINACION DE LOS DATOS DE PUBLIC --
delete from public.access_keys;
delete from public.notifications;
delete from public.users_managements;
delete from public.managements;
delete from public.branch_offices;
delete from public.companies;
delete from public.user_rol;
delete from public.systems_users;
delete from public.persons;
delete from public.rol_task;
delete from public.tasks;
delete from public.tasks_controllers;
delete from public.modules_systems;
delete from public.rol_acceso ;
delete from public.rol_menu ;
delete from public.menu_submenu ;
delete from public.submenus ;
delete from public.menus ;
delete from public.roles;

-- ADICION DE INFORMACION AL ESQUEMA PUBLIC --
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(20, 'Gestiona los usuarios del sistema', 'account_circle', 'Usuarios Sucursal', true, '../../main/usuarioSucursal');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(16, 'Gestiona los pedidos que realizan los clientes', 'request_quote', 'Pedidos', true, '../business/pedido');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(17, 'Gestiona las salidas de los distribuidores a sus rutas respectivas', 'local_shipping', 'Salidas', true, '../business/salida');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(18, 'Gestiona las ventas en el sistema de un distribuidor', 'shopping_cart_checkout', 'Mis Ventas', true, '../business/ventaDistribuidor');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(19, 'Administra todos los reportes que existen en el sistema', 'print', 'Reportes', true, '../business/reporte');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(15, 'Gestiona los movimientos de inventario', 'sync_alt', 'Movimientos', true, '../../business/movimientoInventario');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(9, 'Gestiona las ciudades, zonas y barrios', 'person_pin_circle', 'Lugares', true, '../../business/ciudad');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(8, 'Gestiona las caracteristicas que se usan en la empresa', 'widgets', 'Caracteristicas', true, '../../business/caracteristica');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(10, 'Gestiona los clientes de la empresa', 'emoji_people', 'Clientes', true, '../../business/cliente');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(11, 'Gestiona las categorias de productos', 'category', 'Categoria', true, '../../business/categoriaProductoGestion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(12, 'Muestra de todos los productos que ofrece la empresa', 'liquor', 'Catalogo de productos', true, '../../business/producto');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(13, 'Gestiona el inventario de productos en la sucursal', 'inventory', 'Inventario de Sucursal', true, '../../business/inventario');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(14, 'Gestiona los ingresos de productos a la sucursal', 'real_estate_agent', 'Ingresos', true, '../../business/ingreso');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(7, 'Agrupa los usuarios del sistema', 'account_circle', 'Usuarios', true, '../../main/usuarioGestion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(5, 'Controla los diferentes tipo de accesos ', 'admin_panel_settings', 'Control de accesos', true, '../../main/controlDeAccesos');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(6, 'Configuracion general del sistema.', 'settings', 'Configuracion del sistema', true, '../../main/configuracion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(1, 'Gestiona los submenus', 'menu_open', 'Submenus', true, '../../main/submenuGestion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(2, 'Roles del sistema', 'work', 'Roles', true, '../../main/rolGestion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(3, 'Menus del sistema', 'list', 'Menus', true, '../../main/menuGestion');
INSERT INTO public.submenus
(id, description, icon, "name", status, url)
VALUES(4, 'Agrupa las empresas del sistema', 'store', 'Empresa', true, '../../main/empresaGestion');

INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Grupo de accesos para controlar el sistema', 'manage_accounts', 'Control de Sistema', true, '#');
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Controla las transacciones del sistema', 'local_convenience_store', 'Transacciones', true, '#');
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Configura todos los parametros iniciales para la empresa', 'style', 'Configuración empresarial', true, '#');
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Accesos del distribuidor', 'hail', 'Accesos Distribuidor', true, '#');
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Grupo de accesos del contador', 'card_travel', 'Accesos del Contador', true, '#');


INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 13);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 14);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 15);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 16);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 17);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 18);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(2, 19);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 8);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 9);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 10);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 11);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 12);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(4, 18);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(1, 1);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(1, 2);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(1, 3);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(1, 7);
INSERT INTO public.menu_submenu (menu_id,submenu_id)VALUES (3,20);

INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Super usuario, con derecho a todo', 'warning', 'Super Usuario', true, 'ROLE_ROOT');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Administrador del sistema', 'warning', 'Administrador del Sistema', true, 'ROLE_ADMIN');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Encargado de una empresa', 'info', 'Gerente', true, 'ROLE_GERENT');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Trabajador que ofrece sus servicios', 'fa fa-adn', 'Distribuidor', true, 'ROLE_WORKER');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Gestionas las cuentas contables', 'arrow_upward', 'Contador(a)', true, 'ROLE_USER');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Moderador del sistema', 'fa fa-linux', 'Moderador', true, 'ROLE_USER');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Cliente final que busca servicios', 'center_focus_weak', 'Cliente', true, 'ROLE_CLIENT');
INSERT INTO public.roles (description, icon, "name", status, authority) VALUES('Grupo de personas externas a la empresa que puede ver la pagina web', 'warning', 'Público General', true, 'ROLE_PUBLIC');

INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(1, 1);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(1, 2);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(1, 3);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(3, 2);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(3, 3);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(4, 4);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(2, 1);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(2, 2);
INSERT INTO public.rol_menu (rol_id, menu_id) VALUES(2, 3);

INSERT INTO public.persons
(ci, first_lastname, gender, "name", second_lastname, status, codigo_celular, numero_celular)
VALUES('0000000', 'GENERAL', 'M', 'USUARIO', '', true, null, null),
      ('0000000', 'SYSTEM', 'M', 'ADMIN', 'PRO', true, null, null);

INSERT INTO public.systems_users (id, alias, avatar, email, status, type_system_user, username, celular)
VALUES(2, 'admin7167968', 'avatar_1.png', 'admin@sibol.net', true, 'ROOT', 'admin', NULL);

INSERT INTO public.user_rol (system_user_id, rol_id) VALUES(2, 1);
INSERT INTO public.access_keys (status, system_user_id, type_access, value_access, is_verified_code, code_verification)
VALUES(true, 2, 'USER_PASS', '$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32', true, NULL);

INSERT INTO public.companies (description, email, fax, "name", nit, phone, place, status, type_company_id, web_page)
VALUES('Venta de Vinos artesanales y sus derivados', 'infosystem.tarija@gmail.com', '0', 'Casa Vieja', '0', '7777777', 'Tarija - Bolivia', true, 2, 'http://www.casavieja.com');

INSERT INTO public.branch_offices (address, company_id, description, "name", status)
VALUES('Ciudad el Valle', 1, 'A unas cuadras de la plaza principal', 'Casa Matriz', true);

INSERT INTO public.managements (branch_office_id, end_date, start_date, status, year_number)
VALUES(1, '2024-12-31', '2024-01-01', true, 2024);

INSERT INTO public.users_managements (user_management_id, management_id, status, system_user_id, type_operation)
VALUES(1, 1, true, 2, 'READ_WRITE');

-- ADICION DE DATOS PARA ESQUEMA BUSINESS --

INSERT INTO business.caracteristica(nombre, tipo, status)
VALUES('Unidad', '1', true),('Caja', '2', true),('Cliente General', '3', true);

INSERT INTO business.area_producto
(nombre, descripcion, icono, status)
VALUES('Area General', 'Area generica', 'fa fa-user', true);

INSERT INTO business.tipo_producto
(nombre, descripcion, status, area_producto_id)
VALUES('Tipo General', 'Todas las categorias', true, 1);

INSERT INTO business.categoria_producto
(nombre, descripcion, status, tipo_producto_id)
VALUES('Tradicionales', 'vino tradicionales', true, 1);
INSERT INTO business.categoria_producto
(nombre, descripcion, status, tipo_producto_id)
VALUES('Varietal', 'vino varietal', true, 1);
INSERT INTO business.categoria_producto
(nombre, descripcion, status, tipo_producto_id)
VALUES('Sin categoria', 'Sin categoria', true, 1);

-- SCRIPT PARA IGRESAR DATOS DE PRUEBA PARA PRODUCTOS (OPCIONAL)
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Uvas en singani', 'Uvas en singani', 'Sin descripcion', 'Sin caracteristicas', 1, 'producto.png', 12, 50, 100, 1.00, 1.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vinos 60ml', 'Vinos minis 60ml', 'Sin descripcion ', 'Sin caracteristica', 1, 'producto.png', 20, 50, 100, 1.00, 1.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vinos 300 cc', 'Vinos medianos 300 cc', 'Sin descripcion', 'Sin caracteristica', 1, 'producto.png', 12, 20, 60, 1.00, 1.00, 20.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mistela', 'Mistela', 'Dulce', 'Mistela dulce', 1, 'producto.png', 6, 50, 100, 1.00, 1.00, 50.00, 300.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de uva', 'Licor de uva', 'Dulce', 'Licor dulce', 1, 'producto.png', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de membrillo', 'Licor de membrillo', 'dulce', 'Licor ddulce', 1, 'producto.png', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Sigani pletorico', 'Singani pletorico', 'Sin descripcion', 'Sin caracteristica', 1, 'producto.png', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino 5 santos', '5 santos', 'Seco', 'Vino seco', 3, 'producto.png', 6, 50, 100, 1.00, 1.00, 45.00, 270.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino ugni blanc aventurado', 'Ugni blanc aventurado', 'Seco', 'Vino seco', 3, 'producto.png', 6, 50, 100, 1.00, 1.00, 35.00, 210.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino chardonnay asr', 'Chardonnay asr', 'Seco', 'Vino seco', 3, 'producto.png', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino cabernet asuvignon shn', 'Cabernet asuvignon shn', 'seco', 'vino seco', 3, 'producto.png', 6, 50, 100, 6.00, 6.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino tannat gran victoria', 'Tannat gran victoria', 'Seco', 'vino seco', 3, 'producto.png', 6, 50, 100, 1.00, 1.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino oporto rosado', 'Rosado oporto', 'dulce', 'vino dulce rosado', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco oporto', 'Blanco oporto', 'dulce', 'vino dulce blanco', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto choelro', 'Tinto cholero', 'semidulce', 'vino semidulce tinto', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino rosado cholero', 'Rosado cholero', 'semidulce', 'vino semidulce cholero', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco cholero', 'Blanco cholero', 'semidulce', 'vino semidulce blanco', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto mellicero', 'Tinto mellicero', 'semiseco', 'vino semiseco tinto', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 50.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino blanco mellicero', 'Blanco mellicero', 'semiseco', 'vino semiseco blanco', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto aspero', 'Tinto aspero', 'seco', 'vino seco tinto', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco aspero', 'Blanco aspero', 'seco', 'vino seco blanco', 2, 'producto.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino cholero rosado', 'Cholero rosado', 'semidulce', 'vino emidulce rosado', 2, 'producto-d2b3da36-29ed-49d8-b0b8-098604a0b3cd.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);


INSERT INTO public.persons
(id, ci, first_lastname, gender, "name", second_lastname, status, codigo_celular, numero_celular)
VALUES(6, '7112945', 'LAZCANO', 'M', 'LUIS ARMANDO', ' QUIROGA', true, NULL, NULL);

INSERT INTO public.persons
(id, ci, first_lastname, gender, "name", second_lastname, status, codigo_celular, numero_celular)
VALUES(7, '7174924', 'LÓPEZ', 'F', 'CINTHIA SOLEDAD', 'RODRIGUEZ', true, NULL, NULL);

--INSERT CIUDAD

INSERT INTO business.ciudad
( nombre, status)
VALUES( 'Tarija', true);

--INSERT ZONA
INSERT INTO business.zona
(nombre, status,  ciudad_id)
VALUES('zona centro', true,  1);

--INSERT BARRIO
INSERT INTO business.barrio
( nombre, status, zona_id)
VALUES( 'barrio 4 julio', true, 1);

--INSERT USUARIO

INSERT INTO public.systems_users
(id, alias, avatar, email, status, type_system_user, username, celular)
VALUES(6, 'luchito', 'avatar1.png', 'luchitolazcano30@gmail.com', true, 'ADMIN', 'LLazcano', NULL);

INSERT INTO public.systems_users
(id, alias, avatar, email, status, type_system_user, username, celular)
VALUES(7, 'CLopez', 'avatar2.png', 'lcinthia040@gmail.com', true, 'USER', 'CLopez', NULL);

INSERT INTO public.user_rol (system_user_id, rol_id) VALUES(6, 1);
INSERT INTO public.access_keys (status, system_user_id, type_access, value_access, is_verified_code, code_verification)
VALUES(true, 6, 'USER_PASS', '$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32', true, NULL);
INSERT INTO public.users_managements (user_management_id, management_id, status, system_user_id, type_operation)
VALUES(2, 1, true, 6, 'READ_WRITE');

INSERT INTO public.user_rol (system_user_id, rol_id) VALUES(7, 5);
INSERT INTO public.access_keys (status, system_user_id, type_access, value_access, is_verified_code, code_verification)
VALUES(true, 7, 'USER_PASS', '$2a$10$InCYoobyLIOkvSROzJw9R.1eC6fBBoZ8Rg10F/Yrr6quHG12n0M32', true, NULL);
INSERT INTO public.users_managements (user_management_id, management_id, status, system_user_id, type_operation)
VALUES(3, 1, true, 7, 'READ_WRITE');

--INSERT CLIENTE

--Este cliente siempre debe existir
INSERT INTO business.cliente
(id, alias, direccion, email, nombre_negocio, descripcion_negocio, tipo_cliente, barrio_id, status)
VALUES(1, 'Cliente General', 'Sin direccion', 'sincorreo@gmail.com', 'Sin negocio', 'Sin descripcion',3,1,true);

UPDATE business.producto SET nombre_generico='Vino cholero rosado', nombre_comercial='Cholero rosado', descripcion='semidulce', caracteristica='vino emidulce rosado', categoria_id=2, foto='producto-d64553c9-8747-437d-8544-07968812ce6f.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=22;
UPDATE business.producto SET nombre_generico='vino blanco aspero', nombre_comercial='Blanco aspero', descripcion='seco', caracteristica='vino seco blanco', categoria_id=2, foto='producto-92add2be-17f2-4043-9e3d-fc7aab486d9d.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=21;
UPDATE business.producto SET nombre_generico='vino tinto aspero', nombre_comercial='Tinto aspero', descripcion='seco', caracteristica='vino seco tinto', categoria_id=2, foto='producto-9baab288-083e-445a-8d9b-2fb68bfd853d.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=20;
UPDATE business.producto SET nombre_generico='Vino blanco mellicero', nombre_comercial='Blanco mellicero', descripcion='semiseco', caracteristica='vino semiseco blanco', categoria_id=2, foto='producto-856662b8-c902-477a-9eea-ac1903bf2dc7.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=19;
UPDATE business.producto SET nombre_generico='Vino tinto mellicero', nombre_comercial='Tinto mellicero', descripcion='semiseco', caracteristica='vino semiseco tinto', categoria_id=2, foto='producto-534535a9-4dd4-49fa-a224-c88bd519bae4.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=50.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=18;
UPDATE business.producto SET nombre_generico='vino blanco cholero', nombre_comercial='Blanco cholero', descripcion='semidulce', caracteristica='vino semidulce blanco', categoria_id=2, foto='producto-ec0903c4-a8da-4587-acf7-2c02c1f30304.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=17;
UPDATE business.producto SET nombre_generico='Uvas en singani', nombre_comercial='Uvas en singani', descripcion='Sin descripcion', caracteristica='Sin caracteristicas', categoria_id=1, foto='producto.png', unidad_por_caja=12, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=15.00, pv_caja=180.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=1;
UPDATE business.producto SET nombre_generico='Vinos 60ml', nombre_comercial='Vinos minis 60ml', descripcion='Sin descripcion ', caracteristica='Sin caracteristica', categoria_id=1, foto='producto.png', unidad_por_caja=20, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=10.00, pv_caja=200.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=2;
UPDATE business.producto SET nombre_generico='Vinos 300 cc', nombre_comercial='Vinos medianos 300 cc', descripcion='Sin descripcion', caracteristica='Sin caracteristica', categoria_id=1, foto='producto.png', unidad_por_caja=12, stock_medio=20, stock_alto=60, pc_unit=1.00, pc_caja=1.00, pv_unit=20.00, pv_caja=240.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=3;
UPDATE business.producto SET nombre_generico='Mistela', nombre_comercial='Mistela', descripcion='Dulce', caracteristica='Mistela dulce', categoria_id=1, foto='producto.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=50.00, pv_caja=300.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=4;
UPDATE business.producto SET nombre_generico='Licor de membrillo', nombre_comercial='Licor de membrillo', descripcion='dulce', caracteristica='Licor ddulce', categoria_id=1, foto='producto.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=40.00, pv_caja=240.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=6;
UPDATE business.producto SET nombre_generico='Vino 5 santos', nombre_comercial='5 santos', descripcion='Seco', caracteristica='Vino seco', categoria_id=3, foto='producto.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=45.00, pv_caja=270.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=8;
UPDATE business.producto SET nombre_generico='Vino chardonnay asr', nombre_comercial='Chardonnay asr', descripcion='Seco', caracteristica='Vino seco', categoria_id=3, foto='producto.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=60.00, pv_caja=360.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=10;
UPDATE business.producto SET nombre_generico='vino blanco oporto', nombre_comercial='Blanco oporto', descripcion='dulce', caracteristica='vino dulce blanco', categoria_id=2, foto='producto-4e688a7a-290e-4294-a491-21fed13fe4ff.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=14;
UPDATE business.producto SET nombre_generico='vino oporto rosado', nombre_comercial='Rosado oporto', descripcion='dulce', caracteristica='vino dulce rosado', categoria_id=2, foto='producto-8699a0fe-8f44-4aff-b325-76f8de70017d.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=13;
UPDATE business.producto SET nombre_generico='Vino tannat gran victoria', nombre_comercial='Tannat gran victoria', descripcion='Seco', caracteristica='vino seco', categoria_id=3, foto='producto-3c0e28b7-c7a4-491a-9208-f6c3c33d5c03.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=80.00, pv_caja=480.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=12;
UPDATE business.producto SET nombre_generico='Vino cabernet asuvignon shn', nombre_comercial='Cabernet asuvignon shn', descripcion='seco', caracteristica='vino seco', categoria_id=3, foto='producto-91798c29-adb2-487c-b4da-b3edd28e0872.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=6.00, pc_caja=6.00, pv_unit=80.00, pv_caja=480.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=11;
UPDATE business.producto SET nombre_generico='Sigani pletorico', nombre_comercial='Singani pletorico', descripcion='Sin descripcion', caracteristica='Sin caracteristica', categoria_id=1, foto='producto-0ce129aa-65bd-49ef-a19f-1b5bc1b85141.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=60.00, pv_caja=360.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=7;
UPDATE business.producto SET nombre_generico='Licor de uva', nombre_comercial='Licor de uva', descripcion='Dulce', caracteristica='Licor dulce', categoria_id=1, foto='producto-f5b32bd5-6a6e-4dfd-957f-5909a5d27bdd.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=40.00, pv_caja=240.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=5;
UPDATE business.producto SET nombre_generico='Vino ugni blanc aventurado', nombre_comercial='Ugni blanc aventurado', descripcion='Seco', caracteristica='Vino seco', categoria_id=3, foto='producto-4e9ea46b-2537-4980-bbc3-1d566dcd1a8a.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=35.00, pv_caja=210.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=9;
UPDATE business.producto SET nombre_generico='vino rosado cholero', nombre_comercial='Rosado cholero', descripcion='semidulce', caracteristica='vino semidulce cholero', categoria_id=2, foto='producto.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=16;
UPDATE business.producto SET nombre_generico='vino tinto choelro', nombre_comercial='Tinto cholero', descripcion='semidulce', caracteristica='vino semidulce tinto', categoria_id=2, foto='producto-a32ac7d4-92b7-422f-9b55-bbaf62346441.png', unidad_por_caja=6, stock_medio=50, stock_alto=100, pc_unit=1.00, pc_caja=1.00, pv_unit=25.00, pv_caja=150.00, pv_unit_descuento=0.00, pv_caja_descuento=0.00, pv_unit_promo=0.00, pv_caja_promo=0.00, status=true, presentacion_unit_id=1, presentacion_caja_id=2 WHERE id=15;