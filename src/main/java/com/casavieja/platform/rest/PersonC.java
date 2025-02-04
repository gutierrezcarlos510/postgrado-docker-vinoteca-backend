/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Person;
import com.casavieja.platform.services.PersonS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author CARLOS
 *
 */
@Tag(name = "Persona", description = "Gestiona los datos de las personas que han sido registrado en el sistema")
@RestController
@RequestMapping("person/*")
public class PersonC {
    private final PersonS personS;

    @Autowired
    public PersonC(PersonS personS) {
        this.personS = personS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataResponse> listPage(HttpServletRequest request) throws Exception{
        return new DataResponse(personS.list(request), "").getResult(HttpStatus.OK);
    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> listPage() throws Exception {
        return personS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody Person value) throws Exception {
        return personS.save(value);
    }
    @PostMapping("update")
    public ResponseEntity<DataResponse> update(@RequestBody Person value) throws Exception {
        return personS.update(value);
    }
    @GetMapping("obtener/{id}")
    public ResponseEntity<DataResponse> obtener(@PathVariable Long id) throws Exception {
        return personS.obtener(id);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody Person value) throws Exception {
        return personS.delete(value);
    }
}
