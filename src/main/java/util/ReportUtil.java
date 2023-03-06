package util;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ReportUtil implements Serializable {
    public byte[] gerarRelatorioPDF(List list, String nomeRelatorio, ServletContext servletContext) throws Exception {
        try {
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list); // Cria a lista de dados que vem do SQL quando a consulta é feita
            String arquivoJasperCompilado = servletContext.getRealPath("report") + File.separator + nomeRelatorio + ".jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasperCompilado, new HashMap<>(), jrBeanCollectionDataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao exportar o arquivo Jasper para PDF" + e.getMessage());
        }
    }
}
