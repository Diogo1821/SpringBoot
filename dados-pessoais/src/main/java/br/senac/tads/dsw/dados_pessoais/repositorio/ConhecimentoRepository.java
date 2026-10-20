package br.senac.tads.dsw.dados_pessoais.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads.dsw.dados_pessoais.entidade.ConhecimentoEntity;

@Repository 
public interface ConhecimentoRepository extends JpaRepository<ConhecimentoEntity, Integer> {

    // Spring Data JPA gera a query automaticamente a partir do nome do método:
    // SELECT * FROM tb_conhecimentos WHERE LOWER(nome) = LOWER(:nome)
    Optional<ConhecimentoEntity> findByNomeIgnoreCase(String nome);

}
