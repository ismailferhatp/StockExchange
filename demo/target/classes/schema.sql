CREATE TABLE STOCK_EXCHANGE (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                description VARCHAR(255),
                                live_in_market BOOLEAN
);

CREATE TABLE STOCK (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description VARCHAR(255),
                       current_price DECIMAL(10, 2),
                       last_update TIMESTAMP
);

CREATE TABLE STOCK_EXCHANGE_STOCK (
                                      stock_exchange_id BIGINT,
                                      stock_id BIGINT,
                                      PRIMARY KEY (stock_exchange_id, stock_id),
                                      FOREIGN KEY (stock_exchange_id) REFERENCES STOCK_EXCHANGE(id) ON DELETE CASCADE,
                                      FOREIGN KEY (stock_id) REFERENCES STOCK(id) ON DELETE CASCADE
);
-- Create the Users table
CREATE TABLE USER_INFO (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);



