-- Create Category table
CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY NOT NULL ,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create Product table
CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY NOT NULL ,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    available_quantity DOUBLE PRECISION NOT NULL,
    price NUMERIC(38,2),
    category_id UUID NOT NULL
);
