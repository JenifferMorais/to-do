# Como executar o projeto to-do

Para executar esse projeto, o primeiro passo é criar nossa database (pode ser utilizado o Microsoft SQL Server Management Studio 18 pra isso): 

    1- CREATE DATABASE todo;
  
    2- CREATE LOGIN sysql WITH PASSWORD = 'masterkey@25';
  
    3- CREATE USER sysql FOR LOGIN sysql;

Após, abra a conexão (no meu caso utilizei o dbeaver), com as seguintes informações:

    URL: jdbc:sqlserver://;serverName=localhost;databaseName=todo

    Username: sysql

    Password: masterkey@25

Com a conexão criada, rode os scripts que estão no arquivo script.sql na raiz do projeto para criar as tabelas.


Abra o projeto na sua IDE de preferência, e execute o projeto com o seguinte comando para rodar o projeto:

    1- mvn spring-boot:run


# Importante
*** Crie um usuário e faça login, utilize o Token do login para conseguir realizar outras operações ***

Como post, faça a requisição no caminho http://localhost:8080/user/create para criar seu usuário

Como post, faça a requisição no caminho http://localhost:8080/auth para logar.

Qualquer outra operação necessitará de permissão (do token gerado pelo login).

# Sobre os testes
Para que o teste da service da task corra perfeitamente, crie um usuario antes com os seguintes dados:

    email: jenimorais28@gmail.com
    
    password: Kcm@269854

Os testes foram desenvolvidos de forma a não deixar 'lixo' no banco (o teste começa com um insert e finaliza excluindo o que foi criado!)
