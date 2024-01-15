insert into waitlistdb.table_type (id, name, notes, number_of_seats, max_join_length)
values (1, 'low-top', 'It''s really untall', 2, 3),
       (2, 'high-top', 'It''s really tall', 4, 0),
       (3, 'Banquet', 'General purpose table', 4, 8),
       (4, 'Bar', 'For sitting and waiting and stuff', 6, 0),
       (5, 'Ceremony Table', 'Really big table', 10, 0);



-- insert into waitlistdb.tables (id, number_of_seats, status, status_updated, table_number, table_type_id, display_name)
-- values (1, 0, 99, '2024-01-10 01:37:41.925403', 49, 3, 'Banquet 1'),
--        (2, 215, 3, '2024-01-09 21:42:41.925403', 83, 1, null),
--        (3, 15, 1, null, 11, 4, 'Bar 1'),
--        (4, 0, 99, null, 36, 3, 'Banquet 2'),
--        (5, 0, 99, '2024-01-10 01:19:41.925403', 21, 2, 'High Top 1'),
--        (6, 0, 99, '2024-01-10 01:24:41.925403', 12, 4, 'Bar 2'),
--        (7, 215, 3, '2024-01-09 23:53:41.925403', 90, 3, 'Banquet 3'),
--        (8, 215, 3, '2024-01-09 23:45:41.925403', 88, 5, 'Ceremony Table');