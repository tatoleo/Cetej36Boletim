package com.entrega1.dto;

import java.io.Serializable;

/**
 * DTO para salvar as Notas
 * @aythor LeonardoSilva
 * @since 27/07/2021
 */
public class NotaDTO implements Serializable {

    private static final long serialVersionUID = 3815873784709209943L;

    private String anoLetivo;
    private String bimestre;
    private String disciplina;
    private String professor;
    private String atividade;
    private String nota;
    private Boolean rascunho;

    public NotaDTO() {
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

}
