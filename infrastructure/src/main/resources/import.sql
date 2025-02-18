create table tbl_order(
        order_id varchar(36) not null primary key,
        customer_id BIGINT(19) not null,
        total decimal(16, 5),
        created_time timestamp(6) not null,
        updated_time  timestamp(6) null
);

create table tbl_order_item(
    id varchar(36) not null primary key,
    item_name varchar(250) not null,
    item_price decimal(16, 5) not null,
    order_id varchar(36) not null references tbl_order(order_id),
    created_time timestamp(6) not null
 );

create table tbl_food(
    id int not null primary key,
    name varchar(255) not null,
    price decimal(16, 5) not null
);

create table tbl_ingredient(
    ingredient_id int not null primary key,
    name varchar(60) not null,
    quantity_in_stock int not null
);

create table tbl_food_ingredient(
    food_id int not null,
    ingredient_id int not null,
    constraint pk_food_ingredient primary key(food_id, ingredient_id),
    constraint fk_ingredient_id_ingredient foreign key(ingredient_id) references tbl_ingredient(ingredient_id),
    constraint fk_food_id_food foreign key(food_id) references tbl_food(food_id)
);

insert into tbl_ingredient(ingredient_id, name, quantity_in_stock) values(1, 'pao de brioche', 10);
insert into tbl_ingredient(ingredient_id, name, quantity_in_stock) values(2, 'hamburger de picanha', 11);
insert into tbl_ingredient(ingredient_id, name, quantity_in_stock) values(3, 'pickles', 11);
insert into tbl_food(food_id, name, price) values(1, 'hamburger picanhoso', 30.55);
insert into tbl_food(food_id, name, price) values(2, 'hamburger premium', 40.90);
insert into tbl_food_ingredient values(1, 1);
insert into tbl_food_ingredient values(1, 2);
insert into tbl_food_ingredient values(2, 1);
insert into tbl_food_ingredient values(2, 2);
