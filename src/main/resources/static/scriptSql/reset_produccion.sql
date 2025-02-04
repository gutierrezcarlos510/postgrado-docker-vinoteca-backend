-- ESQUEMA BUSINESS --

-- RESET SECUENCIAS --
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
VALUES('Venta de Vinos artesanales y sus derivados', 'casaviejatarija@gmail.com', '0', 'Casa Vieja', '0', 'Telf.:7777777 - 66-457874', 'Tarija - Bolivia', true, 2, 'http://www.casavieja.com');

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

INSERT INTO categoria_producto
(id, nombre, descripcion, status, tipo_producto_id)
VALUES(12, 'Tradicional 300 CC', '300cc', true, 1);
INSERT INTO categoria_producto
(id, nombre, descripcion, status, tipo_producto_id)
VALUES(20, 'Tradicional 5L', 'Bidon 5L', true, 1);
INSERT INTO categoria_producto
(id, nombre, descripcion, status, tipo_producto_id)
VALUES(13, 'Tradicional 60ML', 'Mini 60ml', true, 1);
INSERT INTO categoria_producto
(id, nombre, descripcion, status, tipo_producto_id)
VALUES(5, 'Varietal', 'Varietal', true, 1);
INSERT INTO categoria_producto
(id, nombre, descripcion, status, tipo_producto_id)
VALUES(21, 'Tradicional 700ml', '700ml', true, 1);


-- SCRIPT PARA IGRESAR DATOS DE PRUEBA PARA PRODUCTOS (OPCIONAL)

INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Rosado 700ml', 'Oporto Rosado 700ml', 'Dulce', 'Botella 700ml', 21, 'producto-10d71381-8559-4eaa-98af-286a3baaf19e.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Blanco 700ml', 'Oporto Blanco 700ml', 'Dulce', 'Botella 700ml', 21, 'producto-42ee931c-75f3-4ded-8013-95f7c753c29c.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Tinto 5 L', 'Oporto Tinto 5 L', 'Dulce', 'Bidon 5 L', 20, 'producto.jpg', 1, 10, 100, 10.00, 10.00, 100.00, 100.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Tinto 700ml', 'Oporto Tinto 700ml', 'DUlce', 'Botella 700ml', 21, 'producto.jpg', 6, 25, 150, 30.00, 180.00, 30.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Tinto 700ml', 'Cholero Tinto 700ml', 'Semi dulce', 'Botella 700ml', 21, 'producto-384f9c3a-f682-43ab-ba47-6ef98601cffe.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Blanco 700ml', 'Cholero Blanco 700ml', 'Semi dulce', 'Botella 700ml', 21, 'producto-2216a8b2-dac4-4be3-8c1a-7df3fc84356f.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino ugni blanc aventurado', 'Ugni blanc aventurado', 'Seco', 'Vino seco', 5, 'producto-14db19f2-ce79-483f-94e4-d7f5402542e4.png', 6, 50, 100, 1.00, 1.00, 35.00, 210.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Blanco 300 CC', 'Aspero Blanco 300 CC', 'Seco', 'Botella 300 cc', 12, 'producto-d911852c-03fe-46eb-9e43-e868d5eb5264.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Rosado 700ml', 'Cholero Rosado 700ml', 'Semi dulce', 'Botella 700ml', 21, 'producto-befb9450-e6bb-4c90-8bca-6542cafe0c36.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero rosado 700ml', 'Cholero rosado 700ml', 'Semi dulce', 'Botella 700ml', 21, 'producto-cacc194a-5659-4996-9567-86a233c130fe.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Rosado 5L', 'Cholero Rosado 5L', 'Dulce', 'Bidon 5L', 20, 'producto-86c4c99b-9a0d-4a9a-a29b-7c1907f21679.png', 1, 10, 100, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Tinto 300 CC', 'Aspero Tinto 300 CC', 'Seco', 'Botella 300cc', 12, 'producto-94d52642-3bcc-49ba-8107-b1f409e37d1f.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Rosado 60 ml', 'Cholero Rosado 60 ml', 'Semi dulce', 'Mini 60ml', 13, 'producto-e0ae052f-19eb-447e-abb9-d26c3c87e31d.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Blanco 300 cc', 'Mellicero Blanco 300 cc', 'Semi seco', 'Botella 300cc', 12, 'producto-e4a4f687-8047-4506-a30c-ba348d878136.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Tinto 300cc', 'Cholero Tinto 300cc', 'Semi dulce', 'Botella 300cc', 12, 'producto-fd3fe4f2-2c62-47a2-9e79-865189e75312.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Tinto 60 ml', 'Oporto Tinto 60 ml', 'Dulce', 'Mini 60ml', 13, 'producto.jpg', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Blanco 700ml', 'Aspero Blanco 700ml', 'Seco', 'Botella 700ml', 21, 'producto-8961c11c-29d6-4da4-8e59-7b6ec77df09a.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Tinto 700ml', 'Mellicero Tinto 700ml', 'Semi seco', 'Botella 700ml', 21, 'producto-9acc8fe4-a345-431a-a487-4e7547dbd607.png', 6, 50, 100, 1.00, 1.00, 50.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Tinto 700ml', 'Aspero Tinto 700ml', 'Seco ', 'Botella 700ml', 21, 'producto-4f623ecf-dbab-498b-a3e7-bdd5f93005ea.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Blanco 700ml', 'Mellicero Blanco 700ml', 'Semi seco', 'Botella 700ml', 21, 'producto-5d374e78-9e4d-4fd3-b7e2-5e9d744f464d.png', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Rosado 5L', 'Oporto Rosado 5L', 'Dulce', 'Bidon 5L', 20, 'producto-a38b9435-d7af-4fae-90ff-d8b79c404626.png', 1, 10, 100, 130.00, 130.00, 130.00, 130.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Blanco5L', 'Oporto Blanco5L', 'Dulce', 'Bidon 5L', 21, 'producto-639ed110-4add-4337-bca3-6089587159c4.png', 1, 10, 100, 130.00, 130.00, 130.00, 130.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Tinto 5L', 'Cholero Tinto 5L', 'Semi dulce', 'Bidon 5L', 20, 'producto-2deb5012-2c32-49da-8bbc-9fe5bf2105ca.png', 1, 10, 100, 120.00, 120.00, 120.00, 120.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Blanco 5L', 'Cholero Blanco 5L', 'Dulce', 'Bidon 5L', 20, 'producto-58d1fd9f-deba-4e7a-96be-d70f82868316.png', 1, 10, 100, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Blanco 5L', 'Mellicero Blanco 5L', 'Semi seco', 'Bidon 5L', 20, 'producto-eac9bff3-ab9c-40d2-ac38-6a3b12daad8d.png', 1, 10, 100, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino tannat gran victoria', 'Tannat gran victoria', 'Seco', 'vino seco', 5, 'producto-4d894036-ae04-4ba8-9d9c-0d2d3e96cfd4.png', 6, 50, 100, 1.00, 1.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Uvas en singani', 'Uvas en singani', 'Sin descripcion', 'Sin caracteristicas', 5, 'producto.jpg', 12, 50, 100, 1.00, 1.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mistela', 'Mistela', 'Dulce', 'Mistela dulce', 5, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 50.00, 300.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de membrillo', 'Licor de membrillo', 'dulce', 'Licor ddulce', 5, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino 5 santos', '5 santos', 'Seco', 'Vino seco', 5, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 45.00, 270.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino chardonnay asr', 'Chardonnay asr', 'Seco', 'Vino seco', 5, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino cabernet asuvignon shn', 'Cabernet asuvignon shn', 'seco', 'vino seco', 5, 'producto-ffe2a5d8-9bf6-4520-8074-93256175238f.png', 6, 50, 100, 6.00, 6.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Sigani pletorico', 'Singani pletorico', 'Sin descripcion', 'Sin caracteristica', 5, 'producto-1e89c9d2-37a7-45fc-8512-86f73ccd23b7.png', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de uva', 'Licor de uva', 'Dulce', 'Licor dulce', 5, 'producto-cfbaa7fb-d2d0-414a-b8e7-f1e0a49e6d18.png', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Tinto 300 CC', 'Oporto Tinto 300 CC', 'Dulce', 'Botella 300 cc', 12, 'producto.jpg', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Blanco 300 cc', 'Oporto Blanco 300 cc', 'Dulce', 'Botella 300 cc', 12, 'producto-a0510fab-a62d-40ee-ac06-5c4963f6187e.png', 12, 10, 100, 10.00, 180.00, 100.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Rosado 300 cc', 'Oporto Rosado 300 cc', 'Dulce', 'Botella 300 c', 12, 'producto-04f954a9-b8c9-4c38-9512-6fe60acbf482.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Rosado 300 cc', 'Cholero Rosado 300 cc', 'Semi dulce', 'Botella 300 cc', 12, 'producto-cb7dbf4b-bd28-4da4-9a13-977daf172257.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Tinto 300 cc', 'Mellicero Tinto 300 cc', 'Semi seco', 'Botella 300 cc', 12, 'producto-ab78046e-ada8-4dc3-aa3f-1c50b66d4291.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Blanco 300 cc', 'Cholero Blanco 300 cc', 'Semi dulce', 'Botella 300 cc', 12, 'producto-a0d15182-19da-43ab-9005-249728d25d1c.png', 12, 10, 100, 15.00, 180.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Blanco 300 cc', 'Aspero Blanco 300 cc', 'Seco', 'Botella 300 cc', 12, 'producto-1af222cc-e0d0-44d6-b4ed-97a40de119c2.png', 12, 20, 60, 1.00, 1.00, 20.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Rosado 60ml', 'Oporto Rosado 60ml', 'Dulce', 'Mini 60 ml', 13, 'producto-4bb13baf-d880-4bfb-acc0-54ec68840a3a.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Oporto Blanco 60ml', 'Oporto Blanco 60ml', 'Dulce', 'Mini 60 ml', 13, 'producto-34552f2e-3d77-4d82-8474-6ae58756f3af.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Cholero Tinto 60ml', 'Cholero Tinto 60ml', 'Semi dulce', 'Mini 60 ml', 13, 'producto-55746866-0f03-49a6-b9f5-dffca7362b5a.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Tinto 60ml', 'Mellicero Tinto 60ml', 'Semi seco', 'Mini 60 ml', 13, 'producto-79e80f93-66a2-4780-95de-da6e1a49bdc7.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Blanco 60ml', 'Mellicero Blanco 60ml', 'Semi seco', 'Mini 60 ml', 13, 'producto-8fc62867-bf15-41ea-9332-aa20238f3e79.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Tinto 60 ml', 'Aspero Tinto 60 ml', 'Seco', 'Mini 60 ml', 13, 'producto-cd65ae7c-98f0-45fb-b84f-f27f1a2452f2.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Blanco 60 ml', 'Aspero Blanco 60 ml', 'Seco', 'Mini 60 ml', 13, 'producto-448aec20-7d53-4fc7-8db7-41bc92052947.png', 20, 10, 100, 10.00, 200.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Tinti 5L', 'Aspero Tinti 5L', 'Seco', 'Bidon 5L', 20, 'producto-04011eec-79b6-4409-a810-3e660f0e11dd.png', 1, 10, 100, 120.00, 120.00, 120.00, 120.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mellicero Tinto 5L', 'Mellicero Tinto 5L', 'Semi seco', 'Bidon 5L', 20, 'producto-a5f6d980-a1df-4d43-98cf-3cb6a4c0481d.png', 1, 10, 100, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Aspero Blanco 5L', 'Aspero Blanco 5L', 'Seco', 'Bidon 5L', 20, 'producto-bd5d00b7-0127-4326-af48-7161b6b07c1f.png', 1, 10, 100, 120.00, 120.00, 120.00, 120.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vinos 60ml', 'Vinos minis 60ml', 'Sin descripcion ', 'Sin caracteristica', 13, 'producto.jpg', 20, 50, 100, 1.00, 1.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);

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

