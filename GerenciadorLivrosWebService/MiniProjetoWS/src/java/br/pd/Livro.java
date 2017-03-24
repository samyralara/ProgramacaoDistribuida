/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pd;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author samyra
 */
@Entity
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;
    
    private String titulo;
    private String autor;
    private String editora;
    private String edicao;
    private String ISBN;


    
    public Livro(){
    }
 
    
    public Livro(String autor, String titulo, String editora, String edicao, String ISBN) {
        this.autor = autor;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.ISBN = ISBN;

        
    }
    
 
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        hash += (autor != null ? autor.hashCode() : 0);
        hash += (titulo != null ? titulo.hashCode() : 0);
        hash += (editora != null ? editora.hashCode() : 0);
        hash += (edicao != null ? edicao.hashCode() : 0);
        hash += (ISBN != null ? ISBN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        if ((this.titulo == null && other.titulo != null) || (this.titulo != null && !this.titulo.equals(other.titulo))) {
            return false;
        }
        if ((this.autor == null && other.autor != null) || (this.autor != null && !this.autor.equals(other.autor))) {
            return false;
        }
        if ((this.editora == null && other.editora != null) || (this.editora != null && !this.editora.equals(other.editora))) {
            return false;
        }
        if ((this.edicao == null && other.edicao != null) || (this.edicao != null && !this.edicao.equals(other.edicao))) {
            return false;
        }
        if ((this.ISBN == null && other.ISBN != null) || (this.ISBN != null && !this.ISBN.equals(other.ISBN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Codigo = " + codigo + " Autor = " + autor + " Titulo = " +titulo +
                " Editora = " + editora+ " Edicao = " + edicao + " ISBN = " + ISBN;
    }
    
}
