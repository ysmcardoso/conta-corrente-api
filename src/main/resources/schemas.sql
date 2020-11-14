
drop table agencia if exists
drop table conta_corrente if exists
drop table pessoa if exists
drop table transacao if exists
drop sequence if exists seq_transacao
create sequence seq_transacao start with 1 increment by 50
create table agencia (nr_ag varchar(255) not null, primary key (nr_ag))
create table conta_corrente (nr_cta varchar(255) not null, nr_ag varchar(255) not null, sld decimal(19,2) not null, cpf_cnpj varchar(255), primary key (nr_ag, nr_cta))
create table pessoa (cpf_cnpj varchar(255) not null, tp_pess varchar(255) not null, primary key (cpf_cnpj))
create table transacao (id integer not null, data timestamp not null, id_tp integer not null, valor decimal(19,2) not null, id_nr_cta varchar(255) not null, id_nr_ag varchar(255) not null, primary key (id))
alter table conta_corrente add constraint UK_jwb9youesj6407ivskq9kl26v unique (nr_cta)
alter table conta_corrente add constraint FKelp6wdvfcny7m2vc4i4jfmyj9 foreign key (nr_ag) references agencia
alter table conta_corrente add constraint FKt5dlpors6dlr5h5d99hbiu4s9 foreign key (cpf_cnpj) references pessoa
alter table transacao add constraint FKqoi7ycxtcb7ija5ljw97poix8 foreign key (id_nr_cta, id_nr_ag) references conta_corrente