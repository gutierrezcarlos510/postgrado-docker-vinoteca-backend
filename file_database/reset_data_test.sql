

-- ESQUEMA BUSINESS --

-- RESET SECUENCIAS --
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
ALTER SEQUENCE  business.producto_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.tipo_producto_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.zona_id_seq
    RESTART WITH 1;
ALTER SEQUENCE  business.historico_id_seq
    RESTART WITH 1;

-- ELIMINACION DE DATOS DE LAS TABLAS BUSINESS --
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
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Configura todos los parametros iniciales para la empresa', 'style', 'Configuración empresarial', true, '#');
INSERT INTO public.menus (description, icon, "name", status, url) VALUES('Grupo de accesos del contador', 'card_travel', 'Accesos del Contador', true, '#');


INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 8);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 9);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 10);
INSERT INTO public.menu_submenu (menu_id, submenu_id) VALUES(3, 11);
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
VALUES('Venta de Vinos artesanales y sus derivados', 'casaviejatarija@gmail.com', '0', 'Casa Vieja', '0', 'Telf.:7777777 - 66-457874', 'Tarija - Bolivia', true, 2, 'http://www.casaviejablabla.com');

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
VALUES('Uvas en singani', 'Uvas en singani', 'Sin descripcion', 'Sin caracteristicas', 1, 'producto.jpg', 12, 50, 100, 1.00, 1.00, 15.00, 180.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vinos 60ml', 'Vinos minis 60ml', 'Sin descripcion ', 'Sin caracteristica', 1, 'producto.jpg', 20, 50, 100, 1.00, 1.00, 10.00, 200.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vinos 300 cc', 'Vinos medianos 300 cc', 'Sin descripcion', 'Sin caracteristica', 1, 'producto.jpg', 12, 20, 60, 1.00, 1.00, 20.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Mistela', 'Mistela', 'Dulce', 'Mistela dulce', 1, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 50.00, 300.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de uva', 'Licor de uva', 'Dulce', 'Licor dulce', 1, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Licor de membrillo', 'Licor de membrillo', 'dulce', 'Licor ddulce', 1, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 40.00, 240.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Sigani pletorico', 'Singani pletorico', 'Sin descripcion', 'Sin caracteristica', 1, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino 5 santos', '5 santos', 'Seco', 'Vino seco', 3, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 45.00, 270.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino ugni blanc aventurado', 'Ugni blanc aventurado', 'Seco', 'Vino seco', 3, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 35.00, 210.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino chardonnay asr', 'Chardonnay asr', 'Seco', 'Vino seco', 3, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 60.00, 360.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino cabernet asuvignon shn', 'Cabernet asuvignon shn', 'seco', 'vino seco', 3, 'producto.jpg', 6, 50, 100, 6.00, 6.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino tannat gran victoria', 'Tannat gran victoria', 'Seco', 'vino seco', 3, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 80.00, 480.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino oporto rosado', 'Rosado oporto', 'dulce', 'vino dulce rosado', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco oporto', 'Blanco oporto', 'dulce', 'vino dulce blanco', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto choelro', 'Tinto cholero', 'semidulce', 'vino semidulce tinto', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino rosado cholero', 'Rosado cholero', 'semidulce', 'vino semidulce cholero', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco cholero', 'Blanco cholero', 'semidulce', 'vino semidulce blanco', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto mellicero', 'Tinto mellicero', 'semiseco', 'vino semiseco tinto', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 50.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('Vino blanco mellicero', 'Blanco mellicero', 'semiseco', 'vino semiseco blanco', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino tinto aspero', 'Tinto aspero', 'seco', 'vino seco tinto', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
INSERT INTO business.producto
(nombre_generico, nombre_comercial, descripcion, caracteristica, categoria_id, foto, unidad_por_caja, stock_medio, stock_alto, pc_unit, pc_caja, pv_unit, pv_caja, pv_unit_descuento, pv_caja_descuento, pv_unit_promo, pv_caja_promo, status, presentacion_unit_id, presentacion_caja_id)
VALUES('vino blanco aspero', 'Blanco aspero', 'seco', 'vino seco blanco', 2, 'producto.jpg', 6, 50, 100, 1.00, 1.00, 25.00, 150.00, 0.00, 0.00, 0.00, 0.00, true, 1, 2);
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
VALUES(6, 'luchito', 'avatar1.png', 'luchitolazcano30@gmail.com', true, 'ADMIN', 'llazcano', NULL);

INSERT INTO public.systems_users
(id, alias, avatar, email, status, type_system_user, username, celular)
VALUES(7, 'CLopez', 'avatar2.png', 'lcinthia040@gmail.com', true, 'USER', 'clopez', NULL);

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
