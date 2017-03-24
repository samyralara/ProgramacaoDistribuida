/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pd;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Samyra e Evangelista
 */
@WebService(serviceName = "GerenciadorWS")
public class GerenciadorWS {
    
@PersistenceContext(unitName="MiniProjetoWSPU")
private EntityManager em;
    
@Resource
private UserTransaction usert;
    

/* Cadastrar livro */

    @WebMethod(operationName = "cadastrar")
    public String cadastrar(@WebParam(name = "autor")String autor, @WebParam(name = "titulo")String titulo, @WebParam(name = "edicao")String edicao, @WebParam(name = "editora")String editora, @WebParam(name = "isbn")String isbn) throws SystemException, NotSupportedException, SecurityException{
    usert.begin();
    Livro livro = new Livro(autor, titulo, edicao,editora,isbn);
    em.persist(livro);
    try {
        usert.commit();
    } catch (RollbackException ex) {
        Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
    } catch (HeuristicMixedException ex) {
        Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
    } catch (HeuristicRollbackException ex) {
        Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalStateException ex) {
        Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "Cadastrado com sucesso!";
    //usert.commit();
    }
    
    /* Alterar Livro */
    @WebMethod(operationName = "alterar")
    public String alterar(@WebParam(name = "codigo")Long codigo, @WebParam(name = "autor")String autor, 
    @WebParam(name = "titulo")String titulo, @WebParam(name = "edicao")String edicao, 
    @WebParam(name = "editora")String editora, @WebParam(name = "isbn")String isbn) throws SystemException, NotSupportedException, SecurityException{
      usert.begin();
      Livro liv = em.find(Livro.class,codigo);
        if (liv == null) {
            return "Este livro não existe";
        } else {
            liv.setAutor(autor);
            liv.setEdicao(edicao);
            liv.setEditora(editora);
            liv.setISBN(isbn);
            liv.setTitulo(titulo);
            em.merge(liv);
          try {
              usert.commit();
          } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | IllegalStateException ex) {
              Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
          }
            System.out.println("ATUALIZAÇÃO do livro "+liv.toString());
            return "Livro atualizado!";
        }
    }
    
      /* Excluir Livro */  
    @WebMethod(operationName = "excluir")
    public String excluir(@WebParam(name = "codigo") Long codigo) {
        try {
                 
            Livro livro = em.find(Livro.class, codigo);
            
            if (livro != null) {
                usert.begin();
                livro = em.merge(livro);
                em.remove(livro);
                usert.commit();
                return "EXCLUSAO do livro "+ livro.toString();
            } else {
                return "Nao existe esse livro";
            }
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(GerenciadorWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }   
    
 //Consultando livro por ISBN   
    @WebMethod(operationName = "consultar")
    public String consultar(@WebParam(name = "isbn") String isbn) throws SystemException, NotSupportedException {
        
        List <Livro> livro;
        usert.begin();
        TypedQuery<Livro> q = em.createQuery("SELECT li FROM Livro li WHERE li.ISBN = '" + isbn + "'", Livro.class);
        livro = q.getResultList();
        if(!livro.isEmpty()){
            for(Livro liv: livro){
                return "Livro: " + liv.toString();
            }
        }
        else{
            return "Este livro nao esta cadastrado";
        }
    return null;
    }
}
