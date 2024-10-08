package com.cs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cs.entity.CitizenPlan;
import com.cs.request.SearchRequest;
import com.cs.service.IReportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {
	
	@Autowired
	private IReportService service;
	
	@PostMapping("/search")
	public String handelerSearch(@ModelAttribute("search") SearchRequest search,Model model)
	{
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		init(model);
		return "index";
	}
	
	@GetMapping("/")
	public String indexPage(Model model)
	{
		model.addAttribute("search",new SearchRequest());
		init(model);
		return "index";
	}


	private void init(Model model) {
		model.addAttribute("names",service.getPlanNames());
		model.addAttribute("status", service.getplanStatuses());
	}
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse res) throws Exception
	{
		res.setContentType("application/octet-stream");
		res.addHeader("Content-Disposition", "attachment; filename=plans.xls");
		service.ExportExcel(res);
	}
	
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse res) throws Exception
	{
		res.setContentType("application/pdf");
		res.addHeader("Content-Disposition", "attachment; filename=plans.pdf");
		service.ExportPdf(res);
	}
}




