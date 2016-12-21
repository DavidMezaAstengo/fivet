create table control (
  id                            bigint auto_increment not null,
  fecha                         timestamp,
  prox_control                  timestamp,
  temperatura                   double,
  peso                          double,
  altura                        double,
  diagnostico                   varchar(255),
  nota                          varchar(255),
  numeroid                      integer not null,
  veterinario_id                bigint,
  version                       bigint not null,
  deleted                       boolean default false not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint pk_control primary key (id)
);

create table examen (
  id                            bigint auto_increment not null,
  numeroid                      integer not null,
  numero                        integer not null,
  nombre                        varchar(255),
  resultado                     varchar(255),
  medicamento                   varchar(255),
  fecha                         timestamp,
  version                       bigint not null,
  deleted                       boolean default false not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint pk_examen primary key (id)
);

create table paciente (
  id                            bigint auto_increment not null,
  numero                        integer not null,
  nombre                        varchar(255),
  fecha_nacimiento              timestamp,
  raza                          varchar(255),
  sexo                          varchar(13),
  color                         varchar(255),
  especie                       varchar(255),
  version                       bigint not null,
  deleted                       boolean default false not null,
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  constraint ck_paciente_sexo check ( sexo in ('Hembra','Macho','Indeterminado')),
  constraint pk_paciente primary key (id)
);

create table paciente_control (
  paciente_id                   bigint not null,
  control_id                    bigint not null,
  constraint pk_paciente_control primary key (paciente_id,control_id)
);

create table persona (
  id                            bigint auto_increment not null,
  rut                           varchar(255) not null,
  nombre                        varchar(255) not null,
  password                      varbinary(255) not null,
  direccion                     varchar(255),
  email                         varchar(255),
  tel_fijo                      varchar(255),
  tel_movil                     varchar(255),
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

alter table control add constraint fk_control_veterinario_id foreign key (veterinario_id) references persona (id) on delete restrict on update restrict;
create index ix_control_veterinario_id on control (veterinario_id);

alter table paciente_control add constraint fk_paciente_control_paciente foreign key (paciente_id) references paciente (id) on delete restrict on update restrict;
create index ix_paciente_control_paciente on paciente_control (paciente_id);

alter table paciente_control add constraint fk_paciente_control_control foreign key (control_id) references control (id) on delete restrict on update restrict;
create index ix_paciente_control_control on paciente_control (control_id);

alter table persona_paciente add constraint fk_persona_paciente_persona foreign key (persona_id) references persona (id) on delete restrict on update restrict;
create index ix_persona_paciente_persona on persona_paciente (persona_id);

alter table persona_paciente add constraint fk_persona_paciente_paciente foreign key (paciente_id) references paciente (id) on delete restrict on update restrict;
create index ix_persona_paciente_paciente on persona_paciente (paciente_id);

