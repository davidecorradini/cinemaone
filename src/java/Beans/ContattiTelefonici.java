/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.math.BigInteger;

/**
 *
 * @author enrico
 */
public class ContattiTelefonici {
    private String nome;
    private BigInteger numeroTelefonico;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the numeroTelefonico
     */
    public BigInteger getNumeroTelefonico() {
        return numeroTelefonico;
    }

    /**
     * @param numeroTelefonico the numeroTelefonico to set
     */
    public void setNumeroTelefonico(BigInteger numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }
}
