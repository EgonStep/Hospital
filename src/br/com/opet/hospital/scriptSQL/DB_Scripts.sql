create user DBhospital identified by SYSTEM;

grant connect, resource to DBhospital;

CREATE TABLE PESSOA 
(
ID number not null,
CPF numeric(11) NOT NULL,
NOME VARCHAR2(60),
NASCIMENTO DATE,
RG VARCHAR2(20),
EMAIL VARCHAR2(60),
CONSTRAINT id_pessoa_pk PRIMARY KEY (ID)
);

CREATE TABLE ADMINISTRATIVO
(
IDADM number not null,
IDPESSOA number not null,
SALARIO float not null,
CONSTRAINT adm_foreign FOREIGN KEY (IDPESSOA) REFERENCES PESSOA(ID)
);

CREATE TABLE ENFERMEIRO
(
IDENF number not null,
IDPESSOA number not null,
CARGAHORARIA NUMBER NOT NULL,
CONSTRAINT enf_foreign FOREIGN KEY (IDPESSOA) REFERENCES PESSOA(ID)
);

CREATE TABLE ESPECIALIDADE
(
IDESPECIALIDADE NUMBER NOT NULL CONSTRAINT ESP_PK PRIMARY KEY,
DESCRICAO VARCHAR2(60) NOT NULL
);

CREATE TABLE MEDICO
(
IDMED number not null,
IDPESSOA number not null,
IDESPECIALIDADE NUMBER NOT NULL,
CONSTRAINT MED_FOREIGN FOREIGN KEY (IDPESSOA) REFERENCES PESSOA(ID),
CONSTRAINT MED_ESP FOREIGN KEY (IDESPECIALIDADE) REFERENCES ESPECIALIDADE(IDESPECIALIDADE)
);