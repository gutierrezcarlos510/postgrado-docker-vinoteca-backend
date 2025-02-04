package com.casavieja.platform.rest;

import com.casavieja.utils.MyConstants;
import com.casavieja.utils.UploadFileS;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class FileDownloadC {

    private final UploadFileS uploadFileS;
    @GetMapping("downloadProducto/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Resource resource = uploadFileS.load(MyConstants.RUTA_CONTROLLER_PRODUCTO,filename);
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getFile/{filename:.+}")
    public Resource getFile(@PathVariable String filename) {
        try {
            return uploadFileS.load(MyConstants.RUTA_CONTROLLER_PRODUCTO,filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
