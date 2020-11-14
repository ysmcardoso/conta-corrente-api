package com.labsit.contacorrente.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "agencia")
public class Agencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="nr_ag", nullable = false, unique = true)
    private String nrAg;

    public Agencia() {
    }

    public Agencia(String nrAg) {
        this.nrAg = nrAg;
    }
}
