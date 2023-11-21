CREATE TABLE `tables`
(
    `id`          integer PRIMARY KEY,
    `tableNumber` integer,
    `inUseStart`  timestamp,
    `inUse`       tinyint
);

CREATE TABLE `reservations`
(
    `id`        integer PRIMARY KEY,
    `name`      varchar(255),
    `startTime` timestamp
);

CREATE TABLE `reservationTables`
(
    `tableId`       integer,
    `reservationId` integer
);

ALTER TABLE `reservationTables`
    ADD FOREIGN KEY (`tableId`) REFERENCES `tables` (`id`);

ALTER TABLE `reservationTables`
    ADD FOREIGN KEY (`reservationId`) REFERENCES `reservations` (`id`);
