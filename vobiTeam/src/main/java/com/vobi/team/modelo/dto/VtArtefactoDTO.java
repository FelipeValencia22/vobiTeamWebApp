package com.vobi.team.modelo.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.sql.*;

import java.util.Date;


/**
*
* @author Zathura Code Generator http://zathuracode.org/
* www.zathuracode.org
*
*/
public class VtArtefactoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(VtArtefactoDTO.class);
    private String activo;
    private Long arteCodigo;
    private String descripcion;
    private Integer esfuerzoEstimado;
    private Integer esfuerzoReal;
    private Integer esfuerzoRestante;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String origen;
    private Integer puntos;
    private String titulo;
    private Long usuCreador;
    private Long usuModificador;
    private Long estaCodigo_VtEstado;
    private Long pilaCodigo_VtPilaProducto;
    private Long prioCodigo_VtPrioridad;
    private Long spriCodigo_VtSprint;
    private Long tparCodigo_VtTipoArtefacto;
    public String tpar_Nombre;
    public String estado_Nombre;
    public String prioridad_Nombre;
    
    
    
    private String tiempoConvertidoEstimado;
    private String tiempoConvertidoRestante;
    private String tiempoConvertidoPuntos;
    private String tiempoConvertidoReal;
    

	public String convertirMinutosAHorasYMinutos(Integer numero){
		try {
			Integer horas = (numero)/60;
			Integer minutos = (numero)%60;
			String j = horas.toString()+minutos.toString();
			
			if(j.length()==3){
				j = "0"+j;
			}
			if(j.length()==2){
				j = "00"+j;
			}
			if(j.length()==1){
				j = "000"+j;
			}
			if(j.equals("")){
				j = "";
			}
			return j;
		} catch (Exception e) {
			return "";
			
		}
		
		

	}
	

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Long getArteCodigo() {
        return arteCodigo;
    }

    public void setArteCodigo(Long arteCodigo) {
        this.arteCodigo = arteCodigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEsfuerzoEstimado() {
        return esfuerzoEstimado;
    }

    public void setEsfuerzoEstimado(Integer esfuerzoEstimado) {
        this.esfuerzoEstimado = esfuerzoEstimado;
        tiempoConvertidoEstimado = convertirMinutosAHorasYMinutos(esfuerzoEstimado);
    }

    public Integer getEsfuerzoReal() {
        return esfuerzoReal;
    }

    public void setEsfuerzoReal(Integer esfuerzoReal) {
        this.esfuerzoReal = esfuerzoReal;
        tiempoConvertidoReal = convertirMinutosAHorasYMinutos(esfuerzoReal);
        
    }

    public Integer getEsfuerzoRestante() {
        return esfuerzoRestante;
    }

    public void setEsfuerzoRestante(Integer esfuerzoRestante) {
        this.esfuerzoRestante = esfuerzoRestante;
        tiempoConvertidoRestante = convertirMinutosAHorasYMinutos(esfuerzoRestante);
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
        tiempoConvertidoPuntos =  convertirMinutosAHorasYMinutos(puntos);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getUsuCreador() {
        return usuCreador;
    }

    public void setUsuCreador(Long usuCreador) {
        this.usuCreador = usuCreador;
    }

    public Long getUsuModificador() {
        return usuModificador;
    }

    public void setUsuModificador(Long usuModificador) {
        this.usuModificador = usuModificador;
    }

    public Long getEstaCodigo_VtEstado() {
        return estaCodigo_VtEstado;
    }

    public void setEstaCodigo_VtEstado(Long estaCodigo_VtEstado) {
        this.estaCodigo_VtEstado = estaCodigo_VtEstado;
    }

    public Long getPilaCodigo_VtPilaProducto() {
        return pilaCodigo_VtPilaProducto;
    }

    public void setPilaCodigo_VtPilaProducto(Long pilaCodigo_VtPilaProducto) {
        this.pilaCodigo_VtPilaProducto = pilaCodigo_VtPilaProducto;
    }

    public Long getPrioCodigo_VtPrioridad() {
        return prioCodigo_VtPrioridad;
    }

    public void setPrioCodigo_VtPrioridad(Long prioCodigo_VtPrioridad) {
        this.prioCodigo_VtPrioridad = prioCodigo_VtPrioridad;
    }

    public Long getSpriCodigo_VtSprint() {
        return spriCodigo_VtSprint;
    }

    public void setSpriCodigo_VtSprint(Long spriCodigo_VtSprint) {
        this.spriCodigo_VtSprint = spriCodigo_VtSprint;
    }

    public Long getTparCodigo_VtTipoArtefacto() {
        return tparCodigo_VtTipoArtefacto;
    }

    public void setTparCodigo_VtTipoArtefacto(Long tparCodigo_VtTipoArtefacto) {
        this.tparCodigo_VtTipoArtefacto = tparCodigo_VtTipoArtefacto;
    }

	public String getTpar_Nombre() {
		return tpar_Nombre;
	}

	public void setTpar_Nombre(String tpar_Nombre) {
		this.tpar_Nombre = tpar_Nombre;
	}

	public String getEstado_Nombre() {
		return estado_Nombre;
	}

	public void setEstado_Nombre(String estado_Nombre) {
		this.estado_Nombre = estado_Nombre;
	}

	public String getPrioridad_Nombre() {
		return prioridad_Nombre;
	}

	public void setPrioridad_Nombre(String prioridad_Nombre) {
		this.prioridad_Nombre = prioridad_Nombre;
	}


	public String getTiempoConvertidoEstimado() {
		return tiempoConvertidoEstimado;
	}


	public void setTiempoConvertidoEstimado(String tiempoConvertidoEstimado) {
		this.tiempoConvertidoEstimado = tiempoConvertidoEstimado;
	}
	public String getTiempoConvertidoRestante() {
		return tiempoConvertidoRestante;
	}


	public void setTiempoConvertidoRestante(String tiempoConvertidoRestante) {
		this.tiempoConvertidoRestante = tiempoConvertidoRestante;
	}


	public String getTiempoConvertidoPuntos() {
		return tiempoConvertidoPuntos;
	}


	public void setTiempoConvertidoPuntos(String tiempoConvertidoPuntos) {
		this.tiempoConvertidoPuntos = tiempoConvertidoPuntos;
	}


	public String getTiempoConvertidoReal() {
		return tiempoConvertidoReal;
	}


	public void setTiempoConvertidoReal(String tiempoConvertidoReal) {
		this.tiempoConvertidoReal = tiempoConvertidoReal;
	}




    
}
