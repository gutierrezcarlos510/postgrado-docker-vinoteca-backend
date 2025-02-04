/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS
 * Created: 07-mar-2020
 */

CREATE TABLE VALUES_PRIMARY (value_primary_id int8, parameter_system_id int8, status boolean, title varchar(150), value_id varchar(50), PRIMARY KEY (value_primary_id));
CREATE TABLE VALUES_SECONDARY (value_secondary_id int8, name varchar(50), status boolean, type_attr varchar(25), value_attr varchar(500), value_primary_id int8, PRIMARY KEY (value_secondary_id));
CREATE TABLE SUBMENUS (submenu_id int4, description varchar(150), icon varchar(50), name varchar(50), status boolean, url varchar(50), PRIMARY KEY (submenu_id));
CREATE TABLE PERSONS (person_id int8, ci varchar(25), first_lastname varchar(100), gender varchar(1), name varchar(100), second_lastname varchar(100), status boolean, PRIMARY KEY (person_id));
CREATE TABLE ACCESS_KEYS (access_key_id int8, status boolean, system_user_id int8, type_access varchar(15), value_access varchar(1024), PRIMARY KEY (access_key_id));
CREATE TABLE BRANCH_OFFICES (branch_office_id int4, address varchar(250), company_id int4, description varchar(500), name varchar(100), status boolean, PRIMARY KEY (branch_office_id));
CREATE TABLE MENUS (menu_id int4, description varchar(150), icon varchar(50), name varchar(50), status boolean, url varchar(50), PRIMARY KEY (menu_id));
CREATE TABLE USERS_MANAGEMENTS (user_management_id int8, management_id int4, status boolean, system_user_id int8, type_operation varchar(15), PRIMARY KEY (user_management_id));
CREATE TABLE TASKS_CONTROLLERS (task_controller_id int4, description varchar(150), module_system_id int4, name varchar(50), status boolean, PRIMARY KEY (task_controller_id));
CREATE TABLE MODULES_SYSTEMS (module_system_id int4, code_module varchar(25), description varchar(150), name varchar(50), status boolean, PRIMARY KEY (module_system_id));
CREATE TABLE PARAMETERS_SYSTEMS (parameter_system_id int8, description varchar(150), module_system_id int4, name varchar(50), status boolean, PRIMARY KEY (parameter_system_id));
CREATE TABLE TASKS (task_id int4, description varchar(150), name varchar(50), status boolean, task_controller_id int4, url varchar(50), PRIMARY KEY (task_id));
CREATE TABLE SYSTEMS_USERS (system_user_id int8, alias varchar(25), avatar varchar(50), email varchar(100), status boolean, type_user_system varchar(5), username varchar(50), PRIMARY KEY (system_user_id));
CREATE TABLE ROLES (rol_id int4, description varchar(150), icon varchar(50), name varchar(50), status boolean, PRIMARY KEY (rol_id));
CREATE TABLE COMPANIES (company_id int4, description varchar(250), email varchar(100), fax varchar(50), name varchar(100), nit varchar(25), phone varchar(100), place varchar(100), status boolean, type_company int8, type_system_company varchar(10), web_page varchar(100), PRIMARY KEY (company_id));
CREATE TABLE MANAGEMENTS (management_id int4, branch_office_id int4, end_date date, start_date date, status boolean, year_number int2, PRIMARY KEY (management_id));
CREATE TABLE menu_submenu (menu_id int4 NOT NULL,submenu_id int4 NOT NULL);
CREATE TABLE rol_menu (rol_id int4 NOT NULL,menu_id int4 NOT NULL);
CREATE TABLE rol_task (rol_id int4 NOT NULL,task_id int4 NOT NULL);
CREATE TABLE user_rol (system_user_id int8 NOT NULL,rol_id int4 NOT NULL);