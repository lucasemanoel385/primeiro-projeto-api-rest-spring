create table tb_categorias(

    id bigint not null auto_increment,
    nome varchar(100) not null unique,
    cor varchar(100) unique,

    primary key(id)

);