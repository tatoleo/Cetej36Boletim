package com.entrega1.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Comparator;

/**
 * DTO para salvar as Notas
 * @aythor LeonardoSilva
 * @since 27/07/2021
 */
@Entity(tableName = "tb_nota")
public class NotaDTO implements Serializable {

    private static final long serialVersionUID = 3815873784709209943L;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String anoLetivo;

    @NonNull
    private String bimestre;

    @NonNull
    private String disciplina;

    @NonNull
    private String professor;

    @NonNull
    private String atividade;

    @NonNull
    private String nota;

    @NonNull
    private Boolean rascunho;

    public NotaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(String anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public String getBimestre() {
        return bimestre;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Boolean getRascunho() {
        return rascunho;
    }

    public void setRascunho(Boolean rascunho) {
        this.rascunho = rascunho;
    }

    @Override
    public String toString() {
        return "Nota:\n" +
                "   anoLetivo:   " + anoLetivo + "\n" +
                "   bimestre   " + bimestre + "\n" +
                "   disciplina   " + disciplina + "\n" +
                "   professor   " + professor + "\n" +
                "   atividade   " + atividade + "\n" +
                "   nota   " + nota + "\n" +
                "   rascunho   " + rascunho + "\n"
                ;
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
