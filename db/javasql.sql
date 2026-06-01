CREATE TABLE department (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE category (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    department_id INTEGER NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE unit (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    abbreviation TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE product (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT NULL,
    current_stock INTEGER NOT NULL DEFAULT 0,
    minimum_stock INTEGER NOT NULL DEFAULT 0,
    category_id INTEGER NOT NULL,
    stock_unit_id INTEGER NOT NULL,
    package_content REAL NOT NULL,
    content_unit_id INTEGER NOT NULL,
    active INTEGER DEFAULT 1,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (stock_unit_id) REFERENCES unit(id),
    FOREIGN KEY (content_unit_id) REFERENCES unit(id)
);

CREATE TABLE movement (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    type TEXT NOT NULL CHECK (type IN ('ENTRY', 'EXIT')),
    date TEXT NOT NULL DEFAULT (datetime('now')),
    notes TEXT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);