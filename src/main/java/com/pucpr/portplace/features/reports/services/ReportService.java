package com.pucpr.portplace.features.reports.services;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.services.PortfolioService;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.reports.exceptions.TemplateNotFoundException;

import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private static final String TEMPLATE_PATH = "/templates/template_portfolio_report_export.xlsx";

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
            JxlsHelper.getInstance().processTemplate(inputStream, outputStream, context);
        } catch(NullPointerException e) {
            throw new TemplateNotFoundException(TEMPLATE_PATH);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
