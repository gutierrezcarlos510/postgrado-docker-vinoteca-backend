
CREATE TABLE business.prestacion_servicio (
                                              id int8 NOT NULL,
                                              fecha_registro timestamp NOT NULL,
                                              cliente_id int8 NOT NULL,
                                              titulo varchar(150) NOT NULL,
                                              descripcion varchar(1000) NOT NULL,
                                              ubicacion_geografica varchar(50) NULL,
                                              presupuesto numeric(7, 2) NOT NULL,
                                              estado_prestacion varchar(1) NOT NULL,
                                              valoracion_por_prestacion int2 NULL,
                                              comentario varchar(1000) NULL,
                                              estado boolean NOT NULL,
                                              updated_at timestamp NULL,
                                              updated_by int8 NULL,
                                              CONSTRAINT prestacion_servicio_pk PRIMARY KEY (id),
                                              CONSTRAINT prestacion_servicio_fk FOREIGN KEY (cliente_id) REFERENCES business.cliente(id)
);

-- Column comments

COMMENT ON COLUMN business.prestacion_servicio.estado_prestacion IS '0= solicitud cancelada, 1 = solicitud aceptada y en proceso, 2 = solicitud pendiente, 3= solicitud finalizada con exito, 4=Finalizado con queja';


ALTER TABLE business.prestacion_servicio ALTER COLUMN hora_trabajo TYPE varchar(150) USING hora_trabajo::varchar(150);

--20/08/2024
ALTER TABLE public.access_keys RENAME COLUMN access_key_id TO id;
ALTER TABLE public.branch_offices RENAME COLUMN branch_office_id TO id;
ALTER TABLE public.companies RENAME COLUMN company_id TO id;
ALTER TABLE public.menus RENAME COLUMN menu_id TO id;
ALTER TABLE public.modules_systems RENAME COLUMN module_system_id TO id;
ALTER TABLE public.notifications RENAME COLUMN notification_id TO id;
ALTER TABLE public.persons RENAME COLUMN person_id TO id;
ALTER TABLE public.roles RENAME COLUMN rol_id TO id;
ALTER TABLE public.submenus RENAME COLUMN submenu_id TO id;
ALTER TABLE public.systems_users RENAME COLUMN system_user_id TO id;
ALTER TABLE public.tasks RENAME COLUMN task_id TO id;
ALTER TABLE public.managements RENAME COLUMN management_id TO id;

