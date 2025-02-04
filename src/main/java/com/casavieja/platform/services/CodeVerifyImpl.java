/**
 * 
 */
package com.casavieja.platform.services;

import com.casavieja.platform.dao.CodeVerifyDao;
import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.entities.CodeVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * @author CARLOS
 *
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CodeVerifyImpl implements CodeVerifyS {

	private final CodeVerifyDao codeVerifyDao;
	private final ApiWhatsappS apiWhatsappS;
	private static final int MINUTO_EXPIRACION_CODE = 30;

	@Override
	@Transactional
	public ResponseEntity<DataResponse> generarCodigo(String celular) {
		if(celular!= null && !celular.isEmpty() && celular.startsWith("591")) {
			CodeVerify codeVerify = new CodeVerify();
			codeVerify.setCodigo(generarCode());
			codeVerify.setCelular(celular);
			codeVerify.setEstado("2");
			cargarRangoFecha(codeVerify);
			codeVerifyDao.save(codeVerify);
			apiWhatsappS.enviarWhatsapp(celular, "*ALTOKE, Codigo de Verificacion:* \n *"+separarCadena(codeVerify.getCodigo())+"*");
			return new DataResponse<>("Se envio correctamente el codigo al Whatsapp de la persona.").getResult(HttpStatus.OK);
		} else {
			return new DataResponse<>("Celular Invalido").getResult(HttpStatus.BAD_REQUEST);
		}
	}

	public String generarCode() {
		double sixDigits = 100000 + Math.random() * 900000;
		String cadena = ""+(int)sixDigits;
		return cadena;
	}
	public String separarCadena(String cadena) {
		String cadena2 = "";
		for (int i = 0; i < cadena.length(); i++) {
			cadena2 = cadena2 + " " + cadena.charAt(i);
		}
		return cadena2.trim();
	}
	public void cargarRangoFecha(CodeVerify codeVerify) {
		Date fini = new Date();
		codeVerify.setFini(fini);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fini); //tuFechaBase es un Date;
		calendar.add(Calendar.MINUTE, MINUTO_EXPIRACION_CODE); //minutosASumar es int.
		//lo que más quieras sumar
		Date ffin = calendar.getTime(); //Y ya tienes la fecha sumada
		codeVerify.setFfin(ffin);
	}
}
