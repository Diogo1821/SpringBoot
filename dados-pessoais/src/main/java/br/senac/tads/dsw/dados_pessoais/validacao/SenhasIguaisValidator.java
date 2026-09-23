package br.senac.tads.dsw.dados_pessoais.validacao;

import br.senac.tads.dsw.dados_pessoais.PessoaDto;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class SenhasIguaisValidator
    implements ConstraintValidator<SenhasIguais, PessoaDto> {

        private String mensagem;

        @Override
        public void initialize(SenhasIguais annotation) {
            //Lê a mensagem configurada na anotação @SenhasIguais
            this.mensagem = annotation.message();
        }

        @Override
        public boolean isValid(PessoaDto pessoaDto, ConstraintValidatorContext context) {
            boolean resultado = pessoaDto.getSenha() != null && pessoaDto.getSenha().equals(pessoaDto.getSenhaRepeticao());

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
