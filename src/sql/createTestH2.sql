drop table if exists Employee
create table Employee(id int primary key, name varchar(255))
insert into Employee values(1,'Hello')
insert into Employee values(2,'World')

select * from Employee order by id