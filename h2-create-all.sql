create table paciente (
  id                            bigint auto_increment not null,
  numero                        integer,
  nombre                        varchar(255),
  fecha_nacimiento              timestamp,
  raza                          varchar(255),
  sexo                          varchar(13),
  color                         varchar(255),
  version                       bigint not null,
  deleted                       boolean default false not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint ck_paciente_sexo check ( sexo in ('Hembra','Macho','Indeterminado')),
  constraint pk_paciente primary key (id)
);

create table persona (
  id                            bigint auto_increment not null,
  rut                           varchar(255) not null,
  nombre                        varchar(255) not null,
  password                      varbinary(255) not null,
  tipo                          varchar(11) not null,
  version                       bigint not null,
  deleted                       boolean default false not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint ck_persona_tipo check ( tipo in ('Veterinario','Cliente')),
  constraint pk_persona primary key (id)
);

create table persona_paciente (
  persona_id                    bigint not null,
  paciente_id                   bigint not null,
  constraint pk_persona_paciente primary key (persona_id,paciente_id)
);

alter table persona_paciente add constraint fk_persona_paciente_persona foreign key (persona_id) references persona (id) on delete restrict on update restrict;
create index ix_persona_paciente_persona on persona_paciente (persona_id);

alter table persona_paciente add constraint fk_persona_paciente_paciente foreign key (paciente_id) references paciente (id) on delete restrict on update restrict;
create index ix_persona_paciente_paciente on persona_paciente (paciente_id);

