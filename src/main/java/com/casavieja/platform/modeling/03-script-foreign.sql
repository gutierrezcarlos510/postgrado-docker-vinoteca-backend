/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS
 * Created: 07-mar-2020
 */
ALTER TABLE user_rol ADD CONSTRAINT user_rol_pkey PRIMARY KEY (system_user_id, rol_id);
ALTER TABLE menu_submenu ADD CONSTRAINT menu_submenu_pkey PRIMARY KEY (menu_id, submenu_id);
ALTER TABLE rol_menu ADD CONSTRAINT rol_menu_pkey PRIMARY KEY (rol_id, menu_id);
ALTER TABLE rol_task ADD CONSTRAINT rol_task_pkey PRIMARY KEY (rol_id, "task_id");
ALTER TABLE access_keys ADD CONSTRAINT access_keys_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES systems_users (system_user_id);
ALTER TABLE branch_offices ADD CONSTRAINT branch_office_company_id_fkey FOREIGN KEY (company_id) REFERENCES companies (company_id);
ALTER TABLE managements ADD CONSTRAINT managements_branch_office_id_fkey FOREIGN KEY (branch_office_id) REFERENCES branch_offices (branch_office_id);
ALTER TABLE menu_submenu ADD CONSTRAINT menu_submenu_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES menus (menu_id);
ALTER TABLE menu_submenu ADD CONSTRAINT menu_submenu_submenu_id_fkey FOREIGN KEY (submenu_id) REFERENCES submenus (submenu_id);
ALTER TABLE parameters_systems ADD CONSTRAINT parameters_systems_module_system_id_fkey FOREIGN KEY (module_system_id) REFERENCES modules_systems (module_system_id);
ALTER TABLE rol_menu ADD CONSTRAINT rol_menu_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES menus (menu_id);
ALTER TABLE rol_menu ADD CONSTRAINT rol_menu_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES roles (rol_id);
ALTER TABLE rol_task ADD CONSTRAINT rol_task_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES roles (rol_id);
ALTER TABLE rol_task ADD CONSTRAINT rol_task_task_id_fkey FOREIGN KEY (task_id) REFERENCES tasks (task_id);
ALTER TABLE user_rol ADD CONSTRAINT user_rol_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES roles (rol_id);
ALTER TABLE user_rol ADD CONSTRAINT user_rol_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES systems_users (system_user_id);
ALTER TABLE systems_users ADD CONSTRAINT systems_users_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES persons (person_id);
ALTER TABLE tasks ADD CONSTRAINT tasks_task_controller_id_fkey FOREIGN KEY (task_controller_id) REFERENCES tasks_controllers (task_controller_id);
ALTER TABLE tasks_controllers ADD CONSTRAINT tasks_controllers_module_system_id_fkey FOREIGN KEY (module_system_id) REFERENCES modules_systems (module_system_id);
ALTER TABLE users_managements ADD CONSTRAINT users_managements_management_id_fkey FOREIGN KEY (management_id) REFERENCES managements (management_id);
ALTER TABLE users_managements ADD CONSTRAINT users_managements_system_user_id_fkey FOREIGN KEY (system_user_id) REFERENCES systems_users (system_user_id);
ALTER TABLE values_primary ADD CONSTRAINT values_primary_parameter_system_id_fkey FOREIGN KEY (parameter_system_id) REFERENCES parameters_systems (parameter_system_id);
ALTER TABLE values_secondary ADD CONSTRAINT values_secondary_value_primary_id_fkey FOREIGN KEY (value_primary_id) REFERENCES values_primary (value_primary_id);