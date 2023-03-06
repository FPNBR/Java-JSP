package util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ReportUtil implements Serializable {
    public byte[] gerarRelatorioExcel(List list, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
        try {
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list); // Cria a lista de dados que vem do SQL quando a consulta é feita
            String arquivoJasperCompilado = servletContext.getRealPath("report") + File.separator + nomeRelatorio + ".jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasperCompilado, params, jrBeanCollectionDataSource);

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new ByteArrayOutputStream()));
            exporter.exportReport();

            return ((ByteArrayOutputStream) exporter.getExporterOutput().getOutputStream()).toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao exportar o arquivo Jasper para Excel " + e.getMessage());
        }
    }


    public byte[] gerarRelatorioPDF(List list, String nomeRelatorio, ServletContext servletContext) throws Exception {
        try {
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list); // Cria a lista de dados que vem do SQL quando a consulta é feita
            String arquivoJasperCompilado = servletContext.getRealPath("report") + File.separator + nomeRelatorio + ".jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasperCompilado, new HashMap<>(), jrBeanCollectionDataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao exportar o arquivo Jasper para PDF " + e.getMessage());
        }
    }

    public byte[] gerarRelatorioPDF(List list, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
        try {
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list); // Cria a lista de dados que vem do SQL quando a consulta é feita
            String arquivoJasperCompilado = servletContext.getRealPath("report") + File.separator + nomeRelatorio + ".jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasperCompilado, params, jrBeanCollectionDataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao exportar o arquivo Jasper para PDF " + e.getMessage());
        }
    }
}
