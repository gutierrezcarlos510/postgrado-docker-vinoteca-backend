package com.casavieja.platform.config;

import com.casavieja.business.dao.AlmacenDao;
import com.casavieja.business.services.AlmacenS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class TareaProgramada {
    private static final String TIME_ZONE = "America/La_Paz";

    @Autowired
    private AlmacenDao almacenDao;


    //A: Segundos (0 - 59) B: Minutos (0 - 59). C: Horas (0 - 23). D: Día (1 - 31). E: Mes (1 - 12). F: Día de la semana (0 - 6).
    @Scheduled(cron = "0 22 0 * * *", zone = TIME_ZONE)
    public void cierreDiarioCredito() {
        try {
            almacenDao.procesarHistoricoDiario();
        } catch (Exception ex) {
            log.error(ex.getMessage()
            );
        }
    }
}
