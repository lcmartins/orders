
create table tbl_order(
        order_id varchar(36) not null primary key,
        customer_id UNSIGNED BIGINT(19) not null,
        total decimal(16, 5),
        created_time timestamp(6) not null,
        update_time  timestamp(6)
);

create tbl_order_item(
    id varchar(36) not null primary key,
    name varchar(250) not null,
    price decimal(16, 5) not null,
    order_id varchr(36) not null references tbl_order(order_id),
    created_time timestamp(6) not null
 );

create table tbl_food(
    food_id int not null primary key,
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


