package br.com.casadocodigo.loja.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServeletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{AppWebConfiguration.class,JPAConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {//estas classes v�o ser carregadas pelo pr�prio container do Spring, esta classe s�o lidas durante o carregamento da aplicacao web.
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {// defini qual vai ser o padrao de endere�o que vai ser delegado para o Servlet do SpringMVC
		return new String[]{"/"};
	}

}
