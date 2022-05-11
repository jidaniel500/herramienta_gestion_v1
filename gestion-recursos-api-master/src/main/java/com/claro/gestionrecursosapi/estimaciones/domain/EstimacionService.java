package com.claro.gestionrecursosapi.estimaciones.domain;

import com.claro.gestionrecursosapi.empleado.repository.IEmpleadoRepository;
import com.claro.gestionrecursosapi.estimaciones.dto.*;
import com.claro.gestionrecursosapi.estimaciones.entity.CostoEstimacion;
import com.claro.gestionrecursosapi.estimaciones.entity.EstimacionAdmin;
import com.claro.gestionrecursosapi.estimaciones.repository.ICostoEstimacionRepository;
import com.claro.gestionrecursosapi.estimaciones.repository.IEstimacionAdminRepository;
import com.claro.gestionrecursosapi.estructura.entity.EstructuraorganizacionalEntity;
import com.claro.gestionrecursosapi.estructura.repository.IEstructuraOrganizacionalRepository;
import com.claro.gestionrecursosapi.reportegenerico.dto.ReporteFiltroDto;
import com.claro.gestionrecursosapi.reportegenerico.entity.RangoEntity;
import com.claro.gestionrecursosapi.reportegenerico.repository.RangoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.*;

@Service
public class EstimacionService implements IEstimacionService {

    @Autowired
    IEstimacionAdminRepository estimacionAdminRepository;

    @Autowired
    IEstructuraOrganizacionalRepository estructuraRepository;

    @Autowired
    ICostoEstimacionRepository costoEstimacionRepository;

    @Autowired
    IEmpleadoRepository empleadoRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    RangoRepository rangoRepository;



    @Override
    public List<EstimacionAdmin> findAll() {
        return estimacionAdminRepository.findAll();
    }

    @Override
    public EstimacionAdmin finById(Integer id) {
        return estimacionAdminRepository.findById(id).orElse(null);
    }

    @Override
    public List<EstimacionAdmin> saveAll(List<EstimacionAdmin> estimaciones) {
        return estimacionAdminRepository.saveAll(estimaciones);
    }

    @Override
    public List<CostoEstimacion> findAllByFechaFinIsNull() {
        return costoEstimacionRepository.findAllByFechaFinIsNull();
    }

    @Override
    public List<EstimacionXProyectoDTO> estimacionXProyecto() {
        return estimacionAdminRepository.estimacionXProyecto();
    }

    @Override
    public List<EstimacionAdmin> estimacionXCodProyecto(int codProyecto) {
        return estimacionAdminRepository.findByCodProyecto(codProyecto);
    }

    @Override
    public void deleteById(Integer id) {
        estimacionAdminRepository.deleteById(id);
    }

    public int calcularEstimacion(String perfil, int cantidadServicios) {
        int valorHora = 0;
        int resultado = 0;
        int horasDesarrollo = 45;

        if (perfil.equals("DESARROLLO ESB")) {
            valorHora = 112800;
            resultado = (valorHora * horasDesarrollo) * cantidadServicios;

        } else if (perfil.equals("DESARROLLO EAP")) {
            valorHora = 100200;
            resultado = (valorHora * horasDesarrollo) * cantidadServicios;

        } else if (perfil.equals("DESARROLLO API/MS")) {
            valorHora = 78286;
            resultado = (valorHora * horasDesarrollo) * cantidadServicios;

        } else if (perfil.equals("DESARROLLO ODI")) {
            valorHora = 112800;
            resultado = (valorHora * horasDesarrollo) * cantidadServicios;

        } else if (perfil.equals("ARQUITECTO API/MS")) {
            valorHora = 94200;
            resultado = (valorHora * horasDesarrollo) * cantidadServicios;

        }
        return resultado;
    }

    public Iterable<DonutChartDTO> reporteChart(int codProyecto, int codEstructuraDesarrollador){

        try {
            DonutChartDataDTO dataCostoEstimacionProyecto = estimacionAdminRepository.costoEstimacionXProyecto(codProyecto);
            ArrayList<DonutChartDTO> ListDonutChar = new ArrayList<DonutChartDTO>();

            if (dataCostoEstimacionProyecto != null) {
                ArrayList<DonutChartDataDTO> listData = new ArrayList<DonutChartDataDTO>();
                listData.add(dataCostoEstimacionProyecto);
                String[] color = {"#1de9b6"};
                ListDonutChar.add(new DonutChartDTO("chart-costo-estimacion", listData, color));
            }

            DonutChartDataDTO dataHoraEstimacionProyecto = estimacionAdminRepository.horaEstimacionXProyecto(codProyecto);
            Long costoHoraReportadas = estimacionAdminRepository.costoHoraReportadaXProyecto(codProyecto);
            DonutChartDataDTO donutChartCostoHoras = new DonutChartDataDTO(dataHoraEstimacionProyecto.getLabel(), costoHoraReportadas);
            if (costoHoraReportadas != null) {
                ArrayList<DonutChartDataDTO> listData = new ArrayList<>();
                listData.add(donutChartCostoHoras);
                String[] color = {"#1de9b6"};
                ListDonutChar.add(new DonutChartDTO("chart-hora-reportadas", listData, color));
            }

            if (dataCostoEstimacionProyecto != null && donutChartCostoHoras != null) {
                int costoPorTerminar = (int) (dataCostoEstimacionProyecto.getValue() - donutChartCostoHoras.getValue());
                ArrayList<DonutChartDataDTO> listData = new ArrayList<>();
                listData.add(new DonutChartDataDTO(donutChartCostoHoras.getLabel(), (long) costoPorTerminar));
                String[] color;
                if ((dataCostoEstimacionProyecto.getValue() * 0.7) <= costoPorTerminar) {
                    color = new String[]{"#1de9b6"};
                } else if ((dataCostoEstimacionProyecto.getValue() * 0.3) <= costoPorTerminar) {
                    color = new String[]{"#f4c22b"};
                } else {
                    color = new String[]{"red"};
                }
                ListDonutChar.add(new DonutChartDTO("chart-hora-estimacion-menos-hora-reportadas", listData, color));
                List<DonutChartDataDTO> dataHorasReportadasXEmpleado = estimacionAdminRepository.horaReportadaXEmpleado(codProyecto, codEstructuraDesarrollador);
                ListDonutChar.add(new DonutChartDTO("chart-hora-reportada-empleado", dataHorasReportadasXEmpleado, null));
            }
            return ListDonutChar;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<DonutChartDTO> monitoreo(ReporteFiltroDto filtros) {
        try {
            this.updateRango(filtros);
            ArrayList<DonutChartDTO> ListDonutChar = new ArrayList<DonutChartDTO>();
            Query celula = entityManager.createNativeQuery("select CELULA from DF_INDICADOR_CALIDAD_V where resultadodelcambio = 'EXITOSO'");
            Query cantidad = entityManager.createNativeQuery("select CANTIDAD from DF_INDICADOR_CALIDAD_V where resultadodelcambio = 'EXITOSO'");
            List<String> resultCelula = celula.getResultList();
            List<Long> resulCantidad = cantidad.getResultList();

            List<DonutChartDataDTO> dataChart = new ArrayList<>();
            for (int i = 0; i < resultCelula.size(); i++) {
                DonutChartDataDTO n = new DonutChartDataDTO();
                BigInteger val = new BigInteger(String.valueOf(resulCantidad.get(i)));
                n.setValue(val.longValue());
                n.setLabel(resultCelula.get(i));
                dataChart.add(n);
            }

            ListDonutChar.add(new DonutChartDTO("indicador_calidad", dataChart, null));
            return ListDonutChar;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza el registro de rango en base de datos, este es usado por las vistas
     * internamente en la base de datos cuando se generan
     *
     * @param filtros
     */
    private void updateRango(ReporteFiltroDto filtros) {
        Optional<RangoEntity> rangoRegistroResult = rangoRepository.getRangoRegistro();
        RangoEntity rangoRegistro = rangoRegistroResult.get();
        rangoRegistro.setFechainicio(filtros.getFechainicio());
        rangoRegistro.setFechafin(filtros.getFechafin());
        rangoRegistro.setCodEstructura(filtros.getCodEstructura());
        EstructuraorganizacionalEntity estructuraorganizacional = estructuraRepository.findById(filtros.getCodEstructura()).orElse(null);
        rangoRegistro.setGer(estructuraorganizacional.getCodpadre());
        rangoRepository.save(rangoRegistro);
    }

    public byte[] download(int codProyecto) {
        ByteArrayOutputStream respuesta = new ByteArrayOutputStream();
        try {
            String[] enzabezado = {"Entregable", "Perfíl", "Pre-Requisito", "Cantidad", "Fecha Inicio", "Fecha Fin", "Costo", "%"};
            List<EstimacionDownloadDTO> itemEstimacion = estimacionAdminRepository.downloadExcel(codProyecto);

            Workbook workbook = new XSSFWorkbook();

            // Hoja de datos
            Sheet sheetDatos = workbook.createSheet("Registros");
            Row headerRow = sheetDatos.createRow(0);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFont(font);

            int index = 0;

            for (String fila : enzabezado) {
                Cell headerCell = headerRow.createCell(index);
                headerCell.setCellValue(enzabezado[index]);
                headerCell.setCellStyle(headerStyle);
                index++;
            }

            index--;

            sheetDatos.setAutoFilter(new CellRangeAddress(0, 0, 0, index));
            sheetDatos.createFreezePane(0, 1);

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle cellStyleDate = workbook.createCellStyle();
            cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

            int currentRowIndex = 1;
            int costo = 0;
            int porcentaje = 0;
            for (EstimacionDownloadDTO item : itemEstimacion) {
                Row fila = sheetDatos.createRow(currentRowIndex++);

                Cell column = fila.createCell(0);
                column.setCellValue(Objects.nonNull(item.getEntregable()) ? item.getEntregable() : item.getEntregable());

                column = fila.createCell(1);
                column.setCellValue(Objects.nonNull(item.getPerfil()) ? item.getPerfil() : item.getPerfil());

                column = fila.createCell(2);
                column.setCellValue(Objects.nonNull(item.getPrerequisito()) ? item.getPrerequisito() : item.getPrerequisito());

                column = fila.createCell(3);
                column.setCellValue(Objects.nonNull(item.getCantidad()) ? item.getCantidad() : item.getCantidad());

                cellStyleDate.setDataFormat(createHelper.createDataFormat()
                        .getFormat("dd/mm/yyyy"));
                column = fila.createCell(4);
                column.setCellStyle(cellStyleDate);
                column.setCellValue(item.getFechaInicio());

                cellStyleDate.setDataFormat(createHelper.createDataFormat()
                        .getFormat("dd/mm/yyyy"));
                column = fila.createCell(5);
                column.setCellStyle(cellStyleDate);
                column.setCellValue(item.getFechaFin());

                column = fila.createCell(6);
                column.setCellValue(Objects.nonNull(item.getCosto()) ? item.getCosto() : item.getCosto());
                costo = costo + item.getCosto();

                column = fila.createCell(7);
                column.setCellValue(Objects.nonNull(item.getPorcentaje()) ? item.getPorcentaje() : item.getPorcentaje());
                porcentaje = porcentaje + item.getPorcentaje();
            }

            Row fila = sheetDatos.createRow(currentRowIndex++);

            Cell columTotal = fila.createCell(0);
            columTotal.setCellValue("Total");

            CellStyle styleTotal = workbook.createCellStyle();
            styleTotal.setFont(font);
            styleTotal.setAlignment(HorizontalAlignment.CENTER);

            styleTotal.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            styleTotal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleTotal.setFont(font);
            columTotal.setCellStyle(styleTotal);

            columTotal = fila.createCell(6);
            columTotal.setCellValue(costo);

            columTotal = fila.createCell(7);
            columTotal.setCellValue(porcentaje);

            workbook.write(respuesta);
            workbook.close();
        } catch (Exception e) {
            System.out.println("Error al descargar el excel: " + e.getMessage());
        }
        return respuesta.toByteArray();
    }

    public DonutChartDataDTO costoEstimacionXProyecto(Integer codProyecto) {
        return estimacionAdminRepository.costoEstimacionXProyecto(codProyecto);
    }
}