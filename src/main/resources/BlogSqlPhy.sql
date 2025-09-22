
DROP DATABASE IF EXISTS Blog;
CREATE DATABASE Blog;
USE Blog;

DROP TABLE IF EXISTS Usuarios;
CREATE TABLE Usuarios(
id_Usuario INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(40) UNIQUE NOT NULL,
contraseña VARCHAR(40) NOT NULL
);

DROP TABLE if EXISTS Publicaciones;
CREATE TABLE Publicaciones(
id_Publicaciones INT AUTO_INCREMENT PRIMARY KEY,
creador INT,
titulo VARCHAR(50) NOT NULL,
contenido TEXT NOT NULL,
fecha_Creacion DATE,
-- Una opción distinta hacer un trigger es  fecha_Creacion DATE DEFAULT CURRENT_DATE
FOREIGN KEY (creador) REFERENCES Usuarios (id_Usuario)
);




DELIMITER $$
DROP TRIGGER IF EXISTS revision_publicaciones_fecha$$
CREATE TRIGGER revision_publicaciones_fecha BEFORE INSERT
ON Publicaciones 
FOR EACH ROW
BEGIN
  IF NEW.fecha_Creacion IS NULL THEN
        SET NEW.fecha_Creacion = CURDATE();
    END IF;
END $$
DELIMITER ;    

DELIMITER $$
DROP TRIGGER IF EXISTS letras_minisculas$$
CREATE TRIGGER letras_minisculas BEFORE INSERT
ON Usuarios 
FOR EACH ROW
BEGIN
  SET NEW.nombre = trim(LOWER(NEW.nombre))  ;
  SET NEW.contraseña = trim(LOWER(NEW.contraseña));
END $$
DELIMITER ;  









    
SHOW TRIGGERS;





















