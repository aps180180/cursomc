package com.andresantos.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.andresantos.cursomc.domain.Pedido;

public abstract class AbstractMailService implements EmailService {
	@Value("${default.sender}") // pega o valor da chave no aplication.properties e joja na variavel sender
	private String sender;
	
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendMail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido conformado! Código : " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()) );
		sm.setText(obj.toString());
		
		return sm;
	};

}
