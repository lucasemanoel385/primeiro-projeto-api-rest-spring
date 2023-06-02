create table tb_videos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(100) not null,
    url varchar(1000) not null,

    primary key(id)

);