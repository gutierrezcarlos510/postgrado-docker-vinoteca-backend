/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS
 * Created: 07-mar-2020
 */

INSERT INTO persons VALUES (1, '00000', 'GUTIERREZ', 'M', 'CARLOS FRANZ', 'GUTIERREZ', 't');
INSERT INTO systems_users VALUES (1, 'cgutierrez7167968', 'not_image.png', 'gutierrezcarlos510@gmail.com', 't', 'ROOT', 'carlos7167968');
INSERT INTO access_keys VALUES (1, 't', 1, 'USER_PASS', 'carlos7167968');
INSERT INTO companies VALUES (1, 'Sistemas Tecnologicos para Bolivia', 'systecbol@gmail.com', '0', 'SysTecBol', '0', '75136609', 'La Paz - Bolivia', 't', 1, 'PROPERTY', 'http://www.sistecbol.com');
INSERT INTO companies VALUES (2, 'Empresa de Contabilidad Bolivia Prueba', 'systecbol_prueba@gmail.com', '0', 'Bolivia Prueba', '0', '75136609', 'La Paz - Bolivia', 't', 1, 'CLIENT', 'http://www.sistecbol.com');
INSERT INTO branch_offices VALUES (1, 'Zona Central, Calle El Prado', 1, 'Frente a Pollos Copacabana', 'Sucursal 1', 't');
INSERT INTO managements VALUES (1, 1, NULL, '2020-03-07', 't', 2020);
INSERT INTO submenus VALUES (1, 'Configuracion general del sistema', 'fa fa-tag', 'Configuracion del sistema', 't', 'configuracion/gestion');
INSERT INTO menus VALUES (1, 'Administracion general', 'fa fa-tag', 'Administracion de sistema', 't', '#');
INSERT INTO roles VALUES (1, 'Super usuario, con derecho a todo', 'fa fa-user', 'Super Usuario', 't');
INSERT INTO roles VALUES (2, 'Administrador del sistema', 'fa fa-linux', 'Administrador del Sistema', 't');
INSERT INTO roles VALUES (3, 'Cliente de prueba', 'fa fa-users', 'Cliente demo', 't');
INSERT INTO modules_systems VALUES (1, 'PLATFORM', 'Modulo de plataforma del sistema', 'Modulo Plataforma', 't');
INSERT INTO parameters_systems VALUES (1, 'Clasifica los tipos de empresa', 1, 'TYPE_COMPANY', 't');
INSERT INTO values_primary VALUES (1, 1, 't', 'Sociedad de Responsabilidad Limitada', '1');
INSERT INTO values_primary VALUES (2, 1, 't', 'Sociedad Limitada de FormaciÃ³n Sucesiva', '2');
INSERT INTO values_secondary VALUES (1, 'sigla', 't', 'String', 'S.R.L.', 1);
INSERT INTO values_secondary VALUES (2, 'sigla', 't', 'String', 'S.L.F.S.', 2);
INSERT INTO tasks_controllers VALUES (1, 'Controla todas las tareas para loguearse y recuperacion', 1, 'Login', 't');
INSERT INTO tasks VALUES (1, 'Resetear contraseÃ±a de usuario', 'Reset Pass', 't', 1, 'login/resetPass');
INSERT INTO rol_task VALUES (1, 1);
INSERT INTO user_rol VALUES (1, 1);
INSERT INTO users_managements VALUES (1, 1, 't', 1, 'READ_WRITE');