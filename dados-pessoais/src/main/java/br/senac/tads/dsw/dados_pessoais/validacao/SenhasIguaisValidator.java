package br.senac.tads.dsw.dados_pessoais.validacao;

import org.springframework.stereotype.Component;

import br.senac.tads.dsw.dados_pessoais.Pessoa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component 
public class SenhasIguaisValidator 
    implements ConstraintValidator<SenhasIguais, Pessoa> {

        private String mensagem;
        
        @Override
        public void initialize(SenhasIguais annotation) {
            //Lê a mensagem configurada na anotação @SenhasIguais
            this.mensagem = annotation.message();
        }

        @Override 
        public boolean isValid(Pessoa pessoa, ConstraintValidatorContext context) {
            boolean resultado = pessoa.getSenha() != null && pessoa.getSenha().equals(pessoa.getSenhaRepeticao());

            if (!resultado) {
                //Associa o erro ao campo "senha" em vez de á classe toda
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(mensagem).addPropertyNode("senha").addConstraintViolation();
             /* Por que addPropertyNode("senha")?
                Como @SenhasIguais é aplicada à classe, o erro seria associado ao objeto inteiro —
                sem indicar qual campo está errado. Este código "move" o erro para o campo ,
                o que facilita a exibição da mensagem no formulário HTML */
            }
            return resultado;
        }
    }
