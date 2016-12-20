alter table persona_paciente drop constraint if exists fk_persona_paciente_persona;
drop index if exists ix_persona_paciente_persona;

alter table persona_paciente drop constraint if exists fk_persona_paciente_paciente;
drop index if exists ix_persona_paciente_paciente;

drop table if exists paciente;

drop table if exists persona;

drop table if exists persona_paciente;

