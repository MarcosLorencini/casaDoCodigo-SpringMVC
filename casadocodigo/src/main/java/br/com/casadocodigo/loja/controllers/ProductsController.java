package br.com.casadocodigo.loja.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.enumeration.BookType;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Product;

@Controller
@Transactional //informa que os método da classe precisa de transação. Pertence a uma especificacao do javaEE, a JTA
@RequestMapping("/produtos")//todos os endereços relacionado com o cad(save,list). começam com produtos, por isso a anotação na classe.
public class ProductsController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private FileSaver fileSaver;
	
	@RequestMapping("/form")//1
	public ModelAndView form(@ModelAttribute Product product){//para a tag form em form.jsp funcionar ela precisa do obj recebido como parametro
		ModelAndView modelAndView = new ModelAndView("products/form");//abre o form.jsp para o preechimento do produtos
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
		
	}
	
	@RequestMapping(method=RequestMethod.POST)//2 é chamado após o preenchimento do produto, quando é feito do cad.
	public ModelAndView save(MultipartFile summary,@Valid Product product, //@Valid vem da especif. Bean Validation. Dispara o processo de validacao.
		//Part representa o arquivo que foi enviado pelo formulario.
		BindingResult bindingResult,// classe que verifica se tem erro de validacao. 
		RedirectAttributes redirectAttributes){
		System.out.println(summary.getName()+";"+summary.getOriginalFilename());//retorna no do input e nome do arq.
		if(bindingResult.hasErrors()){//caso haja erros na validação volta para o form.
			return form(product);//chama o metodo form() para enviar o enum do tipo de books no produtos/form. Envia parametro 
			// para o metodo form para o Spring disponiblizar como variável do request.
		}
		String webPath = fileSaver.write("uploaded-images", summary);
		product.setSummaryPath(webPath);
		System.out.println("Cadastrando o Produto");
		productDAO.save(product);
		redirectAttributes.addFlashAttribute("sucesso", "Produto Cadastrado com sucesso");
		return new ModelAndView("redirect:/produtos");//Nao chamo o post novamente, chama o get para listar os produtos
		//caso nao tenha a barra /produtos o spring faz o redirect a url atual
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("products/list");//envia os produtos para a list.jsp
		System.out.println("Listando Produtos");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	//informa ao Spring que é para usar a classe ProductValidator para validar o Product
	/*@InitBinder// indica que este método deve ser chamado quando o request cair neste controller.
	protected void initBinder(WebDataBinder binder){
		binder.setValidator(new ProductValidator());// fala para o Spring MVC qual validator ele deve usar.
		
	}*/ // Apagado o validador customizado, vamos usar a validacao nos  campos da classe Product

}
