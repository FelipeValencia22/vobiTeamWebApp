package com.vobi.team.modelo;
// Generated 9/05/2016 01:33:59 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * VtEstadoSprint generated by hbm2java
 */
public class VtEstadoSprint  implements java.io.Serializable {


     private Long estsprCodigo;
     private String nombre;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Long usuCreador;
     private Long usuModificador;
     private String activo;
     private Set<VtSprint> vtSprints = new HashSet<VtSprint>(0);

    public VtEstadoSprint() {
    }

	
    public VtEstadoSprint(Long estsprCodigo, String nombre, Date fechaCreacion, Long usuCreador, String activo) {
        this.estsprCodigo = estsprCodigo;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuCreador = usuCreador;
        this.activo = activo;
    }
    public VtEstadoSprint(Long estsprCodigo, String nombre, Date fechaCreacion, Date fechaModificacion, Long usuCreador, Long usuModificador, String activo, Set<VtSprint> vtSprints) {
       this.estsprCodigo = estsprCodigo;
       this.nombre = nombre;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.usuCreador = usuCreador;
       this.usuModificador = usuModificador;
       this.activo = activo;
       this.vtSprints = vtSprints;
    }
   
    public Long getEstsprCodigo() {
        return this.estsprCodigo;
    }
    
    public void setEstsprCodigo(Long estsprCodigo) {
        this.estsprCodigo = estsprCodigo;
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
    public Set<VtSprint> getVtSprints() {
        return this.vtSprints;
    }
    
    public void setVtSprints(Set<VtSprint> vtSprints) {
        this.vtSprints = vtSprints;
    }




}


