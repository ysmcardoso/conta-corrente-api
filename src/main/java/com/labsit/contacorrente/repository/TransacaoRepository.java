package com.labsit.contacorrente.repository;

import com.labsit.contacorrente.model.ContaCorrente;
import com.labsit.contacorrente.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query("select a from Transacao a where a.data >= :dataInicial and a.data <= :dataFinal and a.contaCorrente = :contaCorrente order by a.data desc")
    Optional<List<Transacao>> findTransacaoByContaCorrenteAndPeriodo(@Param("contaCorrente") ContaCorrente contaCorrente,
                                                                     @Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);
}
