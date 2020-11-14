package com.labsit.contacorrente.service;

import com.labsit.contacorrente.model.ContaCorrente;
import com.labsit.contacorrente.model.Transacao;
import com.labsit.contacorrente.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private TransacaoRepository transacaoRepository;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public Optional<List<Transacao>> buscarTransacoesByPeriodo(ContaCorrente contaCorrente, LocalDate dataInicial, LocalDate dataFinal) {
        LocalDateTime newdataInicial = dataInicial.atStartOfDay();
        LocalDateTime newdataFinal = dataFinal.atTime(23,59);
        return transacaoRepository.findTransacaoByContaCorrenteAndPeriodo(contaCorrente, newdataInicial, newdataFinal);
    }

    public void salvar(Transacao transacao){
        transacaoRepository.save(transacao);
    }
}
