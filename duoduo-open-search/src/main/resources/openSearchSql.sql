CREATE TABLE `order`(
    id int (11) NOT NULL AUTO_INCREMENT,
    order_group_id int(11) NOT NULL,
    coin_pair_choice_id int (11) NOT NULL,
    trade_average_price int not null default 0,
    trade_numbers int not null default 0,
    trade_cost int not null default 0,
    trade_type int not null default 1,
    statue tinyint not null default 1,
    created_at timestamp not null default  CURRENT_TIMESTAMP ,
    updated_at timestamp not null default CURRENT_TIMESTAMP ,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_group(
    id int (11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL default '',
    end_profit_ratio int (11) NOT NULL default 0,
    is_end tinyint not null default 0,
    end_type tinyint not null default 1,
    state tinyint not null default 1,
    create_at timestamp not null default  CURRENT_TIMESTAMP ,
    updated_at timestamp not null default CURRENT_TIMESTAMP ,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
