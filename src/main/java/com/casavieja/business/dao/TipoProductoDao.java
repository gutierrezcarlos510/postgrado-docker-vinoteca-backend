package com.casavieja.business.dao;

import com.casavieja.business.entities.TipoProductoEntity;
import org.springframework.data.repository.CrudRepository;

public interface TipoProductoDao extends CrudRepository<TipoProductoEntity, Short> {
}
