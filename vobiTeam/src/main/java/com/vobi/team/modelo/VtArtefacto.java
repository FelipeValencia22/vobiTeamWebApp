package com.vobi.team.modelo;
// Generated 9/05/2016 01:33:59 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * VtArtefacto generated by hbm2java
 */
public class VtArtefacto  implements java.io.Serializable {


     private Long arteCodigo;
     private VtEstado vtEstado;
     private VtPilaProducto vtPilaProducto;
     private VtPrioridad vtPrioridad;
     private VtSprint vtSprint;
     private VtTipoArtefacto vtTipoArtefacto;
     private String titulo;
     private String descripcion;
     private Integer esfuerzoEstimado;
     private Integer esfuerzoRestante;
     private Integer esfuerzoReal;
     private Integer puntos;
     private String origen;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Long usuCreador;
     private Long usuModificador;
     private String activo;
     private Set<VtArchivo> vtArchivos = new HashSet<VtArchivo>(0);
     private Set<VtHistoriaArtefacto> vtHistoriaArtefactos = new HashSet<VtHistoriaArtefacto>(0);
     private Set<VtProgresoArtefacto> vtProgresoArtefactos = new HashSet<VtProgresoArtefacto>(0);
     private Set<VtUsuarioArtefacto> vtUsuarioArtefactos = new HashSet<VtUsuarioArtefacto>(0);

    public VtArtefacto() {
    }

	
    public VtArtefacto(Long arteCodigo, VtEstado vtEstado, VtPilaProducto vtPilaProducto, VtPrioridad vtPrioridad, VtTipoArtefacto vtTipoArtefacto, String titulo, Date fechaCreacion, Long usuCreador, String activo) {
        this.arteCodigo = arteCodigo;
        this.vtEstado = vtEstado;
        this.vtPilaProducto = vtPilaProducto;
        this.vtPrioridad = vtPrioridad;
        this.vtTipoArtefacto = vtTipoArtefacto;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.usuCreador = usuCreador;
        this.activo = activo;
    }
    public VtArtefacto(Long arteCodigo, VtEstado vtEstado, VtPilaProducto vtPilaProducto, VtPrioridad vtPrioridad, VtSprint vtSprint, VtTipoArtefacto vtTipoArtefacto, String titulo, String descripcion, Integer esfuerzoEstimado, Integer esfuerzoRestante, Integer esfuerzoReal, Integer puntos, String origen, Date fechaCreacion, Date fechaModificacion, Long usuCreador, Long usuModificador, String activo, Set<VtArchivo> vtArchivos, Set<VtHistoriaArtefacto> vtHistoriaArtefactos, Set<VtProgresoArtefacto> vtProgresoArtefactos, Set<VtUsuarioArtefacto> vtUsuarioArtefactos) {
       this.arteCodigo = arteCodigo;
       this.vtEstado = vtEstado;
       this.vtPilaProducto = vtPilaProducto;
       this.vtPrioridad = vtPrioridad;
       this.vtSprint = vtSprint;
       this.vtTipoArtefacto = vtTipoArtefacto;
       this.titulo = titulo;
       this.descripcion = descripcion;
       this.esfuerzoEstimado = esfuerzoEstimado;
       this.esfuerzoRestante = esfuerzoRestante;
       this.esfuerzoReal = esfuerzoReal;
       this.puntos = puntos;
       this.origen = origen;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.usuCreador = usuCreador;
       this.usuModificador = usuModificador;
       this.activo = activo;
       this.vtArchivos = vtArchivos;
       this.vtHistoriaArtefactos = vtHistoriaArtefactos;
       this.vtProgresoArtefactos = vtProgresoArtefactos;
       this.vtUsuarioArtefactos = vtUsuarioArtefactos;
    }
   
    public Long getArteCodigo() {
        return this.arteCodigo;
    }
    
    public void setArteCodigo(Long arteCodigo) {
        this.arteCodigo = arteCodigo;
    }
    public VtEstado getVtEstado() {
        return this.vtEstado;
    }
    
    public void setVtEstado(VtEstado vtEstado) {
        this.vtEstado = vtEstado;
    }
    public VtPilaProducto getVtPilaProducto() {
        return this.vtPilaProducto;
    }
    
    public void setVtPilaProducto(VtPilaProducto vtPilaProducto) {
        this.vtPilaProducto = vtPilaProducto;
    }
    public VtPrioridad getVtPrioridad() {
        return this.vtPrioridad;
    }
    
    public void setVtPrioridad(VtPrioridad vtPrioridad) {
        this.vtPrioridad = vtPrioridad;
    }
    public VtSprint getVtSprint() {
        return this.vtSprint;
    }
    
    public void setVtSprint(VtSprint vtSprint) {
        this.vtSprint = vtSprint;
    }
    public VtTipoArtefacto getVtTipoArtefacto() {
        return this.vtTipoArtefacto;
    }
    
    public void setVtTipoArtefacto(VtTipoArtefacto vtTipoArtefacto) {
        this.vtTipoArtefacto = vtTipoArtefacto;
    }
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getEsfuerzoEstimado() {
        return this.esfuerzoEstimado;
    }
    
    public void setEsfuerzoEstimado(Integer esfuerzoEstimado) {
        this.esfuerzoEstimado = esfuerzoEstimado;
    }
    public Integer getEsfuerzoRestante() {
        return this.esfuerzoRestante;
    }
    
    public void setEsfuerzoRestante(Integer esfuerzoRestante) {
        this.esfuerzoRestante = esfuerzoRestante;
    }
    public Integer getEsfuerzoReal() {
        return this.esfuerzoReal;
    }
    
    public void setEsfuerzoReal(Integer esfuerzoReal) {
        this.esfuerzoReal = esfuerzoReal;
    }
    public Integer getPuntos() {
        return this.puntos;
    }
    
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    public String getOrigen() {
        return this.origen;
    }
    
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    public Long getUsuCreador() {
        return this.usuCreador;
    }
    
    public void setUsuCreador(Long usuCreador) {
        this.usuCreador = usuCreador;
    }
    public Long getUsuModificador() {
        return this.usuModificador;
    }
    
    public void setUsuModificador(Long usuModificador) {
        this.usuModificador = usuModificador;
    }
    public String getActivo() {
        return this.activo;
    }
    
    public void setActivo(String activo) {
        this.activo = activo;
    }
    public Set<VtArchivo> getVtArchivos() {
        return this.vtArchivos;
    }
    
    public void setVtArchivos(Set<VtArchivo> vtArchivos) {
        this.vtArchivos = vtArchivos;
    }
    public Set<VtHistoriaArtefacto> getVtHistoriaArtefactos() {
        return this.vtHistoriaArtefactos;
    }
    
    public void setVtHistoriaArtefactos(Set<VtHistoriaArtefacto> vtHistoriaArtefactos) {
        this.vtHistoriaArtefactos = vtHistoriaArtefactos;
    }
    public Set<VtProgresoArtefacto> getVtProgresoArtefactos() {
        return this.vtProgresoArtefactos;
    }
    
    public void setVtProgresoArtefactos(Set<VtProgresoArtefacto> vtProgresoArtefactos) {
        this.vtProgresoArtefactos = vtProgresoArtefactos;
    }
    public Set<VtUsuarioArtefacto> getVtUsuarioArtefactos() {
        return this.vtUsuarioArtefactos;
    }
    
    public void setVtUsuarioArtefactos(Set<VtUsuarioArtefacto> vtUsuarioArtefactos) {
        this.vtUsuarioArtefactos = vtUsuarioArtefactos;
    }




}


