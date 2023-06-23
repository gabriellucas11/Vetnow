package com.example.appvetnow.activities.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vetnow implements Serializable {
    private String codigo;
    private String nome;
    private String descricao;
    private Date dataCriacao;

    public Vetnow() {
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Vetnow{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vetnow)) return false;
        Vetnow that = (Vetnow) o;
        return Objects.equals(getCodigo(), that.getCodigo()) &&
                Objects.equals(getNome(), that.getNome()) &&
                Objects.equals(getDescricao(), that.getDescricao()) &&
                Objects.equals(getDataCriacao(), that.getDataCriacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNome(), getDescricao(), getDataCriacao());
    }

}
