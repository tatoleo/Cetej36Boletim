package com.entrega1.dto;

import com.entrega1.formCadastro.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Classe responsável por salvar o array de NotasDTO.
 * @aythor LeonardoSilva
 * @since 27/07/2021
 */
public class BaseDadosMemoria implements Serializable {

    private static final long serialVersionUID = -1310951989811136948L;

    private static BaseDadosMemoria instancia;

    private ArrayList<NotaDTO> listaNotas;

    public BaseDadosMemoria() {
        listaNotas = new ArrayList<>();
    }

    public static synchronized BaseDadosMemoria getInstance() {
        if (instancia == null) {
            instancia = new BaseDadosMemoria();
        }
        return instancia;
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

    /**
     * Classe interna para ordenação
     */
    public static class ComparadorNotas implements Comparator<NotaDTO> {
        public static final int POR_BIMESTRE = 1;
        public static final int POR_DISCIPLINA = 2;
        public static final int POR_ATIVIDADE = 3;
        public static final int POR_NOTA = 4;
        int tipoComparacao;

        public ComparadorNotas(int tipoComparacao) {
            this.tipoComparacao = tipoComparacao;
        }

        @Override
        public int compare(NotaDTO objeto1, NotaDTO objeto2) {
            int retorno = 0;

            switch (this.tipoComparacao) {
                case POR_BIMESTRE:
                    retorno = objeto1.getBimestre().compareTo(objeto2.getBimestre());
                    break;
                case POR_DISCIPLINA:
                    retorno = objeto1.getDisciplina().compareTo(objeto2.getDisciplina());
                    break;
                case POR_ATIVIDADE:
                    retorno = objeto1.getAtividade().compareTo(objeto2.getAtividade());
                    break;
                case POR_NOTA:
                    retorno = objeto1.getNota().compareTo(objeto2.getNota());
                    break;
                default:
                    throw new RuntimeException();
            }
            return retorno;
        }
    };
}
