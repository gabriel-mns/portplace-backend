package com.pucpr.portplace.features.reports.services;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.services.PortfolioService;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.reports.exceptions.TemplateNotFoundException;

import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReportService {

    private static final String TEMPLATE_PATH = "/templates/template_portfolio_report_export.xlsx";
    private final TemplateEngine templateEngine;

    private PortfolioService portfolioService;

    public void exportDataToExcelTemplate(Long portfolioId, OutputStream outputStream) {

        PortfolioReadDTO portfolio = portfolioService.getPortfolio(portfolioId);
        List<ProjectReadDTO> projects = portfolio.getProjects();

        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("portfolio", portfolio);
        contextMap.put("projects", projects);

        try(InputStream inputStream = this.getClass().getResourceAsStream(TEMPLATE_PATH)) {
            Context context = new Context();
            context.toMap().putAll(contextMap);
            JxlsHelper.getInstance()
                .setEvaluateFormulas(true)
                .processTemplate(inputStream, outputStream, context);
        } catch(NullPointerException e) {
            throw new TemplateNotFoundException(TEMPLATE_PATH);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] exportDataToPdfTemplate(Long portfolioId) {
        try {
            // Obtém o portfólio completo
            PortfolioReadDTO portfolio = portfolioService.getPortfolio(portfolioId);

            // Prepara os dados para o template
            Map<String, Object> model = new HashMap<>();
            model.put("portfolio", portfolio);
            model.put("projects", portfolio.getProjects());
            model.put("risks", portfolio.getRisks());
            model.put("owners", portfolio.getOwners());
            model.put("categories", portfolio.getCategories());

            // Processa o HTML via Thymeleaf
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            context.setVariables(model);
            String html = templateEngine.process("portfolio_report", context);

            // Resolve o caminho base (necessário para o CSS e imagens)
            String baseUri = Objects.requireNonNull(
                    getClass().getResource("/static/")
            ).toURI().toString();

            // Gera o PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(html, baseUri);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do portfólio: " + e.getMessage(), e);
        }
    }
}
