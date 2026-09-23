package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    private PessoaController (PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<PessoaDto> obterPessoas() {
        return pessoaService.obterPessoas();
    }

    @GetMapping("/{username}")
    public PessoaDto obterPessoa(@PathVariable("username") String username) {
        Optional<PessoaDto> optPessoa = pessoaService.obterPessoa(username);
        if (optPessoa.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optPessoa.get();
    }

    @PostMapping("/sem-validacao")
    public ResponseEntity<?> incluirNovo(@RequestBody PessoaDto pessoaDto) {
        pessoaService.incluirNovaPessoa(pessoaDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/pessoas/{username}")
            .buildAndExpand(pessoaDto.getUsername())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<?> incluirNovaComValidacao(@RequestBody @Valid PessoaDto pessoaDto) {

      /*@Valid ativa a validação das anotações definidas na classe Pessoa.
        Se qualquer restrição for violada, o Spring retorna automaticamente
        HTTP 400 Bad Request descrevendo os campos inválidos — sem nenhum código adicional no controller */
        pessoaService.incluirNovaPessoa(pessoaDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/pessoas/{username}")
            .buildAndExpand(pessoaDto.getUsername())
            .toUri();
        return ResponseEntity.created(location).build();
    }

	@PutMapping("/{username}")
	public ResponseEntity<?> atualizar(@PathVariable("username") String username, @RequestBody @Valid PessoaAlteracaoDto pessoa) {
		PessoaDto pessoaDtoAlterada = pessoaService.alteraPessoa(username, pessoa);
		return ResponseEntity.ok().body(pessoaDtoAlterada);
	}

	@DeleteMapping({"/{username}"})
	public ResponseEntity<?> remover(@PathVariable("username") String username) {
		pessoaService.removerPessoa(username);
		return ResponseEntity.noContent().build(); //Erro 204
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<ProblemDetail> tratarExecao(NaoEncontradoException ex) {
		// ProblemDetail - padrão RFC 7807 para representar erros HTTP
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage());
		return ResponseEntity.of(pd).build();
	}
}
