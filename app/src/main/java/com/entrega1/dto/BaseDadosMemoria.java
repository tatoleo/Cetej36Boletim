package com.entrega1.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe responsável por salvar o array de NotasDTO.
 * @aythor LeonardoSilva
 * @since 27/07/2021
 */
public class BaseDadosMemoria implements Serializable {

    private static final long serialVersionUID = -1310951989811136948L;

    private ArrayList<NotaDTO> listaNotas;

    public BaseDadosMemoria() {

    }

    public BaseDadosMemoria(ArrayList<NotaDTO> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public ArrayList<NotaDTO> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(ArrayList<NotaDTO> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @Override
    public String toString() {
        return "BaseDadosMemoria{" +
                "listaNotas=" + listaNotas +
                '}';
    }
}
