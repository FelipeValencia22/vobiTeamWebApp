package com.vobi.team.modelo;
// Generated 8/04/2016 12:30:16 AM by Hibernate Tools 4.3.1.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * VtSprint generated by hbm2java
 */
public class VtSprint  implements java.io.Serializable {


     private Long spriCodigo;
     private VtPilaProducto vtPilaProducto;
     private String nombre;
     private Date fechaInicio;
     private Date fechaFin;
     private String objetivo;
     private Integer capacidadEstimada;
     private Integer capacidadReal;
     private Date fechaCreacion;
     private Date fechaModificacion;
     private Long usuCreador;
     private Long usuModificador;
     private String activo;
     private Set<VtArtefacto> vtArtefactos = new HashSet<VtArtefacto>(0);

    public VtSprint() {
    }

	
    public VtSprint(Long spriCodigo, VtPilaProducto vtPilaProducto, String nombre, Date fechaCreacion, Long usuCreador, String activo) {
        this.spriCodigo = spriCodigo;
        this.vtPilaProducto = vtPilaProducto;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.usuCreador = usuCreador;
        this.activo = activo;
    }
    public VtSprint(Long spriCodigo, VtPilaProducto vtPilaProducto, String nombre, Date fechaInicio, Date fechaFin, String objetivo, Integer capacidadEstimada, Integer capacidadReal, Date fechaCreacion, Date fechaModificacion, Long usuCreador, Long usuModificador, String activo, Set<VtArtefacto> vtArtefactos) {
       this.spriCodigo = spriCodigo;
       this.vtPilaProducto = vtPilaProducto;
       this.nombre = nombre;
       this.fechaInicio = fechaInicio;
       this.fechaFin = fechaFin;
       this.objetivo = objetivo;
       this.capacidadEstimada = capacidadEstimada;
       this.capacidadReal = capacidadReal;
       this.fechaCreacion = fechaCreacion;
       this.fechaModificacion = fechaModificacion;
       this.usuCreador = usuCreador;
       this.usuModificador = usuModificador;
       this.activo = activo;
       this.vtArtefactos = vtArtefactos;
    }
   
    public Long getSpriCodigo() {
        return this.spriCodigo;
    }
    
    public void setSpriCodigo(Long spriCodigo) {
        this.spriCodigo = spriCodigo;
    }
    public VtPilaProducto getVtPilaProducto() {
        return this.vtPilaProducto;
    }
    
    public void setVtPilaProducto(VtPilaProducto vtPilaProducto) {
        this.vtPilaProducto = vtPilaProducto;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return this.fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getObjetivo() {
        return this.objetivo;
    }
    
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    public Integer getCapacidadEstimada() {
        return this.capacidadEstimada;
    }
    
    public void setCapacidadEstimada(Integer capacidadEstimada) {
        this.capacidadEstimada = capacidadEstimada;
    }
    public Integer getCapacidadReal() {
        return this.capacidadReal;
    }
    
    public void setCapacidadReal(Integer capacidadReal) {
        this.capacidadReal = capacidadReal;
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


