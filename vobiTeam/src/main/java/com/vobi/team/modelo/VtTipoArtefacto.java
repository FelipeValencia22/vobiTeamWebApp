package com.vobi.team.modelo;
// Generated 9/05/2016 01:33:59 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * VtTipoArtefacto generated by hbm2java
 */
public class VtTipoArtefacto  implements java.io.Serializable {


     private Long tparCodigo;
     private String nombre;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Long usuCreador;
     private Long usuModificador;
     private String activo;
     private Set<VtArtefacto> vtArtefactos = new HashSet<VtArtefacto>(0);

    public VtTipoArtefacto() {
    }

	
    public VtTipoArtefacto(Long tparCodigo, String nombre, Date fechaCreacion, Long usuCreador, String activo) {
        this.tparCodigo = tparCodigo;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuCreador = usuCreador;
        this.activo = activo;
    }
    public VtTipoArtefacto(Long tparCodigo, String nombre, Date fechaCreacion, Date fechaModificacion, Long usuCreador, Long usuModificador, String activo, Set<VtArtefacto> vtArtefactos) {
       this.tparCodigo = tparCodigo;
       this.nombre = nombre;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.usuCreador = usuCreador;
       this.usuModificador = usuModificador;
       this.activo = activo;
       this.vtArtefactos = vtArtefactos;
    }
   
    public Long getTparCodigo() {
        return this.tparCodigo;
    }
    
    public void setTparCodigo(Long tparCodigo) {
        this.tparCodigo = tparCodigo;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public Set<VtArtefacto> getVtArtefactos() {
        return this.vtArtefactos;
    }
    
    public void setVtArtefactos(Set<VtArtefacto> vtArtefactos) {
        this.vtArtefactos = vtArtefactos;
    }




}


