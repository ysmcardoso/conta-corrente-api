package com.labsit.contacorrente.model;


import com.labsit.contacorrente.model.dominio.DominioTransacao;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_transacao")
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name="data", nullable = false)
    private LocalDateTime data;

    @Column(name="valor", nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name="id_tp", nullable = false)
    private DominioTransacao dominioTransacao;

    @OneToOne
    @JoinColumns({
           @JoinColumn(name = "id.nr_ag", nullable = false),
            @JoinColumn(name = "id.nr_cta", nullable = false)
    })
    private ContaCorrente contaCorrente;

    public Transacao() {
        contaCorrente = new ContaCorrente();
    }
}
