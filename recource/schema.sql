-- Customer Domain
CREATE TABLE address (
    id UUID PRIMARY KEY,
    street VARCHAR(255),
    house_number VARCHAR(50),
    zip_code VARCHAR(20)
);

CREATE TABLE customer (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255),
    address_id UUID,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

-- Product Domain
CREATE TABLE category (
    id UUID PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(255)
);

CREATE TABLE product (
    id UUID PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(255),
    available_quantity INT,
    price DECIMAL(10,2),
    category_id UUID,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Order Domain
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    order_date DATE,
    reference VARCHAR(100),
    customer_id UUID,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE order_line (
    id UUID PRIMARY KEY,
    quantity INT,
    order_id UUID,
    product_id UUID,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Payment Domain
CREATE TABLE payment (
    id UUID PRIMARY KEY,
    reference VARCHAR(100),
    amount DECIMAL(10,2),
    status VARCHAR(50),
    order_id UUID,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Notification Domain
CREATE TABLE notification (
    id UUID PRIMARY KEY,
    sender VARCHAR(255),
    recipient VARCHAR(255),
    content TEXT,
    date TIMESTAMP,
    order_id UUID,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
