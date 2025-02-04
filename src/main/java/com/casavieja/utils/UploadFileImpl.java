package com.casavieja.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileImpl implements UploadFileS {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Resource load(String filePath, String filename) throws MalformedURLException {
		Path pathFoto = getPath(filePath,filename);
		Resource recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}
	@Override
	public boolean save(String filePath, MultipartFile file, String name) throws IOException {
		if (file != null && file.getSize() > 0) {
			try {
				Path rootPath = getPath(filePath, name);
//				Files.copy(file.getInputStream(), rootPath);
				Files.copy(file.getInputStream(), rootPath, StandardCopyOption.REPLACE_EXISTING);
				return true;
			} catch (Exception e) {
				throw new RuntimeException("Error al subir la imagen, ya que excedio el peso del archivo"+e.getMessage());
			}
		}
		return false;

	}

	@Override
	public boolean delete(String filePath, String filename) {
		Path rootPath = getPath(filePath, filename);
		File archivo = rootPath.toFile();
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}
	public Path getPath(String pathFile,String filename) {
		return Paths.get(pathFile).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll(String pathFile) {
		FileSystemUtils.deleteRecursively(Paths.get(pathFile).toFile());

	}
	@Override
	public String getExtensionFile(MultipartFile archivo) {
		if (archivo == null)
			return null;
		if (archivo.isEmpty())
			return null;
		return archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf('.'));
	}
	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		File fileMain = Paths.get(MyConstants.FILE_USER).toFile();
		if(!fileMain.exists())
			Files.createDirectory(Paths.get(MyConstants.FILE_USER));
	}
	@Override
	public String generateNameFile(MultipartFile file, String prefijo) {
		return prefijo + "-" + UUID.randomUUID().toString() + getExtensionFile(file);
	}
}
