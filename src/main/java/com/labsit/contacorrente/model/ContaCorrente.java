package com.labsit.contacorrente.model;

import com.labsit.contacorrente.model.pk.ContaCorrentePK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "conta_corrente")
public class ContaCorrente implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ContaCorrentePK id;

    @Column(name="nr_cta", insertable = false, updatable = false)
    private String nrCta;

    @Column(name="nr_ag", insertable = false, updatable = false)
    private String nrAg;

    @Column(name = "sld", nullable = false)
    private BigDecimal sld;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf_cnpj")
    private Pessoa pessoa;

    public ContaCorrente() {
        id = new ContaCorrentePK();
        pessoa = new Pessoa();

    }
}
