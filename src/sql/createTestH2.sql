drop table if exists test
create table test(id int primary key, name varchar(255))
insert into test values(1,'Hello')
insert into test values(2,'World')

select * from test order by id