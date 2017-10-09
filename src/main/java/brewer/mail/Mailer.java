package brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import brewer.model.Venda;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void enviar(Venda venda) {
	
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("postmaster@sandbox1d93f7aa880d44889b72578267c4477c.mailgun.org");
		mensagem.setTo(venda.getCliente().getEmail());
		mensagem.setSubject("Obrigado por comprar na Brewer");
		mensagem.setText("Sua compra foi realizada com sucesso.");
		
		mailSender.send(mensagem);
	}
}
