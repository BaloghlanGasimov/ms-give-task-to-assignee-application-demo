create table tasks(
    id serial primary key ,
    subject varchar,
    description varchar,
    reporter varchar,
    created_date timestamp,
    status varchar,
    telesale_id int,
    FOREIGN KEY (telesale_id) REFERENCES telesales(id)
)