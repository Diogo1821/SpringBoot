package br.senac.tads.dsw.dados_pessoais;

// RuntimeException não obriga o chamador a usar Try/Catch

public class NaoEncontradoException extends RuntimeException{
	public NaoEncontradoException (String mensagem) {
		super(mensagem);
	}
}
