create table parking_lot (
    id              bigint          not null,
    parking_lot_id     varchar(10)     not null,
    capacity        bigint          not null,
    available_position_count bigint    not null,
    primary key (id),
    unique(parking_lot_id)
)