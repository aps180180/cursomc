package com.andresantos.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andresantos.cursomc.domain.ItemPedido;
import com.andresantos.cursomc.domain.PagamentoComBoleto;
import com.andresantos.cursomc.domain.Pedido;
import com.andresantos.cursomc.domain.enums.EstadoPagamento;
import com.andresantos.cursomc.repositories.ItemPedidoRepository;
import com.andresantos.cursomc.repositories.PagamentoRepository;
import com.andresantos.cursomc.repositories.PedidoRepository;
import com.andresantos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find( Integer id) {
		Optional <Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
	}
	
	@Transactional
	public Pedido insert (Pedido obj) {
		obj.setId(null);
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());			
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip :obj.getItens()) {
			ip.setDesconto(0.00);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
		
	}
}
