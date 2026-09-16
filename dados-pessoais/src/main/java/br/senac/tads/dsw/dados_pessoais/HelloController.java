package br.senac.tads.dsw.dados_pessoais;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mensagem hello() {
        return new Mensagem("Antonio Augusto e Diogo Sena", "Olá, mundo! Meu primerio projeto SpringBoot!");
    }

    @GetMapping(value = "/hello-manual", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloManual() throws JsonProcessingException {
        Mensagem mensagem = new Mensagem("Diogo Sena e Antonio Augusto", "JSON gerado manualmente com ObjectMapper.");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mensagem);
    }
}
