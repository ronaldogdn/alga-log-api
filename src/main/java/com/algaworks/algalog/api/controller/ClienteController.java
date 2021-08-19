package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

/**
 * Método 3
 * 
 * @AllArgsConstructor Para usar a injeção de dependência sem o Autowired Vai
 *                     criar automaticamente o construtor da classe com o
 *                     clienteRepository
 */
@AllArgsConstructor
//necessário para fazer a requisição http
@RestController
@RequestMapping("/clientes") // para não precisar colocar a rota em @GetMapping("/clientes")
public class ClienteController {
	/**
	 * Método 1 Para criar sem o Autowired também é possível pelo método construtor
	 */
	//public ClienteController(ClienteRepository clienteRepository) { super();
	//this.clienteRepository = clienteRepository; }
	
	/**
	 * Método 2 Colocar o Autowired
	 */
	// @Autowired
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;

	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		// retorna um Optional de Cliente que pode ou não ser nulo
		/*
		 * Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		 * if(cliente.isPresent()) { return ResponseEntity.ok(cliente.get()); } return
		 * ResponseEntity.notFound().build();
		 */
		/**
		 * mesmo código reduzido com lambda
		 */
		return clienteRepository.findById(clienteId)
				// .map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)// método reference java
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
		// se o cliente não existir
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		/**
		 * o Id do cliente quando é passado pelo Body está vazio e então coloca o mesmo
		 * id que está vindo pela URI no cliente para não ser criado outro
		 */
		cliente.setId(clienteId);
		catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		// se o cliente não existir
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
 		catalogoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
}
