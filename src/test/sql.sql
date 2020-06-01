create table field_value (
                             id serial primary key,
                             field_id int,
                             foreign key (field_id) references field(id),
                             value varchar(255)

);
create table field_value (
                             id serial primary key,
                             field_id int,
                             foreign key (field_id) references field(id),
                             value varchar(255)

);

select *
from field_value inner join field f on field_value.field_id = f.id
                 inner join tables t on f.tables_id = t.id inner join owners o on t.owner_id = o.id
                 inner join sources s on o.source_id = s.id
where s.id=1;

insert into field_value (field_id, value) values (1,'test');
insert into field_value (field_id, value) values (1,'test2');
insert into field_value (field_id, value) values (2,'TEST');
update profile_result as test
set domain = 'tgdddshhhadg'
where field_id in  (
    select profile_result.field_id
    from profile_result inner join field f on profile_result.field_id = f.id
                        join tables t on f.tables_id = t.id join owners o on t.owner_id = o.id join sources s on o.source_id = s.id
    where s.id=1);

select profile_result.field_id
from profile_result inner join field f on profile_result.field_id = f.id
                    join tables t on f.tables_id = t.id join owners o on t.owner_id = o.id join sources s on o.source_id = s.id
where s.id=1