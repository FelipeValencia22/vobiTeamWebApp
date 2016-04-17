package com.vobi.team.send.email;


public interface IEnvioMensajePorEmail {
	public void enviarMensaje(String from, String to, String subject, String body)throws Exception;
}
