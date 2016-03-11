package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;

//expoe para a Servlet do Sprinv MVC quais são as classes que devem ser lidas e carregadas

@EnableWebMvc// habilita funcionalidades: Conv. de objetos para XML e para JSON. Validacao usando a especificacao. Suporte a geracao de RSS
@ComponentScan(basePackageClasses={HomeController.class,ProductDAO.class,FileSaver.class})//indica qual pacote deve ser lido, ou seja, o pacote da classe HomeController deve ser lido pelo SpringMVC
public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	//informa onde as páginas devem ser encontradas, guarda as config. da pasta base e do sufixo que devem ser add
	//para qualquer caminho que seja retornado por métodos dos controllers.
	@Bean//indica para o Spring que o retorno desse método deve ser registrado como um objeto gerenciado pelo container.
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	// avisa ao spring que o mesmo deve buscar as msg relativa as chaves e valores.
	@Bean
	public MessageSource messageSource(){// o spring vai procurar por um Bean registrado com esse nome
		ReloadableResourceBundleMessageSource bundle = 
				new ReloadableResourceBundleMessageSource();
		bundle.setBasename("/WEB-INF/messages");
		bundle.setDefaultEncoding("UTF-8");
		bundle.setCacheSeconds(1);//deve ser carregado a cada um segungo
		return bundle;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		//true indica que todos os conversores padrões devem ser add.
		
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));//informa o tipo de de formatação
		registrar.registerFormatters(conversionService);//registra os convesores no objeto do tipo FormattingConversionService
		return conversionService;
	}
	// O nome do metodo tem que ser mvcConversionService, o Spring usa para registrar o objeto responsável
	//por agrupar os conversores
	//DateFormatterRegistrar implementa a inteface FormatterRegistrar, onde é agrupado vários tipos de 
	//conversão: Calendar para Long, Long para Calendar. Date para Calendar, Calendar para Date.
	
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
	//MultipartResolver interface que trata requeste conhecido como contentType que é o multipart/form-data.
	//implementacao que usa o recurso de tratamento de upload provido pela especificacao de Servlets.
	

}
