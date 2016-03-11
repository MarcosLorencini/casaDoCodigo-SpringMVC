<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Cadastro de Produtos</title>
	</head>
	<body>
		<!--disponib. o mesmo obj. BindingResult através da var erro  -->
		<spring:hasBindErrors name="product"> <!-- o name está associado com o parametro Product do metodo save do controller -->
			<ul>
				<c:forEach var="error" items="${errors.allErrors}">	
				<li><spring:message code="${error.code}"
					text="${error.field}"/></li>
		</c:forEach>
			</ul>
		</spring:hasBindErrors>
		
		<!-- funcao que recebe PC letras iniciais do controller e save é o nome do método.
		O metodo build() constroe a url caso ela mude. A saída fica assim: (http://localhost:8080/casadocodigo/produtos).
		Usado caso o nome do contexto mude ou a url -->
		<form:form action="${spring:mvcUrl('PC#save').build()}" method="post" commandName="product" enctype="multipart/form-data">
		<!-- commandName recebe o parametro que está sendo validado pelo controler com a 1a letra em minusculo save(@Valid
		Product product...)  -->
		<!--enctype informa como queremos mandar os dados do navegador para o servidor. multipart/form-data queremos transmitir
		arquivos  -->
			<div>
				<label for="title">Titulo</label>
				<form:input path="title" /><!--caso gerou erro na validacao, para nao perder os dados invoca os getters da classe Product definido pelo commandName   -->
				<form:errors path="title"/><!--a tag errors exibe todos os erros do atributo. Deve ser usado dentro do form  -->
			</div>
			<div>
				<label for="description">Descrição</label>
				<form:textarea path="description" rows="10" cols="20" />
				<form:errors path="description"/>
			</div>
			<div>
				<label for="pages">Número de Páginas</label>
				<form:input path="pages"/>
				<form:errors path="pages"/>
			</div>
			<div>
				<label for="releaseDate">Data de lançamento</label>
				<form:input path="releaseDate" type="date"/>
				<form:errors path="releaseDate"/>
			</div>
			<div>
				<label for="summary">Sumario do livro</label>
				<input type="file" name="summary"/>
				<form:errors path="summaryPath"/>
			</div>
			<div>
				<c:forEach items="${types}" var="bookType" varStatus="status">
					<div>
						<label for="price_${bookType}">${bookType}</label>
						<input type="text" name="prices[${status.index}].value" id="price_${bookType}" />
						<input type="hidden" name="prices[${status.index}].bookType" value="${bookType}"/>
					</div>
				</c:forEach>
			</div>
			<div>
				<input type="submit" value="Enviar">
			</div>
		
		</form:form>
	
	</body>

</html>