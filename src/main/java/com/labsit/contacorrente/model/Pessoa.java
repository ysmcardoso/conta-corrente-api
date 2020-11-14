package com.labsit.contacorrente.model;

import com.labsit.contacorrente.model.dominio.DominioTipoPessoa;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj;

    @Column(name="nome", nullable = false, unique = true)
    private String nome;

    @Column(name="nr_fone", nullable = false)
    private String nrFone;

    @Column(name="tp_pess", nullable = false)
    @Enumerated(EnumType.STRING)
    private DominioTipoPessoa tpPess;
}
