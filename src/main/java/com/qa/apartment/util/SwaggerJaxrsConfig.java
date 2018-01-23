package com.qa.apartment.util;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

@WebServlet(name="SwaggerJaxrsConfig", loadOnStartup=1)
public class SwaggerJaxrsConfig extends HttpServlet {
	
	@Inject
	private SwaggerConfig swagConf;
	
	@Override
	public void init(ServletConfig servConf) {
		try {
			super.init(servConf);
			ConfigFactory.setConfig(swagConf);
			swagConf.setBasePath("http://localhost:8080/qa-apartments3/rest");;
			swagConf.setApiVersion("1.0.0");
			ScannerFactory.setScanner(new DefaultJaxrsScanner());
			ClassReaders.setReader(new DefaultJaxrsApiReader());
			
		} catch (ServletException e) {
			System.out.println(e.getMessage());
		}
	}

}
