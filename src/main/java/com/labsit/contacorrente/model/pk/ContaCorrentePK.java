package com.labsit.contacorrente.model.pk;

import com.labsit.contacorrente.model.Agencia;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class ContaCorrentePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="nr_cta", nullable = false, unique = true)
    private String nrCta;

    @OneToOne
    @JoinColumn(name="nr_ag", nullable = false)
    private Agencia agencia;

    public ContaCorrentePK() {
        agencia = new Agencia();
    }

    public ContaCorrentePK(String nrCta, String nrAgencia) {
        Agencia agencia = new Agencia(nrAgencia);
        this.nrCta = nrCta;
        this.agencia = agencia;
    }
}
