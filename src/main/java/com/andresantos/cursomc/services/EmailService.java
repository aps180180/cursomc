package com.andresantos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.andresantos.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendMail(SimpleMailMessage msg);
	
}
