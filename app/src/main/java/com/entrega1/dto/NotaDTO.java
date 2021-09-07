package com.entrega1.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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

}
