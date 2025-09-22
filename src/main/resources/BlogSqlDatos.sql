USE blog;
/*
La primary key es autoincremental, ahora mismo estos registros se crearan
infinitamente a no ser que se utilice un trigger o ottra sentencia
INSERT INTO usuarios (nombre, contraseña) VALUES 
('invitado','invitado'),
('administrador','1234');
*/


INSERT INTO usuarios (id_Usuario ,nombre, contraseña) VALUES 
(1,'invitado','invitado'),
(2,'administrador','1234'),
(3,'TODOMAYUSCULAS','TODOMAYUSCULAS')
;


SELECT  *  FROM usuarios;

INSERT INTO publicaciones (id_Publicaciones ,creador, titulo, contenido,fecha_Creacion) VALUES 
(1, 2,'Título cualquiera número 1','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.', '2008-11-11'),
(2, 2, 'Título cualquiera número 2','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.', '2025-06-06'),
(3, 2,'Título cualquiera número 3','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.', '2017-11-29');

INSERT INTO publicaciones VALUES
(4, 2, 'Título cualquiera número 4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.', NULL);


















-- id_Publicaciones ,creador, titulo, contenido,fecha_Creacion
SELECT  *  FROM publicaciones;
/*
Insert Into publicaciones (creador, titulo, contenido, fecha_Creacion) VALUE
(2,'Java prueba','adadasdsadsadasdasdsadsadsadasdasdsadasdsadsadsa', NULL);
SELECT  *  FROM publicaciones;
*/

/* Omitimos el auto incrementar y la fecha
INSERT INTO Publicaciones (creador, titulo, contenido)
VALUES (1, 'Primera publicación', 'Este es el contenido del post.');
*/
/*
DELETE FROM publicaciones
where fecha_Creacion IS NULL;
*/
SELECT titulo, contenido, fecha_Creacion from publicaciones;
SELECT COUNT(*) FROM usuarios WHERE nombre = 'administrador' AND contraseña = '1234';
SELECT * from usuarios;

SELECT * FROM publicaciones LIMIT 1;
SELECT * FROM publicaciones where id_Publicaciones = 1;


DELETE FROM publicaciones
where id_Publicaciones = 6;




SELECT * FROM usuarios;

SELECT id_Usuario FROM usuarios WHERE nombre = 'invitado';


SELECT * FROM usuarios WHERE nombre = 'administrador' AND contraseña = '1';

select * from publicaciones;

UPDATE publicaciones SET titulo = "titulo actualizado ", contenido = 'asa' where id_publicaciones = 4 ;


INSERT INTO publicaciones (creador, titulo, contenido) VALUE
(2,'texto de prueba', ' contenido de prueba');
  



