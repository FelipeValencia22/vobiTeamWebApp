package com.vobi.team.taras.programadas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Scope("Singleton")
public class TareasProgramadasVobiTeam {
	
	 private static final Logger log = LoggerFactory.getLogger(TareasProgramadasVobiTeam.class);
	@Scheduled(fixedDelay=50000)
	public void mandarMensajeDeActualizacionDeEsfuerzo() {
		log.info("Probando ejecutar tareas");
	}
}
