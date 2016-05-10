package com.vobi.team.modelo;
// Generated 9/05/2016 01:33:59 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;

/**
 * VtArchivo generated by hbm2java
 */
public class VtArchivo  implements java.io.Serializable {


     private Long archCodigo;
     private VtArtefacto vtArtefacto;
     private String nombre;
     private byte[] archivo;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Long usuCreador;
     private Long usuModificador;
     private String activo;

    public VtArchivo() {
    }

	
    public VtArchivo(Long archCodigo, VtArtefacto vtArtefacto, String nombre, Date fechaCreacion, Long usuCreador, String activo) {
        this.archCodigo = archCodigo;
        this.vtArtefacto = vtArtefacto;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuCreador = usuCreador;
        this.activo = activo;
    }
    public VtArchivo(Long archCodigo, VtArtefacto vtArtefacto, String nombre, byte[] archivo, Date fechaCreacion, Date fechaModificacion, Long usuCreador, Long usuModificador, String activo) {
       this.archCodigo = archCodigo;
       this.vtArtefacto = vtArtefacto;
       this.nombre = nombre;
       this.archivo = archivo;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.usuCreador = usuCreador;
       this.usuModificador = usuModificador;
       this.activo = activo;
    }
   
    public Long getArchCodigo() {
        return this.archCodigo;
    }
    
    public void setArchCodigo(Long archCodigo) {
        this.archCodigo = archCodigo;
    }
    public VtArtefacto getVtArtefacto() {
        return this.vtArtefacto;
    }
    
    public void setVtArtefacto(VtArtefacto vtArtefacto) {
        this.vtArtefacto = vtArtefacto;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public byte[] getArchivo() {
        return this.archivo;
    }
    
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
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




}


