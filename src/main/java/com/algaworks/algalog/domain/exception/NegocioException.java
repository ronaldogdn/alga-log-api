package com.algaworks.algalog.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		//passa a mensagem para a super classe
		super(message);
	}
}
