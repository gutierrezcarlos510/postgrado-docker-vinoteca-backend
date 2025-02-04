package com.casavieja.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controla los archivos que se suben al sistema
 * @author CARLOS
 *
 */
public interface UploadFileS {
	/**
	 * Obtiene el recurso para mostrar un archivo
	 * @param filePath
	 * @param filename
	 * @return
	 * @throws MalformedURLException
	 */
	Resource load(String filePath,String filename) throws MalformedURLException;
	/**
	 * Guarda un archivo
	 * @param filePath
	 * @param file
	 * @param name
	 * @return
	 * @throws IOException
	 */
	boolean save(String filePath, MultipartFile file, String name) throws IOException;
	/**
	 * Elimina un archivo
	 * @param filePath
	 * @param filename
	 * @return
	 */
	boolean delete(String filePath,String filename);
	/**
	 * Elimina todos los archivos de un directorio
	 * @param filePath
	 */
	void deleteAll(String filePath);

	String getExtensionFile(MultipartFile archivo);

	/**
	 * Archivo de configuracion cuando  se ejecuta el proyecto, creado los directorios donde se guardaran los archivos
	 * @throws IOException
	 */
	void init() throws IOException;

	String generateNameFile(MultipartFile file, String prefijo);
}
