package com.claro.gestionrecursosweb.reporte.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReporteProrrateoGeneralDto {

    private Long id;
    private String fecha;
    private String proyecto;
    private String proveedor;
    private String perfil;
    private String idRecurso;
    private String recurso;
    private BigDecimal horasSinEstructura;
    private BigDecimal costoSinEstructura;
    private BigDecimal horasConEstructura;
    private BigDecimal costoConEstructura;
    private BigDecimal porcentajeHoras;
    private BigDecimal porcentajeCosto;

    public ReporteProrrateoGeneralDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public BigDecimal getHorasSinEstructura() {
        return horasSinEstructura;
    }

    public void setHorasSinEstructura(BigDecimal horasSinEstructura) {
        this.horasSinEstructura = horasSinEstructura;
    }

    public BigDecimal getCostoSinEstructura() {
        return costoSinEstructura;
    }

    public void setCostoSinEstructura(BigDecimal costoSinEstructura) {
        this.costoSinEstructura = costoSinEstructura;
    }

    public BigDecimal getHorasConEstructura() {
        return horasConEstructura;
    }

    public void setHorasConEstructura(BigDecimal horasConEstructura) {
        this.horasConEstructura = horasConEstructura;
    }

    public BigDecimal getCostoConEstructura() {
        return costoConEstructura;
    }

    public void setCostoConEstructura(BigDecimal costoConEstructura) {
        this.costoConEstructura = costoConEstructura;
    }

    public BigDecimal getPorcentajeHoras() {
        return porcentajeHoras;
    }

    public void setPorcentajeHoras(BigDecimal porcentajeHoras) {
        this.porcentajeHoras = porcentajeHoras;
    }

    public BigDecimal getPorcentajeCosto() {
        return porcentajeCosto;
    }

    public void setPorcentajeCosto(BigDecimal porcentajeCosto) {
        this.porcentajeCosto = porcentajeCosto;
    }

    /*
    * A??O || '/' || LPAD(MES, 2, '0') || '/01' FECHA,
        PROYECTO,
        PROVEEDOR,
        PERFIL,
        IDRECURSO,
        RECURSO,
        HORAS_PERFIL HORAS_SIN_ESTRUCTURA,
        COSTO_PROYECTO COSTO_SIN_ESTRUCTURA,
        CASE WHEN PROYECTO = 'Estructura' THEN 0 ELSE HORAS_PERFIL + ((HORAS_ESTRUCTURA * PORCENTAJE_HORAS) / 100) END HORAS_CON_ESTRUCTURA,
        CASE WHEN PROYECTO = 'Estructura' THEN 0 ELSE COSTO_PROYECTO + ((COSTO_ESTRUCTURA * PORCENTAJE_COSTO) / 100) END COSTO_CON_ESTRUCTURA,
        CASE WHEN PROYECTO = 'Estructura' THEN 0 ELSE PORCENTAJE_HORAS END PORCENTAJE_HORAS,
        CASE WHEN PROYECTO = 'Estructura' THEN 0 ELSE PORCENTAJE_COSTO END PORCENTAJE_COSTO
    * */
}
