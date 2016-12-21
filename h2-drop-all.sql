alter table control drop constraint if exists fk_control_veterinario_id;
drop index if exists ix_control_veterinario_id;

alter table paciente_control drop constraint if exists fk_paciente_control_paciente;
drop index if exists ix_paciente_control_paciente;

alter table paciente_control drop constraint if exists fk_paciente_control_control;
drop index if exists ix_paciente_control_control;

alter table persona_paciente drop constraint if exists fk_persona_paciente_persona;
drop index if exists ix_persona_paciente_persona;

alter table persona_paciente drop constraint if exists fk_persona_paciente_paciente;
drop index if exists ix_persona_paciente_paciente;

drop table if exists control;

drop table if exists examen;

drop table if exists paciente;

drop table if exists paciente_control;

drop table if exists persona;

drop table if exists persona_paciente;

