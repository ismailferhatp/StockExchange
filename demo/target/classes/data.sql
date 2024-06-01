INSERT INTO STOCK_EXCHANGE (name, description, live_in_market) VALUES
                                                                   ('NYSE', 'New York Stock Exchange', FALSE),
                                                                   ('NASDAQ', 'National Association of Securities Dealers Automated Quotations', FALSE);

INSERT INTO STOCK (name, description, current_price, last_update) VALUES
                                                                      ('AAPL', 'Apple Inc.', 150.00, '2024-05-28 10:00:00'),
                                                                      ('GOOGL', 'Alphabet Inc.', 2800.00, '2024-05-28 10:00:00');

INSERT INTO STOCK_EXCHANGE_STOCK (stock_exchange_id, stock_id) VALUES
                                                                   (1, 1),
                                                                   (1, 2),
                                                                   (2, 1),
                                                                   (2, 2);

-- Insert a sample user
INSERT INTO USER_INFO (username, password) VALUES ('ismailp', '$2a$10$JDrHJLvnM56NTX1RRRhri.eIkTLUzzK99en/QclkQWljrOp08bSHO');
