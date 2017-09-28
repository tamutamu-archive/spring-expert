package brewer.validation.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import brewer.validation.AtributoConformacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConformacao, Object>{
	
	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConformacao constraintAnnotation) {
		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
	}
	
	@Override
	public boolean isValid(Object objeto, ConstraintValidatorContext context) {
		
		boolean valido = false;
		
		try {
			Object valorAtributo = BeanUtils.getProperty(objeto, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(objeto, this.atributoConfirmacao);
			
			valido = ambosSaoNull(valorAtributo, valorAtributoConfirmacao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao); 
			
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos " , e);
		}
		
		if(!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean ambosSaoNull(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo == null && valorAtributoConfirmacao == null;
	}

}
