/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sccd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sccd.jdbc.ConnectionFactory;
import sccd.model.Desenhos;

/**
 *
 * @author mrs_a
 */
public class DesenhosDAO {
    
    private Connection con;
    
    public DesenhosDAO(){
        this.con = new ConnectionFactory().getConnection();
    }
    
    //Metodo cadastrar
    public void cadastrarDesenhos(Desenhos obj){
        try {
            //Cria comando sql
            String sql = "insert into tb_desenhos (faca, comprimento, largura, altura, colagem, abas, "
                    +"berco, promocional, operador, datahora) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getFaca());
            stmt.setFloat(2, obj.getComprimento());
            stmt.setFloat(3, obj.getLargura());
            stmt.setFloat(4, obj.getAltura());
            stmt.setString(5, obj.getColagem());
            stmt.setString(6, obj.getAbas());
            stmt.setString(7, obj.getBerco());
            stmt.setString(8, obj.getPromocional());
            stmt.setString(9, obj.getOperador());
            stmt.setString(10, obj.getDatahora());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Alterar
    public void alterarDesenhos(Desenhos obj){
        try {
            //Cria comando sql
            String sql = "update tb_desenhos set "
                    +"faca=?, comprimento=?, largura=?, altura=?, colagem=?, abas=?, berco=?, promocional=?, operador=?, datahora=? "
                    +"where id=?";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getFaca());
            stmt.setFloat(2, obj.getComprimento());
            stmt.setFloat(3, obj.getLargura());
            stmt.setFloat(4, obj.getAltura());
            stmt.setString(5, obj.getColagem());
            stmt.setString(6, obj.getAbas());
            stmt.setString(7, obj.getBerco());
            stmt.setString(8, obj.getPromocional());
            stmt.setString(9, obj.getOperador());
            stmt.setString(10, obj.getDatahora());
            stmt.setInt(11, obj.getId());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Excluir
    public void excluirDesenhos(Desenhos obj){
        try {
            //Cria o comando sql
            String sql = "delete from tb_desenhos where id";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Listar
    public List<Desenhos> listarDesenhos(){
        try {
            //Cria Lista
            List<Desenhos> lista = new ArrayList<>();
            
            //Cria comando sql
            String sql = "select * from tb_desenhos";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                
                Desenhos obj = new Desenhos();
                obj.setId(rs.getInt("id"));
                obj.setFaca(rs.getInt("faca"));
                obj.setComprimento(rs.getFloat("comprimento"));
                obj.setLargura(rs.getFloat("largura"));
                obj.setAltura(rs.getFloat("altura"));
                obj.setColagem(rs.getString("colagem"));
                obj.setAbas(rs.getString("abas"));
                obj.setBerco(rs.getString("berco"));
                obj.setPromocional(rs.getString("promocional"));
                obj.setOperador(rs.getString("operador"));
                obj.setDatahora(rs.getString("datahora"));
                
                //Executa
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }
    }
    
    //Metoda Pesquisar
    public List<Desenhos> pesquisarDesenhos(String faca, String comprimento, String largura, String altura, 
            String colagem, String abas, String berco, String promocional, String operador){
        
        try {
            //Cria a Lista
            List<Desenhos> lista = new ArrayList<>();
            
            //Cria comando sql
            String sql = "select * from tb_desenhos where faca like? and comprimento <=? and comprimento >=? and "
                    +"largura <=? and largura >=? and altura <=? and altura >=?";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, faca);            
            stmt.setString(2, comprimento);            
            stmt.setString(3, comprimento);            
            stmt.setString(4, largura);            
            stmt.setString(5, largura);            
            stmt.setString(6, altura);            
            stmt.setString(7, altura);            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                
                Desenhos obj = new Desenhos();
                obj.setId(rs.getInt("id"));
                obj.setFaca(rs.getInt("faca"));
                obj.setComprimento(rs.getFloat("comprimento"));
                obj.setLargura(rs.getFloat("largura"));
                obj.setAltura(rs.getFloat("altura"));
                obj.setColagem(rs.getString("colagem"));
                obj.setAbas(rs.getString("abas"));
                obj.setBerco(rs.getString("berco"));
                obj.setPromocional(rs.getString("promocional"));
                obj.setOperador(rs.getString("operador"));
                obj.setDatahora(rs.getString("datahora"));
                
                //Executa
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }
    }
    
}
