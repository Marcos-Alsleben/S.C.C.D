/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sccd.view;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sccd.dao.AbasFundoAutomaticoDAO;
import sccd.dao.AbasLateralDAO;
import sccd.dao.AbasOutrosDAO;
import sccd.dao.CaminhoArteDAO;
import sccd.dao.ContaDAO;
import sccd.dao.DesenhosDAO;
import sccd.dao.EmpresaDAO;
import sccd.model.AbasFundoAutomatico;
import sccd.model.AbasLateral;
import sccd.model.AbasOutros;
import sccd.model.CaminhoArte;
import sccd.model.Conta;
import sccd.model.Desenhos;
import sccd.model.Empresa;

/**
 *
 * @author mrs_a
 */
public class FrmPrincipal extends javax.swing.JFrame {

    //Metoto para acesso convidado
    public void AcessoConvidado() {

        lb_usuario.setText("Convidado");
        btn_cadastrarDesenho.setEnabled(false);
        btn_configurar.setEnabled(false);
        btn_administrar.setEnabled(false);
        btn_logout.setEnabled(false);
        btn_editarDesenho.setEnabled(false);
        btn_escluirDesenho.setEnabled(false);
    }

    //Metodo para acesso Administrador
    public void AcessoAdmin() {

        btn_cadastrarDesenho.setEnabled(true);
        btn_configurar.setEnabled(true);
        btn_administrar.setEnabled(true);
        btn_logout.setEnabled(true);
        btn_editarDesenho.setEnabled(true);
        btn_escluirDesenho.setEnabled(true);
    }

    //Metodo para acesso usuario
    public void AcessoUsuario() {

        btn_cadastrarDesenho.setEnabled(true);
        btn_configurar.setEnabled(true);
        btn_logout.setEnabled(true);
        btn_editarDesenho.setEnabled(true);
        btn_escluirDesenho.setEnabled(true);
    }

    // Metodo Pegar Data e Hora
    public String DH() {
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = formatar.format(data);

        return dataFormatada;
    }

    //Metodo atualizar campo local de arquivos para visualização
    public void AtualizaCaminhoArte() {

        CaminhoArteDAO dao = new CaminhoArteDAO();
        List<CaminhoArte> lista = dao.listar();

        for (CaminhoArte c : lista) {
            txt_localPasta.setText(c.getEndereco());

        }
    }

    //Metodo atualizar campos de cadastro Empresa
    public void AtualizaEmpresa() {

        EmpresaDAO dao = new EmpresaDAO();
        List<Empresa> lista = dao.listar();

        for (Empresa c : lista) {
            txt_nomeEmpresa.setText(c.getNome());
            txt_enderecoEmpresa.setText(c.getEndereco());
            txt_telefoneEmpresa.setText(c.getTelefone());
            txt_obsEmpresa.setText(c.getObservacao());
            lb_empresa.setText(c.getNome());

        }
    }

    // Metodo pesquisar na lista  de desenhos
    public void PesquisarDesenhos() {
        String faca = "%" + txt_pesquisaFaca.getText() + "%";
        String comprimento = txt_pesquisaComprimento.getText().replace(",", ".") + "%";
        String largura = txt_pesquisaLargura.getText().replace(",", ".") + "%";
        String altura = txt_pesquisaAltura.getText().replace(",", ".") + "%";
        String colagem;
        String abas;

        if ("*".equals(cb_pesquisaColagem.getSelectedItem())) {
            colagem = "%";
        } else {
            colagem = "%" + cb_pesquisaColagem.getSelectedItem().toString() + "%";
        }
        if ("*".equals(cb_pesquisaAbas.getSelectedItem())) {
            abas = "%";
        } else {
            abas = "%" + cb_pesquisaAbas.getSelectedItem().toString() + "%";
        }

        DesenhosDAO dao = new DesenhosDAO();
        List<Desenhos> lista = dao.pesquisar(faca, comprimento, largura, altura, colagem, abas);
        DefaultTableModel dados = (DefaultTableModel) tb_desenhos.getModel();
        dados.setNumRows(0);
        for (Desenhos c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getFaca(),
                c.getComprimento(),
                c.getLargura(),
                c.getAltura(),
                c.getColagem(),
                c.getAbas(),
                c.getBerco(),
                c.getPromocional(),});

        }
    }

    // Metodo pesquisar na lista  de desenhos relatorio admin
    public void PesquisaAdmin() {
        String cadastradopor;
        String datahora = "1000";

        if ("*".equals(cb_usuarioRelatorio.getSelectedItem())) {
            cadastradopor = "%";
        } else {
            cadastradopor = cb_usuarioRelatorio.getSelectedItem().toString();
        }
        switch (cb_mesesRelatorio.getSelectedItem().toString()) {

            case "1 Mês":
                datahora = "1";
                break;

            case "2 Meses":
                datahora = "2";
                break;

            case "3 Meses":
                datahora = "3";
                break;

            case "6 Meses":
                datahora = "6";
                break;

            case "12 Meses":
                datahora = "12";
                break;

            case "24 Meses":
                datahora = "24";
                break;

            case "36 Meses":
                datahora = "36";
                break;

            default:
                break;
        }

        DesenhosDAO dao = new DesenhosDAO();
        List<Desenhos> lista = dao.pesquisarAdmin(cadastradopor, datahora);
        DefaultTableModel dados = (DefaultTableModel) tb_relatorio.getModel();
        dados.setNumRows(0);
        for (Desenhos c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getFaca(),
                c.getComprimento(),
                c.getLargura(),
                c.getAltura(),
                c.getCadastradopor(),
                c.getDatahora(),});

        }
    }

    //Metodo cadastrar desenho
    public void CadastrarDesenho() {
        if ("".equals(txt_cadastrarFaca.getText())) {
            JOptionPane.showMessageDialog(null, "Campo faca Inválido!", "", 2);
        } else {
            if ("".equals(txt_cadastrarComprimento.getText())) {
                JOptionPane.showMessageDialog(null, "Campo Comprimento Inválido!", "", 2);
            } else {
                if ("".equals(txt_cadastrarLargura.getText())) {
                    JOptionPane.showMessageDialog(null, "Campo Largura Inválido!", "", 2);
                } else {
                    if ("".equals(txt_cadastrarAltura.getText())) {
                        JOptionPane.showMessageDialog(null, "Campo Altura Inválido!", "", 2);
                    } else {
                        if ("*".equals(cb_cadastrarColagem.getSelectedItem().toString())) {
                            JOptionPane.showMessageDialog(null, "Campo Colagem Iválido!", "", 2);
                        } else {
                            if ("*".equals(cb_cadastrarAbas.getSelectedItem().toString())) {
                                JOptionPane.showMessageDialog(null, "Campo Abas Iválido!", "", 2);
                            } else {
                                if ("*".equals(cb_cadastrarBerco.getSelectedItem().toString())) {
                                    JOptionPane.showMessageDialog(null, "Campo Berço Iválido!", "", 2);
                                } else {
                                    if ("*".equals(cb_cadastrarPromocional.getSelectedItem().toString())) {
                                        JOptionPane.showMessageDialog(null, "Campo Promocional Iválido!", "", 2);
                                    } else {

                                        Desenhos obj = new Desenhos();
                                        obj.setFaca(Integer.parseInt(txt_cadastrarFaca.getText()));
                                        obj.setComprimento(Float.parseFloat(txt_cadastrarComprimento.getText().replace(",", ".")));
                                        obj.setLargura(Float.parseFloat(txt_cadastrarLargura.getText().replace(",", ".")));
                                        obj.setAltura(Float.parseFloat(txt_cadastrarAltura.getText().replace(",", ".")));
                                        obj.setColagem(cb_cadastrarColagem.getSelectedItem().toString());
                                        obj.setAbas(cb_cadastrarAbas.getSelectedItem().toString());
                                        obj.setBerco(cb_cadastrarBerco.getSelectedItem().toString());
                                        obj.setPromocional(cb_cadastrarPromocional.getSelectedItem().toString());
                                        obj.setCadastradopor(lb_usuario.getText());
                                        obj.setDatahora(DH());

                                        if ("".equals(lb_cadastroId.getText())) {

                                            DesenhosDAO dao = new DesenhosDAO();
                                            dao.cadastrar(obj);

                                            LimpaCamposCadastro();

                                        } else {

                                            obj.setId(Integer.parseInt(lb_cadastroId.getText()));

                                            DesenhosDAO dao = new DesenhosDAO();
                                            dao.alterar(obj);

                                            LimpaCamposCadastro();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Metodo listar desenhos
    public void ListarDesenhos() {
        DesenhosDAO dao = new DesenhosDAO();
        List<Desenhos> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_desenhos.getModel();
        dados.setNumRows(0);
        for (Desenhos c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getFaca(),
                c.getComprimento(),
                c.getLargura(),
                c.getAltura(),
                c.getColagem(),
                c.getAbas(),
                c.getBerco(),
                c.getPromocional()
            });

        }
    }
    
    //Metodo listar relatorio
    public void ListarRelatorio() {
        DesenhosDAO dao = new DesenhosDAO();
        List<Desenhos> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_relatorio.getModel();
        dados.setNumRows(0);
        for (Desenhos c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getFaca(),
                c.getComprimento(),
                c.getLargura(),
                c.getAltura(),
                c.getCadastradopor(),
                c.getDatahora()
            });

        }
    }

    //Metodo listar Usuários
    public void ListarUsuarios() {
        ContaDAO dao = new ContaDAO();
        List<Conta> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_usuario.getModel();
        dados.setNumRows(0);
        for (Conta c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getSenha(),
                c.getPrivilegio(),});

        }
    }

    //Metodo Atualizar cb_usuarios
    public void Atualiza_cbUsuarios() {
        ContaDAO dao = new ContaDAO();
        List<Conta> lista = dao.listar();

        cb_usuarioRelatorio.removeAllItems();
        cb_usuarioRelatorio.addItem("*");

        for (Conta c : lista) {
            cb_usuarioRelatorio.addItem(c.getNome());

        }
    }

    //Metodo listar Abas Lateral
    public void ListarAbasLateral() {
        AbasLateralDAO dao = new AbasLateralDAO();
        List<AbasLateral> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_abasLateral.getModel();
        dados.setNumRows(0);
        for (AbasLateral c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getTipo(),});

        }
    }

    //Metodo listar Abas Fundo Automatico
    public void ListarAbasFundoAutomatico() {
        AbasFundoAutomaticoDAO dao = new AbasFundoAutomaticoDAO();
        List<AbasFundoAutomatico> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_abasFundoAutomatico.getModel();
        dados.setNumRows(0);
        for (AbasFundoAutomatico c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getTipo(),});

        }
    }

    //Metodo listar Abas Outros
    public void ListarAbasOutros() {
        AbasOutrosDAO dao = new AbasOutrosDAO();
        List<AbasOutros> lista = dao.listar();
        DefaultTableModel dados = (DefaultTableModel) tb_abasOutros.getModel();
        dados.setNumRows(0);
        for (AbasOutros c : lista) {
            dados.addRow(new Object[]{
                c.getId(),
                c.getTipo(),});

        }
    }

    //Metodo cadastrar Caminho Arte
    public void AlterarCaminhoArte() {
        CaminhoArte obj = new CaminhoArte();
        obj.setEndereco(txt_localPasta.getText());
        obj.setCadastradopor(lb_usuario.getText());
        obj.setDatahora(DH());
        obj.setId(1);

        CaminhoArteDAO dao = new CaminhoArteDAO();
        dao.alterar(obj);
    }

    //Metodo cadastrar Empresa
    public void AlterarEmpresa() {
        Empresa obj = new Empresa();
        obj.setNome(txt_nomeEmpresa.getText());
        obj.setEndereco(txt_enderecoEmpresa.getText());
        obj.setTelefone(txt_telefoneEmpresa.getText());
        obj.setObservacao(txt_obsEmpresa.getText());
        obj.setCadastradopor(lb_usuario.getText());
        obj.setDatahora(DH());
        obj.setId(1);

        EmpresaDAO dao = new EmpresaDAO();
        dao.alterar(obj);
    }

    //Metodo mostrar cardLayout do jPanelPrincipal
    public void MostraCard_jPanelPrincipal(String card) {
        CardLayout cl = (CardLayout) jPanelPrincipal.getLayout();
        cl.show(jPanelPrincipal, card);
    }

    //Metodo mostrar cardLayout do jPanelAdminSubPrincipal
    public void MostraCard_jPanelAdminSubPrincipal(String card) {
        CardLayout cl = (CardLayout) jPanelAdminSubPrincipal.getLayout();
        cl.show(jPanelAdminSubPrincipal, card);
    }

    //Metodo limpar campos de cadastro
    public void LimpaCamposCadastro() {

        lb_cadastroId.setText("");
        txt_cadastrarFaca.setText("");
        txt_cadastrarComprimento.setText("");
        txt_cadastrarLargura.setText("");
        txt_cadastrarAltura.setText("");
        cb_cadastrarColagem.setSelectedItem("*");
        cb_cadastrarAbas.setSelectedItem("*");
        cb_cadastrarBerco.setSelectedItem("*");
        cb_cadastrarPromocional.setSelectedItem("*");

        txt_cadastrarFaca.setEnabled(true);
    }

    //Metodo limpar campos de login
    public void LimpaCamposLogin() {
        txt_loginNome.setText("");
        txt_loginSenha.setText("");
    }

    //Metodo limpar campos Usuários
    public void LimpaCamposUsuarios() {
        txt_nomeUsuario.setText("");
        txt_senhaUsuario.setText("");
        cb_privilegioUsuario.setSelectedItem("*");
        lb_idUsuario.setText("");
    }

    //Metodo limpar campos de pesquisa
    public void LimpaCamposPesquisa() {
        txt_pesquisaFaca.setText("");
        txt_pesquisaComprimento.setText("");
        txt_pesquisaLargura.setText("");
        txt_pesquisaAltura.setText("");
        cb_pesquisaColagem.setSelectedItem("*");
        cb_pesquisaAbas.setSelectedItem("*");
    }

    //Metodo limpar campos de cadastro abas config
    public void LimpaCamposCadastroAbas() {

        txt_cadastroAbasConfig.setText("");
        cb_cadastrarAbasConfig.setSelectedItem("*");
        lb_idAbasConfig.setText("");

    }

    //Metodo limpar campos Empresa
    public void LimpaCamposEmpresa() {
        txt_nomeEmpresa.setText("");
        txt_enderecoEmpresa.setText("");
        txt_telefoneEmpresa.setText("");
        txt_obsEmpresa.setText("");
    }

    //Metodo Atualizar cb_cadastrarAbas conforme seleção do cb_cadastrarColagem
    public void Atualiza_cbCadastrarAbas() {
        if (cb_cadastrarColagem.getSelectedItem() == "Lateral") {
            AbasLateralDAO dao = new AbasLateralDAO();
            List<AbasLateral> lista = dao.listar();

            cb_cadastrarAbas.removeAllItems();
            cb_cadastrarAbas.addItem("*");

            for (AbasLateral c : lista) {
                cb_cadastrarAbas.addItem(c.getTipo());

            }
        } else {
            if (cb_cadastrarColagem.getSelectedItem() == "Fundo Automático") {
                AbasFundoAutomaticoDAO dao = new AbasFundoAutomaticoDAO();
                List<AbasFundoAutomatico> lista = dao.listar();

                cb_cadastrarAbas.removeAllItems();
                cb_cadastrarAbas.addItem("*");

                for (AbasFundoAutomatico c : lista) {
                    cb_cadastrarAbas.addItem(c.getTipo());

                }
            } else {
                if (cb_cadastrarColagem.getSelectedItem() == "Outros") {
                    AbasOutrosDAO dao = new AbasOutrosDAO();
                    List<AbasOutros> lista = dao.listar();

                    cb_cadastrarAbas.removeAllItems();
                    cb_cadastrarAbas.addItem("*");

                    for (AbasOutros c : lista) {
                        cb_cadastrarAbas.addItem(c.getTipo());

                    }
                } else {
                    cb_cadastrarAbas.removeAllItems();
                    cb_cadastrarAbas.addItem("*");
                }
            }
        }
    }

    //Metodo Atualizar cb_pesquisaAbas conforme seleção do cb_pesquisaColagem
    public void Atualiza_cbpesquisaAbas() {
        if (cb_pesquisaColagem.getSelectedItem() == "Lateral") {
            AbasLateralDAO dao = new AbasLateralDAO();
            List<AbasLateral> lista = dao.listar();

            cb_pesquisaAbas.removeAllItems();
            cb_pesquisaAbas.addItem("*");

            for (AbasLateral c : lista) {
                cb_pesquisaAbas.addItem(c.getTipo());

            }
        } else {
            if (cb_pesquisaColagem.getSelectedItem() == "Fundo Automático") {
                AbasFundoAutomaticoDAO dao = new AbasFundoAutomaticoDAO();
                List<AbasFundoAutomatico> lista = dao.listar();

                cb_pesquisaAbas.removeAllItems();
                cb_pesquisaAbas.addItem("*");

                for (AbasFundoAutomatico c : lista) {
                    cb_pesquisaAbas.addItem(c.getTipo());

                }
            } else {
                if (cb_pesquisaColagem.getSelectedItem() == "Outros") {
                    AbasOutrosDAO dao = new AbasOutrosDAO();
                    List<AbasOutros> lista = dao.listar();

                    cb_pesquisaAbas.removeAllItems();
                    cb_pesquisaAbas.addItem("*");

                    for (AbasOutros c : lista) {
                        cb_pesquisaAbas.addItem(c.getTipo());

                    }
                } else {
                    cb_pesquisaAbas.removeAllItems();
                    cb_pesquisaAbas.addItem("*");
                }
            }
        }
    }

    //Construtor
    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        AtualizaCaminhoArte();
        AtualizaEmpresa();
        AcessoConvidado();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        btn_pesquisar = new javax.swing.JButton();
        btn_cadastrarDesenho = new javax.swing.JButton();
        btn_configurar = new javax.swing.JButton();
        btn_administrar = new javax.swing.JButton();
        btn_logar = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        lb_usuario = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelPrincipal = new javax.swing.JPanel();
        jPanelPesquisar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_desenhos = new javax.swing.JTable();
        btn_editarDesenho = new javax.swing.JButton();
        btn_escluirDesenho = new javax.swing.JButton();
        btn_visualizarDesenho = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        btn_pesquisarDesenhos = new javax.swing.JButton();
        btn_limpaCamposPesquisa = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        txt_pesquisaFaca = new javax.swing.JTextField();
        txt_pesquisaComprimento = new javax.swing.JTextField();
        txt_pesquisaLargura = new javax.swing.JTextField();
        txt_pesquisaAltura = new javax.swing.JTextField();
        cb_pesquisaColagem = new javax.swing.JComboBox();
        jSeparator17 = new javax.swing.JSeparator();
        cb_pesquisaAbas = new javax.swing.JComboBox();
        jPanelCadastrar = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cb_cadastrarPromocional = new javax.swing.JComboBox<>();
        cb_cadastrarBerco = new javax.swing.JComboBox<>();
        cb_cadastrarAbas = new javax.swing.JComboBox();
        cb_cadastrarColagem = new javax.swing.JComboBox();
        txt_cadastrarAltura = new javax.swing.JTextField();
        txt_cadastrarComprimento = new javax.swing.JTextField();
        txt_cadastrarLargura = new javax.swing.JTextField();
        txt_cadastrarFaca = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        btn_cancelarCadastroDesenho = new javax.swing.JButton();
        btn_LimpaCamposCadastroDesenho = new javax.swing.JButton();
        btn_salvaCadastroDesenho = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JSeparator();
        lb_cadastroId = new javax.swing.JLabel();
        jPanelConfig = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jl_config = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        cb_cadastrarAbasConfig = new javax.swing.JComboBox<>();
        lb_idAbasConfig = new javax.swing.JLabel();
        txt_cadastroAbasConfig = new javax.swing.JTextField();
        btn_criarAba = new javax.swing.JButton();
        btn_removerAba = new javax.swing.JButton();
        btn_limpaCamposCadastroAba = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_abasOutros = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_abasFundoAutomatico = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_abasLateral = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txt_localPasta = new javax.swing.JTextField();
        btn_escolherCaminho = new javax.swing.JButton();
        btn_salvarCaminho = new javax.swing.JButton();
        jPanelAdmin = new javax.swing.JPanel();
        btn_relatorio = new javax.swing.JButton();
        btn_CadastroAdmin = new javax.swing.JButton();
        lb_infoAdmin = new javax.swing.JLabel();
        jPanelAdminSubPrincipal = new javax.swing.JPanel();
        jPanelAdminSubCadastros = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lb_nomeEmpresa = new javax.swing.JLabel();
        lb_enderecoEmpresa = new javax.swing.JLabel();
        lb_telefoneEmpresa = new javax.swing.JLabel();
        lb_obsEmpresa = new javax.swing.JLabel();
        txt_nomeEmpresa = new javax.swing.JTextField();
        txt_obsEmpresa = new javax.swing.JTextField();
        txt_enderecoEmpresa = new javax.swing.JTextField();
        txt_telefoneEmpresa = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        btn_limparEmpresa = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        lb_nomeEmpresa1 = new javax.swing.JLabel();
        lb_enderecoEmpresa1 = new javax.swing.JLabel();
        lb_obsEmpresa1 = new javax.swing.JLabel();
        txt_nomeUsuario = new javax.swing.JTextField();
        txt_senhaUsuario = new javax.swing.JTextField();
        btn_salvarUsuario = new javax.swing.JButton();
        cb_privilegioUsuario = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_usuario = new javax.swing.JTable();
        btn_limparUsuario = new javax.swing.JButton();
        btn_removerUsuario = new javax.swing.JButton();
        lb_nomeEmpresa2 = new javax.swing.JLabel();
        lb_idUsuario = new javax.swing.JLabel();
        jPanelAdminSubRelatorios = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cb_usuarioRelatorio = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cb_mesesRelatorio = new javax.swing.JComboBox<>();
        btn_limpaCamposRelatorio = new javax.swing.JButton();
        btn_pesquisarRelatorio = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_relatorio = new javax.swing.JTable();
        jPanelLogin = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_loginNome = new javax.swing.JTextField();
        txt_loginSenha = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lb_empresa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("S.C.C.D");
        setMinimumSize(new java.awt.Dimension(1100, 720));
        setPreferredSize(new java.awt.Dimension(1100, 720));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        btn_pesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Pesquisar_64.png"))); // NOI18N
        btn_pesquisar.setToolTipText("Pesquisar");
        btn_pesquisar.setBorder(null);
        btn_pesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesquisarActionPerformed(evt);
            }
        });

        btn_cadastrarDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Adicionar_64.png"))); // NOI18N
        btn_cadastrarDesenho.setToolTipText("Adicionar Registro");
        btn_cadastrarDesenho.setBorder(null);
        btn_cadastrarDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cadastrarDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarDesenhoActionPerformed(evt);
            }
        });

        btn_configurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Configuração._64.png"))); // NOI18N
        btn_configurar.setToolTipText("Configuração");
        btn_configurar.setBorder(null);
        btn_configurar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_configurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configurarActionPerformed(evt);
            }
        });

        btn_administrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Protegido_64.png"))); // NOI18N
        btn_administrar.setToolTipText("Acesso Administrador");
        btn_administrar.setBorder(null);
        btn_administrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_administrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_administrarActionPerformed(evt);
            }
        });

        btn_logar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Usuario_64.png"))); // NOI18N
        btn_logar.setToolTipText("Login");
        btn_logar.setBorder(null);
        btn_logar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logarActionPerformed(evt);
            }
        });

        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Sair_24.png"))); // NOI18N
        btn_logout.setToolTipText("Logout");
        btn_logout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        lb_usuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_usuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lb_usuario.setText("convidado");
        lb_usuario.setPreferredSize(new java.awt.Dimension(45, 18));

        jPanelPrincipal.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 100));

        tb_desenhos.setAutoCreateRowSorter(true);
        tb_desenhos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_desenhos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Faca", "Comprimento", "Largura", "Altura", "Colagem", "Abas", "Berço", "Promocional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_desenhos.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_desenhos.setMinimumSize(new java.awt.Dimension(100, 100));
        tb_desenhos.setRowHeight(20);
        tb_desenhos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_desenhos);
        if (tb_desenhos.getColumnModel().getColumnCount() > 0) {
            tb_desenhos.getColumnModel().getColumn(0).setMinWidth(20);
            tb_desenhos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tb_desenhos.getColumnModel().getColumn(0).setMaxWidth(70);
            tb_desenhos.getColumnModel().getColumn(1).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(1).setPreferredWidth(80);
            tb_desenhos.getColumnModel().getColumn(2).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tb_desenhos.getColumnModel().getColumn(3).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(3).setPreferredWidth(40);
            tb_desenhos.getColumnModel().getColumn(4).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(4).setPreferredWidth(40);
            tb_desenhos.getColumnModel().getColumn(5).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(6).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(6).setPreferredWidth(150);
            tb_desenhos.getColumnModel().getColumn(7).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(7).setPreferredWidth(20);
            tb_desenhos.getColumnModel().getColumn(7).setHeaderValue("Berço");
            tb_desenhos.getColumnModel().getColumn(8).setResizable(false);
            tb_desenhos.getColumnModel().getColumn(8).setPreferredWidth(20);
            tb_desenhos.getColumnModel().getColumn(8).setHeaderValue("Promocional");
        }

        btn_editarDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Editar_24.png"))); // NOI18N
        btn_editarDesenho.setToolTipText("Alterar");
        btn_editarDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editarDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarDesenhoActionPerformed(evt);
            }
        });

        btn_escluirDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Remover_24.png"))); // NOI18N
        btn_escluirDesenho.setToolTipText("Remover");
        btn_escluirDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_escluirDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_escluirDesenhoActionPerformed(evt);
            }
        });

        btn_visualizarDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Visualizar_24.png"))); // NOI18N
        btn_visualizarDesenho.setToolTipText("Visualizar");
        btn_visualizarDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_visualizarDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_visualizarDesenhoActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Pesquisar");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("F:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("C:");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("L:");

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("A:");

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btn_pesquisarDesenhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Pesquisar2_16.png"))); // NOI18N
        btn_pesquisarDesenhos.setToolTipText("Pesquisar");
        btn_pesquisarDesenhos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pesquisarDesenhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesquisarDesenhosActionPerformed(evt);
            }
        });

        btn_limpaCamposPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_16.png"))); // NOI18N
        btn_limpaCamposPesquisa.setToolTipText("Limpar Pesquisa");
        btn_limpaCamposPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_limpaCamposPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpaCamposPesquisaActionPerformed(evt);
            }
        });

        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txt_pesquisaFaca.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pesquisaFaca.setToolTipText("Faca");
        txt_pesquisaFaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisaFacaKeyPressed(evt);
            }
        });

        txt_pesquisaComprimento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pesquisaComprimento.setToolTipText("Comprimento");
        txt_pesquisaComprimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisaComprimentoKeyPressed(evt);
            }
        });

        txt_pesquisaLargura.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pesquisaLargura.setToolTipText("Largura");
        txt_pesquisaLargura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisaLarguraKeyPressed(evt);
            }
        });

        txt_pesquisaAltura.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_pesquisaAltura.setToolTipText("Altura");
        txt_pesquisaAltura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisaAlturaKeyPressed(evt);
            }
        });

        cb_pesquisaColagem.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_pesquisaColagem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*", "Lateral", "Fundo Automático", "Outros" }));
        cb_pesquisaColagem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_pesquisaColagemItemStateChanged(evt);
            }
        });

        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cb_pesquisaAbas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_pesquisaAbas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txt_pesquisaFaca, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(txt_pesquisaComprimento, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(txt_pesquisaLargura, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(txt_pesquisaAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_pesquisaColagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_pesquisaAbas, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pesquisarDesenhos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_limpaCamposPesquisa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_editarDesenho)
                .addGap(25, 25, 25)
                .addComponent(btn_escluirDesenho)
                .addGap(25, 25, 25)
                .addComponent(btn_visualizarDesenho)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_pesquisaFaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_pesquisaComprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_pesquisaLargura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_pesquisaAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cb_pesquisaColagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_pesquisaAbas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pesquisarDesenhos)
                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpaCamposPesquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editarDesenho)
                    .addComponent(btn_escluirDesenho)
                    .addComponent(btn_visualizarDesenho))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelPesquisarLayout = new javax.swing.GroupLayout(jPanelPesquisar);
        jPanelPesquisar.setLayout(jPanelPesquisarLayout);
        jPanelPesquisarLayout.setHorizontalGroup(
            jPanelPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelPesquisarLayout.setVerticalGroup(
            jPanelPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelPrincipal.add(jPanelPesquisar, "pesquisar");
        jPanelPesquisar.getAccessibleContext().setAccessibleName("pesquisar");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Faca:");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Comprimento:");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setText("Colagem:");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setText("Abas:");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setText("Berço:");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setText("Promocional");

        cb_cadastrarPromocional.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_cadastrarPromocional.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*", "Sim", "Não" }));

        cb_cadastrarBerco.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_cadastrarBerco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*", "Com", "Sem" }));

        cb_cadastrarAbas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_cadastrarAbas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*" }));

        cb_cadastrarColagem.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_cadastrarColagem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*", "Lateral", "Fundo Automático", "Outros" }));
        cb_cadastrarColagem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_cadastrarColagemItemStateChanged(evt);
            }
        });

        txt_cadastrarAltura.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txt_cadastrarComprimento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txt_cadastrarLargura.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txt_cadastrarFaca.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Largura:");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("Altura:");

        btn_cancelarCadastroDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancelar2_32.png"))); // NOI18N
        btn_cancelarCadastroDesenho.setToolTipText("Cancelar");
        btn_cancelarCadastroDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cancelarCadastroDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarCadastroDesenhoActionPerformed(evt);
            }
        });

        btn_LimpaCamposCadastroDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_32.png"))); // NOI18N
        btn_LimpaCamposCadastroDesenho.setToolTipText("Limpar");
        btn_LimpaCamposCadastroDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_LimpaCamposCadastroDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LimpaCamposCadastroDesenhoActionPerformed(evt);
            }
        });

        btn_salvaCadastroDesenho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_32.png"))); // NOI18N
        btn_salvaCadastroDesenho.setToolTipText("Salvar");
        btn_salvaCadastroDesenho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvaCadastroDesenho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvaCadastroDesenhoActionPerformed(evt);
            }
        });

        lb_cadastroId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_cadastroId.setForeground(new java.awt.Color(153, 153, 153));
        lb_cadastroId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_cadastrarFaca, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_cadastrarComprimento, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cadastrarAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cadastrarLargura, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_cadastrarColagem, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator11)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_cadastrarAbas, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_cadastrarPromocional, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator13)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_cadastrarBerco, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator12)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cancelarCadastroDesenho)
                .addGap(18, 18, 18)
                .addComponent(btn_LimpaCamposCadastroDesenho)
                .addGap(18, 18, 18)
                .addComponent(btn_salvaCadastroDesenho)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator14, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(lb_cadastroId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lb_cadastroId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txt_cadastrarFaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel10)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel11))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txt_cadastrarComprimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(txt_cadastrarAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_cadastrarLargura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_cadastrarColagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(0, 0, 0)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel13)
                    .addComponent(cb_cadastrarAbas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cb_cadastrarBerco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(0, 0, 0)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_cadastrarPromocional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(0, 0, 0)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cancelarCadastroDesenho)
                    .addComponent(btn_LimpaCamposCadastroDesenho)
                    .addComponent(btn_salvaCadastroDesenho))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCadastrarLayout = new javax.swing.GroupLayout(jPanelCadastrar);
        jPanelCadastrar.setLayout(jPanelCadastrarLayout);
        jPanelCadastrarLayout.setHorizontalGroup(
            jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelCadastrarLayout.setVerticalGroup(
            jPanelCadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelPrincipal.add(jPanelCadastrar, "cadastrar");

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jl_config.setForeground(new java.awt.Color(102, 102, 102));
        jl_config.setText("Configuração");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Abas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        cb_cadastrarAbasConfig.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_cadastrarAbasConfig.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*", "Lateral", "Fundo Automático", "Outros" }));

        lb_idAbasConfig.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_idAbasConfig.setForeground(new java.awt.Color(153, 153, 153));
        lb_idAbasConfig.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        txt_cadastroAbasConfig.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_criarAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_16.png"))); // NOI18N
        btn_criarAba.setToolTipText("Salvar");
        btn_criarAba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_criarAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_criarAbaActionPerformed(evt);
            }
        });

        btn_removerAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Remover_16.png"))); // NOI18N
        btn_removerAba.setToolTipText("Remover");
        btn_removerAba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_removerAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerAbaActionPerformed(evt);
            }
        });

        btn_limpaCamposCadastroAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_16.png"))); // NOI18N
        btn_limpaCamposCadastroAba.setToolTipText("Limpar");
        btn_limpaCamposCadastroAba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_limpaCamposCadastroAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpaCamposCadastroAbaActionPerformed(evt);
            }
        });

        tb_abasOutros.setAutoCreateRowSorter(true);
        tb_abasOutros.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_abasOutros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Outros"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_abasOutros.setToolTipText("Duplo click para editar cadastro");
        tb_abasOutros.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_abasOutros.setMinimumSize(new java.awt.Dimension(105, 80));
        tb_abasOutros.setRowHeight(20);
        tb_abasOutros.getTableHeader().setReorderingAllowed(false);
        tb_abasOutros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_abasOutrosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_abasOutros);
        if (tb_abasOutros.getColumnModel().getColumnCount() > 0) {
            tb_abasOutros.getColumnModel().getColumn(0).setMinWidth(40);
            tb_abasOutros.getColumnModel().getColumn(0).setPreferredWidth(40);
            tb_abasOutros.getColumnModel().getColumn(0).setMaxWidth(40);
            tb_abasOutros.getColumnModel().getColumn(1).setResizable(false);
        }
        tb_abasOutros.getAccessibleContext().setAccessibleName("Outros");

        tb_abasFundoAutomatico.setAutoCreateRowSorter(true);
        tb_abasFundoAutomatico.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_abasFundoAutomatico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fundo Automático"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_abasFundoAutomatico.setToolTipText("Duplo click para editar cadastro");
        tb_abasFundoAutomatico.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_abasFundoAutomatico.setMinimumSize(new java.awt.Dimension(105, 80));
        tb_abasFundoAutomatico.setRowHeight(20);
        tb_abasFundoAutomatico.getTableHeader().setReorderingAllowed(false);
        tb_abasFundoAutomatico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_abasFundoAutomaticoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_abasFundoAutomatico);
        if (tb_abasFundoAutomatico.getColumnModel().getColumnCount() > 0) {
            tb_abasFundoAutomatico.getColumnModel().getColumn(0).setMinWidth(40);
            tb_abasFundoAutomatico.getColumnModel().getColumn(0).setPreferredWidth(40);
            tb_abasFundoAutomatico.getColumnModel().getColumn(0).setMaxWidth(40);
            tb_abasFundoAutomatico.getColumnModel().getColumn(1).setResizable(false);
        }
        tb_abasFundoAutomatico.getAccessibleContext().setAccessibleName("Fundo Automático");

        tb_abasLateral.setAutoCreateRowSorter(true);
        tb_abasLateral.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_abasLateral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Lateral"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_abasLateral.setToolTipText("Duplo click para editar cadastro");
        tb_abasLateral.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_abasLateral.setMinimumSize(new java.awt.Dimension(105, 80));
        tb_abasLateral.setRowHeight(20);
        tb_abasLateral.getTableHeader().setReorderingAllowed(false);
        tb_abasLateral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_abasLateralMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_abasLateral);
        if (tb_abasLateral.getColumnModel().getColumnCount() > 0) {
            tb_abasLateral.getColumnModel().getColumn(0).setMinWidth(40);
            tb_abasLateral.getColumnModel().getColumn(0).setPreferredWidth(40);
            tb_abasLateral.getColumnModel().getColumn(0).setMaxWidth(40);
            tb_abasLateral.getColumnModel().getColumn(1).setResizable(false);
        }
        tb_abasLateral.getAccessibleContext().setAccessibleName("Lateral");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cb_cadastrarAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_idAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cadastroAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_criarAba)
                .addGap(0, 0, 0)
                .addComponent(btn_removerAba)
                .addGap(0, 0, 0)
                .addComponent(btn_limpaCamposCadastroAba)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_idAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cadastroAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_criarAba)
                    .addComponent(btn_removerAba)
                    .addComponent(btn_limpaCamposCadastroAba)
                    .addComponent(cb_cadastrarAbasConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Local de arquivos para visualização", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txt_localPasta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btn_escolherCaminho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Reticencias_16.png"))); // NOI18N
        btn_escolherCaminho.setToolTipText("Escolher");
        btn_escolherCaminho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_escolherCaminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_escolherCaminhoActionPerformed(evt);
            }
        });

        btn_salvarCaminho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_16.png"))); // NOI18N
        btn_salvarCaminho.setToolTipText("Salvar");
        btn_salvarCaminho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvarCaminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarCaminhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_localPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_escolherCaminho)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_salvarCaminho)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txt_localPasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_escolherCaminho)
                    .addComponent(btn_salvarCaminho))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jl_config)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jl_config)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelConfigLayout = new javax.swing.GroupLayout(jPanelConfig);
        jPanelConfig.setLayout(jPanelConfigLayout);
        jPanelConfigLayout.setHorizontalGroup(
            jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelConfigLayout.setVerticalGroup(
            jPanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelPrincipal.add(jPanelConfig, "config");

        jPanelAdmin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btn_relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Relatorios_64.png"))); // NOI18N
        btn_relatorio.setToolTipText("Relatório pesquisa");
        btn_relatorio.setBorder(null);
        btn_relatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_relatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_relatorioActionPerformed(evt);
            }
        });

        btn_CadastroAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cadastro_64.png"))); // NOI18N
        btn_CadastroAdmin.setToolTipText("Cadastros");
        btn_CadastroAdmin.setBorder(null);
        btn_CadastroAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_CadastroAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CadastroAdminActionPerformed(evt);
            }
        });

        lb_infoAdmin.setForeground(new java.awt.Color(102, 102, 102));
        lb_infoAdmin.setText("Administrador/Cadastro");

        jPanelAdminSubPrincipal.setLayout(new java.awt.CardLayout());

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        lb_nomeEmpresa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_nomeEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        lb_nomeEmpresa.setText("Nome:");

        lb_enderecoEmpresa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_enderecoEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        lb_enderecoEmpresa.setText("Endereço:");

        lb_telefoneEmpresa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_telefoneEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        lb_telefoneEmpresa.setText("Telefone:");

        lb_obsEmpresa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_obsEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        lb_obsEmpresa.setText("Observações:");

        txt_nomeEmpresa.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txt_obsEmpresa.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txt_enderecoEmpresa.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        try {
            txt_telefoneEmpresa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_telefoneEmpresa.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_32.png"))); // NOI18N
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_limparEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_32.png"))); // NOI18N
        btn_limparEmpresa.setToolTipText("Limpar");
        btn_limparEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_limparEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(lb_nomeEmpresa)
                        .addGap(60, 60, 60)
                        .addComponent(txt_nomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lb_enderecoEmpresa)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(lb_obsEmpresa)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_obsEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(txt_enderecoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_telefoneEmpresa)
                                .addGap(12, 12, 12)
                                .addComponent(txt_telefoneEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limparEmpresa)
                    .addComponent(jButton3))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lb_nomeEmpresa))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(txt_nomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_enderecoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_enderecoEmpresa)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(lb_telefoneEmpresa))
                                    .addComponent(txt_telefoneEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_obsEmpresa)
                            .addComponent(txt_obsEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limparEmpresa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        lb_nomeEmpresa1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_nomeEmpresa1.setForeground(new java.awt.Color(102, 102, 102));
        lb_nomeEmpresa1.setText("Nome:");

        lb_enderecoEmpresa1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_enderecoEmpresa1.setForeground(new java.awt.Color(102, 102, 102));
        lb_enderecoEmpresa1.setText("Senha:");

        lb_obsEmpresa1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_obsEmpresa1.setForeground(new java.awt.Color(102, 102, 102));
        lb_obsEmpresa1.setText("Privilégio:");

        txt_nomeUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_nomeUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_senhaUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_senhaUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_salvarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_32.png"))); // NOI18N
        btn_salvarUsuario.setToolTipText("Salvar");
        btn_salvarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarUsuarioActionPerformed(evt);
            }
        });

        cb_privilegioUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_privilegioUsuario.setMaximumRowCount(3);
        cb_privilegioUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*", "Usuário", "Admin" }));

        tb_usuario.setAutoCreateRowSorter(true);
        tb_usuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nome", "Senha", "Privilégio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_usuario.setToolTipText("Clicke duas vezes do cadastro para editá-lo");
        tb_usuario.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_usuario.setRowHeight(20);
        tb_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_usuarioMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tb_usuario);
        if (tb_usuario.getColumnModel().getColumnCount() > 0) {
            tb_usuario.getColumnModel().getColumn(0).setResizable(false);
            tb_usuario.getColumnModel().getColumn(0).setPreferredWidth(30);
            tb_usuario.getColumnModel().getColumn(1).setResizable(false);
            tb_usuario.getColumnModel().getColumn(1).setPreferredWidth(200);
            tb_usuario.getColumnModel().getColumn(2).setMinWidth(0);
            tb_usuario.getColumnModel().getColumn(2).setPreferredWidth(0);
            tb_usuario.getColumnModel().getColumn(2).setMaxWidth(0);
            tb_usuario.getColumnModel().getColumn(3).setResizable(false);
            tb_usuario.getColumnModel().getColumn(3).setPreferredWidth(35);
        }

        btn_limparUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_32.png"))); // NOI18N
        btn_limparUsuario.setToolTipText("Limpar");
        btn_limparUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_limparUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparUsuarioActionPerformed(evt);
            }
        });

        btn_removerUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Remover_32.png"))); // NOI18N
        btn_removerUsuario.setToolTipText("Remover");
        btn_removerUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_removerUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removerUsuarioActionPerformed(evt);
            }
        });

        lb_nomeEmpresa2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_nomeEmpresa2.setForeground(new java.awt.Color(102, 102, 102));
        lb_nomeEmpresa2.setText("Id:");

        lb_idUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_idUsuario.setForeground(new java.awt.Color(153, 153, 153));
        lb_idUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_obsEmpresa1)
                            .addComponent(lb_enderecoEmpresa1)
                            .addComponent(lb_nomeEmpresa1)
                            .addComponent(lb_nomeEmpresa2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cb_privilegioUsuario, javax.swing.GroupLayout.Alignment.LEADING, 0, 100, Short.MAX_VALUE)
                                .addComponent(txt_senhaUsuario, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(txt_nomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(btn_salvarUsuario)
                        .addGap(20, 20, 20)
                        .addComponent(btn_removerUsuario)
                        .addGap(20, 20, 20)
                        .addComponent(btn_limparUsuario)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lb_nomeEmpresa2)
                            .addComponent(lb_idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_nomeEmpresa1)
                            .addComponent(txt_nomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_enderecoEmpresa1)
                            .addComponent(txt_senhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_obsEmpresa1)
                            .addComponent(cb_privilegioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_salvarUsuario)
                            .addComponent(btn_limparUsuario)
                            .addComponent(btn_removerUsuario))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelAdminSubCadastrosLayout = new javax.swing.GroupLayout(jPanelAdminSubCadastros);
        jPanelAdminSubCadastros.setLayout(jPanelAdminSubCadastrosLayout);
        jPanelAdminSubCadastrosLayout.setHorizontalGroup(
            jPanelAdminSubCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelAdminSubCadastrosLayout.setVerticalGroup(
            jPanelAdminSubCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAdminSubCadastrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jPanelAdminSubPrincipal.add(jPanelAdminSubCadastros, "SubCadastros");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cb_usuarioRelatorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_usuarioRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*" }));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Cadastrado por:");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("no(s) ultimo(s)");

        cb_mesesRelatorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cb_mesesRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*", "1 Mês", "2 Meses", "3 Meses", "6 Meses", "12 Meses", "24 Meses", "36 Meses" }));

        btn_limpaCamposRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Limpar_16.png"))); // NOI18N
        btn_limpaCamposRelatorio.setToolTipText("Limpar");
        btn_limpaCamposRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_limpaCamposRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpaCamposRelatorioActionPerformed(evt);
            }
        });

        btn_pesquisarRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Pesquisar2_16.png"))); // NOI18N
        btn_pesquisarRelatorio.setToolTipText("Pesquisar");
        btn_pesquisarRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pesquisarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesquisarRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_usuarioRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_mesesRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_pesquisarRelatorio)
                .addGap(18, 18, 18)
                .addComponent(btn_limpaCamposRelatorio)
                .addContainerGap(386, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_limpaCamposRelatorio)
                    .addComponent(cb_mesesRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(cb_usuarioRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(btn_pesquisarRelatorio)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tb_relatorio.setAutoCreateRowSorter(true);
        tb_relatorio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_relatorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Faca", "Comprimento", "Largura", "Altura", "CadastradoPor", "DataHora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_relatorio.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tb_relatorio.setMinimumSize(new java.awt.Dimension(100, 100));
        tb_relatorio.setRowHeight(20);
        tb_relatorio.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tb_relatorio);
        if (tb_relatorio.getColumnModel().getColumnCount() > 0) {
            tb_relatorio.getColumnModel().getColumn(0).setMinWidth(20);
            tb_relatorio.getColumnModel().getColumn(0).setPreferredWidth(20);
            tb_relatorio.getColumnModel().getColumn(0).setMaxWidth(70);
            tb_relatorio.getColumnModel().getColumn(1).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(1).setPreferredWidth(80);
            tb_relatorio.getColumnModel().getColumn(2).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(2).setPreferredWidth(40);
            tb_relatorio.getColumnModel().getColumn(3).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(3).setPreferredWidth(40);
            tb_relatorio.getColumnModel().getColumn(4).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(4).setPreferredWidth(40);
            tb_relatorio.getColumnModel().getColumn(5).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(6).setResizable(false);
            tb_relatorio.getColumnModel().getColumn(6).setPreferredWidth(150);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1096, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAdminSubRelatoriosLayout = new javax.swing.GroupLayout(jPanelAdminSubRelatorios);
        jPanelAdminSubRelatorios.setLayout(jPanelAdminSubRelatoriosLayout);
        jPanelAdminSubRelatoriosLayout.setHorizontalGroup(
            jPanelAdminSubRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelAdminSubRelatoriosLayout.setVerticalGroup(
            jPanelAdminSubRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminSubRelatoriosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanelAdminSubPrincipal.add(jPanelAdminSubRelatorios, "SubRelatorios");

        javax.swing.GroupLayout jPanelAdminLayout = new javax.swing.GroupLayout(jPanelAdmin);
        jPanelAdmin.setLayout(jPanelAdminLayout);
        jPanelAdminLayout.setHorizontalGroup(
            jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelAdminSubPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelAdminLayout.createSequentialGroup()
                .addGroup(jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_infoAdmin)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAdminLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_CadastroAdmin)
                        .addGap(40, 40, 40)
                        .addComponent(btn_relatorio)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelAdminLayout.setVerticalGroup(
            jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAdminLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lb_infoAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_CadastroAdmin)
                    .addComponent(btn_relatorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAdminSubPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelPrincipal.add(jPanelAdmin, "adminPrincipal");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Usuario_128.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("LOGIN");

        txt_loginNome.setBackground(new java.awt.Color(214, 217, 223));
        txt_loginNome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_loginNome.setForeground(new java.awt.Color(0, 0, 102));
        txt_loginNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_loginNome.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        txt_loginSenha.setBackground(new java.awt.Color(214, 217, 223));
        txt_loginSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_loginSenha.setForeground(new java.awt.Color(0, 0, 102));
        txt_loginSenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_loginSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Senha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Confirmar2_32.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancelar2_32.png"))); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_loginNome, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_loginSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(59, 59, 59)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(40, 40, 40)
                        .addComponent(jButton1)
                        .addGap(41, 41, 41)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_loginNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txt_loginSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelPrincipal.add(jPanelLogin, "login");

        lb_empresa.setForeground(new java.awt.Color(102, 102, 102));
        lb_empresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lb_empresa.setText("jLabel16");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btn_pesquisar)
                .addGap(40, 40, 40)
                .addComponent(btn_cadastrarDesenho)
                .addGap(40, 40, 40)
                .addComponent(btn_configurar)
                .addGap(40, 40, 40)
                .addComponent(btn_administrar)
                .addGap(40, 40, 40)
                .addComponent(btn_logar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lb_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_logout))
                    .addComponent(lb_empresa, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_administrar)
                    .addComponent(btn_configurar)
                    .addComponent(btn_cadastrarDesenho)
                    .addComponent(btn_pesquisar)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lb_usuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_logout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(lb_empresa))
                    .addComponent(btn_logar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisarActionPerformed
        // TODO add your handling code here:
        MostraCard_jPanelPrincipal("pesquisar");
    }//GEN-LAST:event_btn_pesquisarActionPerformed

    private void btn_logarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logarActionPerformed
        // TODO add your handling code here:
        MostraCard_jPanelPrincipal("login");
    }//GEN-LAST:event_btn_logarActionPerformed

    private void btn_cadastrarDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarDesenhoActionPerformed
        // TODO add your handling code here:
        MostraCard_jPanelPrincipal("cadastrar");
    }//GEN-LAST:event_btn_cadastrarDesenhoActionPerformed

    private void btn_configurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configurarActionPerformed
        // TODO add your handling code here:
        MostraCard_jPanelPrincipal("config");
    }//GEN-LAST:event_btn_configurarActionPerformed

    private void btn_administrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_administrarActionPerformed
        // TODO add your handling code here:
        MostraCard_jPanelPrincipal("adminPrincipal");
    }//GEN-LAST:event_btn_administrarActionPerformed

    private void btn_cancelarCadastroDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarCadastroDesenhoActionPerformed
        // TODO add your handling code here:
        LimpaCamposCadastro();
        MostraCard_jPanelPrincipal("pesquisar");
    }//GEN-LAST:event_btn_cancelarCadastroDesenhoActionPerformed

    private void btn_LimpaCamposCadastroDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LimpaCamposCadastroDesenhoActionPerformed
        // TODO add your handling code here:
        LimpaCamposCadastro();
    }//GEN-LAST:event_btn_LimpaCamposCadastroDesenhoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        LimpaCamposLogin();
        MostraCard_jPanelPrincipal("pesquisar");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_salvaCadastroDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvaCadastroDesenhoActionPerformed
        // TODO add your handling code here:
        CadastrarDesenho();
    }//GEN-LAST:event_btn_salvaCadastroDesenhoActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        ListarDesenhos();
        ListarAbasLateral();
        ListarAbasFundoAutomatico();
        ListarAbasOutros();
        ListarUsuarios();
        Atualiza_cbUsuarios();
        ListarRelatorio();
    }//GEN-LAST:event_formWindowActivated

    private void btn_pesquisarDesenhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisarDesenhosActionPerformed
        // TODO add your handling code here:
        PesquisarDesenhos();
    }//GEN-LAST:event_btn_pesquisarDesenhosActionPerformed

    private void btn_limpaCamposPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpaCamposPesquisaActionPerformed
        // TODO add your handling code here:
        LimpaCamposPesquisa();
        ListarDesenhos();
    }//GEN-LAST:event_btn_limpaCamposPesquisaActionPerformed

    private void txt_pesquisaFacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisaFacaKeyPressed
        // TODO add your handling code here:
        PesquisarDesenhos();
    }//GEN-LAST:event_txt_pesquisaFacaKeyPressed

    private void txt_pesquisaComprimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisaComprimentoKeyPressed
        // TODO add your handling code here:
        PesquisarDesenhos();
    }//GEN-LAST:event_txt_pesquisaComprimentoKeyPressed

    private void txt_pesquisaLarguraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisaLarguraKeyPressed
        // TODO add your handling code here:
        PesquisarDesenhos();
    }//GEN-LAST:event_txt_pesquisaLarguraKeyPressed

    private void txt_pesquisaAlturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisaAlturaKeyPressed
        // TODO add your handling code here:
        PesquisarDesenhos();
    }//GEN-LAST:event_txt_pesquisaAlturaKeyPressed

    private void btn_escolherCaminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_escolherCaminhoActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = file.showSaveDialog(null);
        if (i == 1) {
            txt_localPasta.setText("");
        } else {
            File arquivo = file.getSelectedFile();
            txt_localPasta.setText(arquivo.getPath());
        }
    }//GEN-LAST:event_btn_escolherCaminhoActionPerformed

    private void btn_salvarCaminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarCaminhoActionPerformed
        // TODO add your handling code here:
        AlterarCaminhoArte();
    }//GEN-LAST:event_btn_salvarCaminhoActionPerformed

    private void btn_visualizarDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualizarDesenhoActionPerformed
        // TODO add your handling code here:
        if (tb_desenhos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado!", "", 2);
        } else {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File(txt_localPasta.getText() + "\\" + tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 1).toString() + ".pdf"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Arquivo não encontrado!", "", 2);
                //JOptionPane.showMessageDialog(null, txt_localPasta.getText() + "\\" + tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 1).toString() + ".pdf", "", 2);
            }
        }
    }//GEN-LAST:event_btn_visualizarDesenhoActionPerformed

    private void btn_editarDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarDesenhoActionPerformed
        // TODO add your handling code here:
        if (tb_desenhos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado!", "", 2);
        } else {
            String c0 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 0).toString();
            String c1 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 1).toString();
            String c2 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 2).toString();
            String c3 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 3).toString();
            String c4 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 4).toString();
            String c5 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 5).toString();
            String c6 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 6).toString();
            String c7 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 7).toString();
            String c8 = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 8).toString();

            lb_cadastroId.setText(c0);
            txt_cadastrarFaca.setText(c1);
            txt_cadastrarComprimento.setText(c2);
            txt_cadastrarLargura.setText(c3);
            txt_cadastrarAltura.setText(c4);
            cb_cadastrarColagem.setSelectedItem(c5);
            cb_cadastrarAbas.setSelectedItem(c6);
            cb_cadastrarBerco.setSelectedItem(c7);
            cb_cadastrarPromocional.setSelectedItem(c8);

            txt_cadastrarFaca.setEnabled(false);

            MostraCard_jPanelPrincipal("cadastrar");
        }
    }//GEN-LAST:event_btn_editarDesenhoActionPerformed

    private void btn_escluirDesenhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_escluirDesenhoActionPerformed
        // TODO add your handling code here:
        if (tb_desenhos.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum registro selecionado!", "", 2);
        } else {
            String value = tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 1).toString();
            int resposta = JOptionPane.showConfirmDialog(null, "Excluir Cadastro da Faca " + value + "?", "", JOptionPane.YES_NO_OPTION);

            if (resposta == 0) {
                Desenhos obj = new Desenhos();

                obj.setId(Integer.parseInt(tb_desenhos.getValueAt(tb_desenhos.getSelectedRow(), 0).toString()));

                DesenhosDAO dao = new DesenhosDAO();

                dao.excluir(obj);
            }
        }
    }//GEN-LAST:event_btn_escluirDesenhoActionPerformed

    private void btn_criarAbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_criarAbaActionPerformed
        // TODO add your handling code here:
        if ("".equals(txt_cadastroAbasConfig.getText())) {

            JOptionPane.showMessageDialog(null, "Nome Inválido!", "", 2);

        } else {

            if ("Lateral".equals(cb_cadastrarAbasConfig.getSelectedItem().toString())) {

                AbasLateral obj = new AbasLateral();
                obj.setTipo(txt_cadastroAbasConfig.getText());
                obj.setCadastradopor(lb_usuario.getText());
                obj.setDatahora(DH());

                AbasLateralDAO dao = new AbasLateralDAO();
                if ("".equals(lb_idAbasConfig.getText())) {

                    dao.cadastrar(obj);

                } else {

                    obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                    dao.alterar(obj);

                }
            } else {
                if ("Fundo Automático".equals(cb_cadastrarAbasConfig.getSelectedItem().toString())) {

                    AbasFundoAutomatico obj = new AbasFundoAutomatico();
                    obj.setTipo(txt_cadastroAbasConfig.getText());
                    obj.setCadastradopor(lb_usuario.getText());
                    obj.setDatahora(DH());

                    AbasFundoAutomaticoDAO dao = new AbasFundoAutomaticoDAO();
                    if ("".equals(lb_idAbasConfig.getText())) {

                        dao.cadastrar(obj);

                    } else {

                        obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                        dao.alterar(obj);

                    }
                } else {
                    if ("Outros".equals(cb_cadastrarAbasConfig.getSelectedItem().toString())) {

                        AbasOutros obj = new AbasOutros();
                        obj.setTipo(txt_cadastroAbasConfig.getText());
                        obj.setCadastradopor(lb_usuario.getText());
                        obj.setDatahora(DH());

                        AbasOutrosDAO dao = new AbasOutrosDAO();
                        if ("".equals(lb_idAbasConfig.getText())) {

                            dao.cadastrar(obj);

                        } else {

                            obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                            dao.alterar(obj);

                        }
                    } else {

                        JOptionPane.showMessageDialog(null, "Campo Colagem Iválido!", "", 2);

                    }
                }
            }

            LimpaCamposCadastroAbas();
        }
    }//GEN-LAST:event_btn_criarAbaActionPerformed

    private void cb_cadastrarColagemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_cadastrarColagemItemStateChanged
        // TODO add your handling code here:
        Atualiza_cbCadastrarAbas();
    }//GEN-LAST:event_cb_cadastrarColagemItemStateChanged

    private void btn_limpaCamposCadastroAbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpaCamposCadastroAbaActionPerformed
        // TODO add your handling code here:
        LimpaCamposCadastroAbas();
    }//GEN-LAST:event_btn_limpaCamposCadastroAbaActionPerformed

    private void tb_abasLateralMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_abasLateralMouseClicked
        // TODO add your handling code here:
        tb_abasLateral.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    lb_idAbasConfig.setText(tb_abasLateral.getValueAt(tb_abasLateral.getSelectedRow(), 0).toString());
                    txt_cadastroAbasConfig.setText(tb_abasLateral.getValueAt(tb_abasLateral.getSelectedRow(), 1).toString());
                    cb_cadastrarAbasConfig.setSelectedItem(tb_abasLateral.getAccessibleContext().getAccessibleName());
                }
            }
        });
    }//GEN-LAST:event_tb_abasLateralMouseClicked

    private void tb_abasFundoAutomaticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_abasFundoAutomaticoMouseClicked
        // TODO add your handling code here:
        tb_abasFundoAutomatico.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    lb_idAbasConfig.setText(tb_abasFundoAutomatico.getValueAt(tb_abasFundoAutomatico.getSelectedRow(), 0).toString());
                    txt_cadastroAbasConfig.setText(tb_abasFundoAutomatico.getValueAt(tb_abasFundoAutomatico.getSelectedRow(), 1).toString());
                    cb_cadastrarAbasConfig.setSelectedItem(tb_abasFundoAutomatico.getAccessibleContext().getAccessibleName());
                }
            }
        });
    }//GEN-LAST:event_tb_abasFundoAutomaticoMouseClicked

    private void tb_abasOutrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_abasOutrosMouseClicked
        // TODO add your handling code here:
        tb_abasOutros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    lb_idAbasConfig.setText(tb_abasOutros.getValueAt(tb_abasOutros.getSelectedRow(), 0).toString());
                    txt_cadastroAbasConfig.setText(tb_abasOutros.getValueAt(tb_abasOutros.getSelectedRow(), 1).toString());
                    cb_cadastrarAbasConfig.setSelectedItem(tb_abasOutros.getAccessibleContext().getAccessibleName());
                }
            }
        });
    }//GEN-LAST:event_tb_abasOutrosMouseClicked

    private void btn_removerAbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerAbaActionPerformed
        // TODO add your handling code here:
        if (lb_idAbasConfig.getText() == "") {
            JOptionPane.showMessageDialog(null, "Nenhum registro(id) encontrado!", "", 2);
        } else {
            String infoaba = txt_cadastroAbasConfig.getText();
            String infocolagem = cb_cadastrarAbasConfig.getSelectedItem().toString();
            int resposta = JOptionPane.showConfirmDialog(null, "Excluir '" + infoaba + "' da Tabela " + infocolagem + "?", "", JOptionPane.YES_NO_OPTION);

            if (resposta == 0) {
                if (cb_cadastrarAbasConfig.getSelectedItem() == "Lateral") {

                    AbasLateral obj = new AbasLateral();

                    obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                    AbasLateralDAO dao = new AbasLateralDAO();
                    dao.excluir(obj);

                } else {
                    if (cb_cadastrarAbasConfig.getSelectedItem() == "Fundo Automático") {

                        AbasFundoAutomatico obj = new AbasFundoAutomatico();

                        obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                        AbasFundoAutomaticoDAO dao = new AbasFundoAutomaticoDAO();
                        dao.excluir(obj);

                    } else {
                        if (cb_cadastrarAbasConfig.getSelectedItem() == "Outros") {

                            AbasOutros obj = new AbasOutros();

                            obj.setId(Integer.parseInt(lb_idAbasConfig.getText()));
                            AbasOutrosDAO dao = new AbasOutrosDAO();
                            dao.excluir(obj);

                        }
                    }

                }
            }
        }
        LimpaCamposCadastroAbas();
    }//GEN-LAST:event_btn_removerAbaActionPerformed

    private void cb_pesquisaColagemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_pesquisaColagemItemStateChanged
        // TODO add your handling code here:
        Atualiza_cbpesquisaAbas();

    }//GEN-LAST:event_cb_pesquisaColagemItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ContaDAO dao = new ContaDAO();

        int vl = dao.efetuaLogin(txt_loginNome.getText(), txt_loginSenha.getText());

        switch (vl) {

            //Caso o usuario seja do tipo admin
            case 1:
                AcessoAdmin();
                lb_usuario.setText(txt_loginNome.getText());
                LimpaCamposLogin();
                MostraCard_jPanelPrincipal("pesquisar");

                break;

            //Caso o usuario seja do tipo limitado
            case 2:
                AcessoUsuario();
                lb_usuario.setText(txt_loginNome.getText());
                LimpaCamposLogin();
                MostraCard_jPanelPrincipal("pesquisar");

                break;

            default:

                break;

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed

        AcessoConvidado();
        MostraCard_jPanelPrincipal("pesquisar");
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_CadastroAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CadastroAdminActionPerformed

        MostraCard_jPanelAdminSubPrincipal("SubCadastros");
        lb_infoAdmin.setText("Administrador/Cadastro");
    }//GEN-LAST:event_btn_CadastroAdminActionPerformed

    private void btn_relatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_relatorioActionPerformed

        MostraCard_jPanelAdminSubPrincipal("SubRelatorios");
        lb_infoAdmin.setText("Administrador/Relatório");

    }//GEN-LAST:event_btn_relatorioActionPerformed

    private void tb_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_usuarioMouseClicked

        tb_usuario.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (!"admin".equals(tb_usuario.getValueAt(tb_usuario.getSelectedRow(), 1).toString())) {                                         
                    
                        lb_idUsuario.setText(tb_usuario.getValueAt(tb_usuario.getSelectedRow(), 0).toString());
                        txt_nomeUsuario.setText(tb_usuario.getValueAt(tb_usuario.getSelectedRow(), 1).toString());
                        txt_senhaUsuario.setText(tb_usuario.getValueAt(tb_usuario.getSelectedRow(), 2).toString());
                        cb_privilegioUsuario.setSelectedItem(tb_usuario.getValueAt(tb_usuario.getSelectedRow(), 3));
                    }
                }                
            }
        });
    }//GEN-LAST:event_tb_usuarioMouseClicked

    private void btn_salvarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarUsuarioActionPerformed

        if ("".equals(txt_nomeUsuario.getText())) {
            JOptionPane.showMessageDialog(null, "Nome Inválido!", "", 2);
        } else {
            if ("".equals(txt_senhaUsuario.getText())) {
                JOptionPane.showMessageDialog(null, "Senha Inválido!", "", 2);
            } else {
                if ("*".equals(cb_privilegioUsuario.getSelectedItem())) {
                    JOptionPane.showMessageDialog(null, "Nível de Previlégio Inválido!", "", 2);
                } else {

                    Conta obj = new Conta();
                    obj.setNome(txt_nomeUsuario.getText());
                    obj.setSenha(txt_senhaUsuario.getText());
                    obj.setPrivilegio(cb_privilegioUsuario.getSelectedItem().toString());

                    if ("".equals(lb_idUsuario.getText())) {

                        ContaDAO dao = new ContaDAO();
                        dao.cadastrar(obj);

                        LimpaCamposUsuarios();

                    } else {

                        obj.setId(Integer.parseInt(lb_idUsuario.getText()));

                        ContaDAO dao = new ContaDAO();
                        dao.alterar(obj);

                        LimpaCamposUsuarios();
                    }

                }
            }
        }

    }//GEN-LAST:event_btn_salvarUsuarioActionPerformed

    private void btn_removerUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removerUsuarioActionPerformed

        if (lb_idUsuario.getText() == "") {
            JOptionPane.showMessageDialog(null, "Nenhum registro(id) encontrado!", "", 2);
        } else {
            String usr = txt_nomeUsuario.getText();
            int resposta = JOptionPane.showConfirmDialog(null, "Excluir '" + usr + " da tabela Usuários?", "", JOptionPane.YES_NO_OPTION);

            if (resposta == 0) {

                Conta obj = new Conta();

                obj.setId(Integer.parseInt(lb_idUsuario.getText()));

                ContaDAO dao = new ContaDAO();

                dao.excluir(obj);

            }

        }

        LimpaCamposUsuarios();

    }//GEN-LAST:event_btn_removerUsuarioActionPerformed

    private void btn_limparUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparUsuarioActionPerformed
        LimpaCamposUsuarios();
    }//GEN-LAST:event_btn_limparUsuarioActionPerformed

    private void btn_limparEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparEmpresaActionPerformed
        LimpaCamposEmpresa();
    }//GEN-LAST:event_btn_limparEmpresaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AlterarEmpresa();
        lb_empresa.setText(txt_nomeEmpresa.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_limpaCamposRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpaCamposRelatorioActionPerformed

        cb_usuarioRelatorio.setSelectedItem("*");
        cb_mesesRelatorio.setSelectedItem("*");
        ListarRelatorio();
    }//GEN-LAST:event_btn_limpaCamposRelatorioActionPerformed

    private void btn_pesquisarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisarRelatorioActionPerformed

        PesquisaAdmin();
    }//GEN-LAST:event_btn_pesquisarRelatorioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CadastroAdmin;
    private javax.swing.JButton btn_LimpaCamposCadastroDesenho;
    private javax.swing.JButton btn_administrar;
    private javax.swing.JButton btn_cadastrarDesenho;
    private javax.swing.JButton btn_cancelarCadastroDesenho;
    private javax.swing.JButton btn_configurar;
    private javax.swing.JButton btn_criarAba;
    private javax.swing.JButton btn_editarDesenho;
    private javax.swing.JButton btn_escluirDesenho;
    private javax.swing.JButton btn_escolherCaminho;
    private javax.swing.JButton btn_limpaCamposCadastroAba;
    private javax.swing.JButton btn_limpaCamposPesquisa;
    private javax.swing.JButton btn_limpaCamposRelatorio;
    private javax.swing.JButton btn_limparEmpresa;
    private javax.swing.JButton btn_limparUsuario;
    private javax.swing.JButton btn_logar;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_pesquisar;
    private javax.swing.JButton btn_pesquisarDesenhos;
    private javax.swing.JButton btn_pesquisarRelatorio;
    private javax.swing.JButton btn_relatorio;
    private javax.swing.JButton btn_removerAba;
    private javax.swing.JButton btn_removerUsuario;
    private javax.swing.JButton btn_salvaCadastroDesenho;
    private javax.swing.JButton btn_salvarCaminho;
    private javax.swing.JButton btn_salvarUsuario;
    private javax.swing.JButton btn_visualizarDesenho;
    private javax.swing.JComboBox cb_cadastrarAbas;
    private javax.swing.JComboBox<String> cb_cadastrarAbasConfig;
    private javax.swing.JComboBox<String> cb_cadastrarBerco;
    private javax.swing.JComboBox cb_cadastrarColagem;
    private javax.swing.JComboBox<String> cb_cadastrarPromocional;
    private javax.swing.JComboBox<String> cb_mesesRelatorio;
    private javax.swing.JComboBox cb_pesquisaAbas;
    private javax.swing.JComboBox cb_pesquisaColagem;
    private javax.swing.JComboBox<String> cb_privilegioUsuario;
    private javax.swing.JComboBox<String> cb_usuarioRelatorio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAdmin;
    private javax.swing.JPanel jPanelAdminSubCadastros;
    private javax.swing.JPanel jPanelAdminSubPrincipal;
    private javax.swing.JPanel jPanelAdminSubRelatorios;
    private javax.swing.JPanel jPanelCadastrar;
    private javax.swing.JPanel jPanelConfig;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelPesquisar;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel jl_config;
    private javax.swing.JLabel lb_cadastroId;
    private javax.swing.JLabel lb_empresa;
    private javax.swing.JLabel lb_enderecoEmpresa;
    private javax.swing.JLabel lb_enderecoEmpresa1;
    private javax.swing.JLabel lb_idAbasConfig;
    private javax.swing.JLabel lb_idUsuario;
    private javax.swing.JLabel lb_infoAdmin;
    private javax.swing.JLabel lb_nomeEmpresa;
    private javax.swing.JLabel lb_nomeEmpresa1;
    private javax.swing.JLabel lb_nomeEmpresa2;
    private javax.swing.JLabel lb_obsEmpresa;
    private javax.swing.JLabel lb_obsEmpresa1;
    private javax.swing.JLabel lb_telefoneEmpresa;
    public javax.swing.JLabel lb_usuario;
    private javax.swing.JTable tb_abasFundoAutomatico;
    private javax.swing.JTable tb_abasLateral;
    private javax.swing.JTable tb_abasOutros;
    private javax.swing.JTable tb_desenhos;
    private javax.swing.JTable tb_relatorio;
    private javax.swing.JTable tb_usuario;
    private javax.swing.JTextField txt_cadastrarAltura;
    private javax.swing.JTextField txt_cadastrarComprimento;
    private javax.swing.JTextField txt_cadastrarFaca;
    private javax.swing.JTextField txt_cadastrarLargura;
    private javax.swing.JTextField txt_cadastroAbasConfig;
    private javax.swing.JTextField txt_enderecoEmpresa;
    private javax.swing.JTextField txt_localPasta;
    private javax.swing.JTextField txt_loginNome;
    private javax.swing.JPasswordField txt_loginSenha;
    private javax.swing.JTextField txt_nomeEmpresa;
    private javax.swing.JTextField txt_nomeUsuario;
    private javax.swing.JTextField txt_obsEmpresa;
    private javax.swing.JTextField txt_pesquisaAltura;
    private javax.swing.JTextField txt_pesquisaComprimento;
    private javax.swing.JTextField txt_pesquisaFaca;
    private javax.swing.JTextField txt_pesquisaLargura;
    private javax.swing.JTextField txt_senhaUsuario;
    private javax.swing.JFormattedTextField txt_telefoneEmpresa;
    // End of variables declaration//GEN-END:variables
}
