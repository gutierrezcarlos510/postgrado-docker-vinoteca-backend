package com.casavieja.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Clase Generador de reporrs
 * Implementa las diferentes funcionalidades de logica de reportes
 *
 * @author Carlos Franz Gutierrez Gutierrez / infosystem tarija
 * @version 1.0.0, 2022/01/01
 */
public class UtilReportes {

    private static final String PATH_FILE = "/Reportes/";

    public ResponseEntity<Resource> generate(DataReport data, DataSource dataSource) throws JRException, SQLException, IOException {
        return generarReporte(data.getUrl(), data.getTipo(), data.getParametro(), dataSource.getConnection(), data.getName());
    }

    public ResponseEntity<Resource> generarReporte(String reportPath, String format, Map<?, ?> parameters, Connection connection, String NameReport) throws JRException, IOException {
        Resource resource = new ClassPathResource(new StringBuilder().append(PATH_FILE).append(reportPath).toString());
        byte[] fichero = getBytesReport(resource.getURL(), format, parameters, connection);
        ByteArrayResource resourceFile = new ByteArrayResource(fichero);
        try {
            String extension = ".pdf";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fichero.length)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(NameReport + extension).build().toString()).body(resourceFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve el reporte en un array[] de byte
     * @param reportPath
     * @param format
     * @param parameters
     * @param connection
     * @return
     * @throws JRException
     */
    public byte[] getBytesReport(URL reportPath, String format, Map<?, ?> parameters, Connection connection) throws JRException {
        JasperReport report2 = (JasperReport) JRLoader.loadObject(reportPath);

        JasperPrint fill = JasperFillManager.fillReport(report2, (Map<String, Object>) parameters, connection);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (format.equalsIgnoreCase("pdf") || format.equalsIgnoreCase("")) {
            return JasperExportManager.exportReportToPdf(fill);
        } else {

            if (format.equalsIgnoreCase("xls")) {

                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(fill));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                exporter.exportReport();
            } else if (format.equalsIgnoreCase("html")) {
                HtmlExporter exporter = new HtmlExporter();
                exporter.setExporterInput(new SimpleExporterInput(fill));
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
                exporter.exportReport();
            } else if (format.equals("rtf")) {
                JRRtfExporter exporter = new JRRtfExporter();
                exporter.setExporterInput(new SimpleExporterInput(fill));
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
                exporter.exportReport();
            }

        }
        return out.toByteArray();

    }

}