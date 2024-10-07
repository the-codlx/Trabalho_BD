package model;
public class Cliente {

    private int id_cliente;
    private String nome;
    private String email;
    private String cpf;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String numero;
    private String complemento;
    private String telefone;
    private String senha;
    private String data_criacao;
    private String nome_usuario;
    private String role;
    
    
    public Cliente(String nome, String email, String cpf, String cep, String rua, String bairro,
            String cidade, String numero, String complemento, String telefone, String senha, String nome_usuario) {

        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.complemento = complemento;
        this.telefone = telefone;
        this.senha = senha;
        this.nome_usuario = nome_usuario;
        this.role = "cliente";
    }

    

    public Cliente() {
        this.nome = "";
        this.email = "";
        this.cpf = "";
        this.cep = "";
        this.rua = "";
        this.bairro = "";
        this.cidade = "";
        this.numero = "";
        this.complemento = "";
        this.telefone = "";
        this.senha = "";
        this.nome_usuario = "";
        this.role = "cliente";
    }



    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getNome_Usuario() {
        return nome_usuario;
    }

    public void setNome_Usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public void setRole(String role) {
        this.role = role;
    }   

    public String getRole() {
        return role;
    }

}