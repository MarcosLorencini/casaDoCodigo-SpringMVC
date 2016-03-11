package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Product;

@Repository// classe gerenciada pelo Spring e responsável pelo acesso aos dados.
public class ProductDAO {

	@PersistenceContext//Anotação da própria JPA. Injeta o EntityManager. Pertence a uma especificacao do javaEE, a JTA 
	private EntityManager manager;
	
	public void save(Product product){
		manager.persist(product);
	}
	
	public List<Product> list(){
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices",Product.class).getResultList();
	}
}
