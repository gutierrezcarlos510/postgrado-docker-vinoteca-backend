/**
 * 
 */
package com.casavieja.business.services;

import com.casavieja.business.dao.ProductoDao;
import com.casavieja.business.dto.form.ProductoForm;
import com.casavieja.business.entities.ProductoEntity;
import com.casavieja.business.mappers.ProductoMapper;
import com.casavieja.business.model.ProductoM;
import com.casavieja.pagination.DataTableResults;
import com.casavieja.pagination.SqlBuilder;
import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UploadFileS;
import com.casavieja.utils.UtilDataTableS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
public class ProductoImpl implements ProductoS {

	private final ProductoDao productoDao;
	private final UtilDataTableS utilDataTableS;
	private final ProductoMapper productoMapper;
	private final UploadFileS uploadFileS;

	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductoEntity>> listActive() {
		List<ProductoEntity> lista = productoDao.findByStatusTrue();
		return ResponseEntity.ok(lista);
	}

	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProductoEntity>> findByCategoria(Short categoriaId) {
		List<ProductoEntity> lista = productoDao.findByCategoriaIdAndStatusTrue(categoriaId);
		return ResponseEntity.ok(lista);
	}

	
	@Override
	@Transactional(readOnly = true)
	public DataTableResults<ProductoM> list(HttpServletRequest request) {
		SqlBuilder sql = new SqlBuilder();
		sql.setSelect("p.*, c.nombre as xcategoria");
		sql.setFrom("business.producto p");
		sql.addJoin("business.categoria_producto c on c.id = p.categoria_id");
		sql.setWhere("p.status = true");
		return utilDataTableS.list(request, ProductoM.class, sql);
	}

	
	@Override
	@Transactional
	public ProductoEntity save(ProductoEntity value) {
		return productoDao.save(value);
	}

	@Override
	@Transactional
	public ProductoEntity save(ProductoForm value) {
		try {
			setNameFotoIfExist(value);
			value.setStatus(true);
			ProductoEntity producto = save(productoMapper.toEntity(value));
			if(!isDefaultProductImage(producto.getFoto())) {
				uploadFileS.save(MyConstants.RUTA_CONTROLLER_PRODUCTO, value.getArchivos().get(0), producto.getFoto());
			}
			return producto;
		} catch (IOException e) {
			throw new RuntimeException("Fallo al subir archivo:" + e.getMessage());
		}
	}

	private static boolean isDefaultProductImage(String image) {
		return image != null && image.equals(MyConstants.PRODUCTO_DEFAULT);
	}

	/**
	 * Asigna el nombre del archivo a la propiedad foto de la entidad. Si no hay
	 * archivos se asigna el valor por defecto.
	 *
	 * @param value
	 *            Entidad con la propiedad que se va a asignar.
	 * @throws IOException
	 *             Error al asignar el nombre del archivo.
	 */
	private void setNameFotoIfExist(ProductoForm value) throws IOException {
		if (value.getArchivos() != null && !value.getArchivos().isEmpty()) {
			for (int i = 0; i < value.getArchivos().size(); i++) {
				if(!value.getArchivos().get(i).isEmpty()) {
					String nameArchivo = uploadFileS.generateNameFile(value.getArchivos().get(i), "producto");
					value.setFoto(nameArchivo);
				} else {
					value.setFoto(MyConstants.PRODUCTO_DEFAULT);
				}
			}
		} else {
			value.setFoto(MyConstants.PRODUCTO_DEFAULT);
		}
	}

	@Override
	public ProductoEntity findById(Integer productoId) {
        return productoDao.findById(productoId).orElseThrow(()-> new RuntimeException("No existe Id"));
	}

	
	@Override
	@Transactional
	public ProductoEntity update(Integer productoId, ProductoEntity value) {
		ProductoEntity productoDB = productoDao.findById(productoId).orElseThrow(()-> new RuntimeException("No existe ID"));
		productoDB.setNombreGenerico(value.getNombreGenerico());
		productoDB.setNombreComercial(value.getNombreComercial());
		productoDB.setDescripcion(value.getDescripcion());
		productoDB.setCaracteristica(value.getCaracteristica());
		productoDB.setCategoriaId(value.getCategoriaId());
		productoDB.setFoto(value.getFoto());
		productoDB.setUnidadPorCaja(value.getUnidadPorCaja());
		productoDB.setStockMedio(value.getStockMedio());
		productoDB.setStockAlto(value.getStockAlto());
		productoDB.setPcUnit(value.getPcUnit());
		productoDB.setPcCaja(value.getPcCaja());
		productoDB.setPvUnit(value.getPvUnit());
		productoDB.setPvCaja(value.getPvCaja());
		productoDB.setPvUnitDescuento(value.getPvUnitDescuento());
		productoDB.setPvCajaDescuento(value.getPvCajaDescuento());
		productoDB.setPvUnitPromo(value.getPvUnitPromo());
		productoDB.setPvCajaPromo(value.getPvCajaPromo());
		productoDB.setPresentacionUnitId(value.getPresentacionUnitId());
		productoDB.setPresentacionCajaId(value.getPresentacionCajaId());
		return productoDao.save(productoDB);
	}

	@Transactional
	@Override
	public ProductoEntity update(ProductoForm value) {
		try {
			boolean isNewFile = false;
			ProductoEntity productoDB = productoDao.findById(value.getId()).orElseThrow(()-> new RuntimeException("No existe ID"));
			if(value.getArchivos() != null && !value.getArchivos().isEmpty() && !value.getArchivos().get(0).isEmpty()) {
				setNameFotoIfExist(value);
				isNewFile = true;
				productoDB.setFoto(value.getFoto());
			}
			String nameFileOld = productoDB.getFoto();
			productoDB.setNombreGenerico(value.getNombreGenerico());
			productoDB.setNombreComercial(value.getNombreComercial());
			productoDB.setDescripcion(value.getDescripcion());
			productoDB.setCaracteristica(value.getCaracteristica());
			productoDB.setCategoriaId(value.getCategoriaId());
			productoDB.setUnidadPorCaja(value.getUnidadPorCaja());
			productoDB.setStockMedio(value.getStockMedio());
			productoDB.setStockAlto(value.getStockAlto());
			productoDB.setPcUnit(value.getPcUnit());
			productoDB.setPcCaja(value.getPcCaja());
			productoDB.setPvUnit(value.getPvUnit());
			productoDB.setPvCaja(value.getPvCaja());
			productoDB.setPvUnitDescuento(value.getPvUnitDescuento());
			productoDB.setPvCajaDescuento(value.getPvCajaDescuento());
			productoDB.setPvUnitPromo(value.getPvUnitPromo());
			productoDB.setPvCajaPromo(value.getPvCajaPromo());
			productoDB.setPresentacionUnitId(value.getPresentacionUnitId());
			productoDB.setPresentacionCajaId(value.getPresentacionCajaId());
			ProductoEntity obj = productoDao.save(productoDB);
			// Si el formulario actualizar no manda nuevo archivo, no se realiza cambios ni se elimina
			if (!isDefaultProductImage(nameFileOld) && isNewFile) {//Se elimina la foto anterior si no es el archivo por default
				uploadFileS.delete(MyConstants.RUTA_CONTROLLER_PRODUCTO, nameFileOld);
			}
			if(!isDefaultProductImage(productoDB.getFoto()) && isNewFile) {//Se adiciona la foto si no es el archivo por default
				uploadFileS.save(MyConstants.RUTA_CONTROLLER_PRODUCTO, value.getArchivos().get(0), productoDB.getFoto());
			}
			return obj;
		} catch (IOException e) {
			throw new RuntimeException("Fallo al subir archivo:" + e.getMessage());
		}
	}

	
	@Override
	@Transactional
	public ProductoEntity delete(Integer productoId) {
		ProductoEntity productoDB = productoDao.findById(productoId).orElseThrow(()-> new RuntimeException("No existe ID"));
		boolean isDelete = productoDao.deleteLogic(productoDB.getId()) > 0;
		if(isDelete) {
			if(!isDefaultProductImage(productoDB.getFoto())) {//Se elimina la foto si no es el archivo por default
				uploadFileS.delete(MyConstants.RUTA_CONTROLLER_PRODUCTO, productoDB.getFoto());
			}
			return productoDB;
		} else {
			throw new RuntimeException("Existe un error al eliminar");
		}
	}

}
