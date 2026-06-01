CREATE TABLE `department`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE `category`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

    `name` VARCHAR(100) NOT NULL,

    `department_id` INT UNSIGNED NOT NULL
);

CREATE TABLE `unit`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

    `abbreviation` VARCHAR(10) NOT NULL UNIQUE,

    `description` VARCHAR(50) NOT NULL
);

CREATE TABLE `product`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

    `name` VARCHAR(100) NOT NULL,

    `description` TEXT NULL,

    `current_stock` INT NOT NULL DEFAULT 0,

    `minimum_stock` INT NOT NULL DEFAULT 0,

    `category_id` INT UNSIGNED NOT NULL,

    `stock_unit_id` INT UNSIGNED NOT NULL,

    `package_content` DECIMAL(10,2) NOT NULL,

    `content_unit_id` INT UNSIGNED NOT NULL,

    `active` BOOLEAN DEFAULT TRUE
);

CREATE TABLE `movement`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

    `product_id` INT UNSIGNED NOT NULL,

    `quantity` INT NOT NULL,

    `type` ENUM('ENTRY', 'EXIT') NOT NULL,

    `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    `notes` TEXT NULL
);

ALTER TABLE
    `category`
ADD CONSTRAINT `category_department_id_foreign`
FOREIGN KEY(`department_id`)
REFERENCES `department`(`id`);

ALTER TABLE
    `product`
ADD CONSTRAINT `product_category_id_foreign`
FOREIGN KEY(`category_id`)
REFERENCES `category`(`id`);

ALTER TABLE
    `product`
ADD CONSTRAINT `product_stock_unit_id_foreign`
FOREIGN KEY(`stock_unit_id`)
REFERENCES `unit`(`id`);

ALTER TABLE
    `product`
ADD CONSTRAINT `product_content_unit_id_foreign`
FOREIGN KEY(`content_unit_id`)
REFERENCES `unit`(`id`);

ALTER TABLE
    `movement`
ADD CONSTRAINT `movement_product_id_foreign`
FOREIGN KEY(`product_id`)
REFERENCES `product`(`id`);