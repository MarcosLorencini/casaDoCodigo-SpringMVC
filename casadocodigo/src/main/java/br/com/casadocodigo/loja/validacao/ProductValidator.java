package br.com.casadocodigo.loja.validacao;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Product;

public class ProductValidator implements Validator {

	// recebe o obj que quer ser validado e retorna se o validador sabe lidar com ele.
	//caso aceite associe uma classe de valida��o a um controller, mas o m�todo supports n�o aceite o par�metro
	//anotado com @Valid, ser� lan�ado uma exception.
	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {//recebe o obj a ser validado
		ValidationUtils.rejectIfEmpty(errors, "title", "field.required");//obj onde sera guardados cada um dos erros,
		//atributo do modelo que deve ser validado, chave que ser� usada para buscar msg relativa a esse erro.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");
		
		Product product = (Product) target;
		if(product.getPages()==0){
			errors.rejectValue("pages", "field.required");
		}
		
	}

}
