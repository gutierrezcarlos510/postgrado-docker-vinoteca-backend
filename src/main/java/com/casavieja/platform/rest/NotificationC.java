/**
 *
 */
package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.Notification;
import com.casavieja.platform.services.NotificationS;
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
@Tag(name = "Notificaciones", description = "Gestiona las notificaciones del sistema")
@RestController
@RequestMapping("notification/*")
public class NotificationC {
    private final NotificationS notificationS;

    @Autowired
    public NotificationC(NotificationS notificationS) {
        this.notificationS = notificationS;
    }

    @RequestMapping("listPage")
    public ResponseEntity<DataResponse> listPage(HttpServletRequest request) throws Exception{
        return new DataResponse(notificationS.list(request), "").getResult(HttpStatus.OK);
    }
    @GetMapping("list")
    public ResponseEntity<DataResponse> listPage() throws Exception {
        return notificationS.listActive();
    }

    @PostMapping("save")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody Notification value) throws Exception {
        return notificationS.save(value);
    }

    @PostMapping("delete")
    public ResponseEntity<DataResponse> delete(@RequestBody Notification value) throws Exception {
        return notificationS.delete(value);
    }
}
