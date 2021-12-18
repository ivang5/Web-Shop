INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Taylor", "Ellis", "taylor", "$2a$10$6j2bUrZQoa3e2/fVtEMqvOkkGnY6teB1tx1nhhKyViwHUoMKWmYu2", 0);
INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Josh", "Paul", "josh", "$2a$10$l.zn./MiJ7XC4kSif89MD.27jW8hfmYUnwnB6dy7nwfl4NHGJNWay", 0);
INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Cody", "Cook", "cody", "$2a$10$0W3JYr5E437b62AMv/dLKeOn/DrdchHPn9nyDTgOogdMgojgPIxEO", 0);

INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Isabel", "Burns", "isabel", "$2a$10$Hr2h.8YJd3j0lZe/w8aq7.Z7UL88.SHLvti41JVc3hSiptNEhGVjm", 0, "2125 Chestnut St.");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Nathan", "Gibson", "nathan", "$2a$10$pJpCGq.h9pN33tVaNhdVpeAbu0quQkpXZVb2Jlfja0ydiE8CSwNDa", 1, "84 Fairfield Ave.");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Jacob", "Hill", "jacob", "$2a$10$zvsn/xMz98EdOkoQSDHm4.X0X9cyjAl1eNC/MtOyIPkTQg4JBjrmq", 0, "8564 Fairfield St.");

INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Toby", "Smith", "toby", "$2a$10$seFhCMfK4WXhbgJvSItUKOk.vVg4gDIIfmGRUDWDfGqEshoQKrUlm", 0, "2021-10-20 10:00:00.000000", "tobysmith@example.com", "9579 Prince Rd.", "Toby Mobile")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Isaac", "Hill", "isaac", "$2a$10$mhDt2DFKQj8ok0u9oPYeYuH2jHUjIatBIwpIEREsA9bVZHxrFQeQS", 1, "2021-11-25 11:00:00.000000", "isaachill@example.com", "7553 Argyle St.", "Isaac business")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Emma", "Cortez", "emma", "$2a$10$59qX/j7b6z4wjMBqMbx7COeqQHjsb9NRHoiVh2Cv9qPrBI9RRXR9O", 0, "2021-12-30 12:00:00.000000", "emmacortez@example.com", "834 Selby Rd.", "Emma Audio")

INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Redmi Note 10 Pro", "Xiaomi Redmi Note 10 Pro, 108MP, 6.67 inches 120Hz Amoled, 33W fast charging, Octa-core (2x2.3 GHz Kryo 470 Gold & 6x1.8 GHz Kryo 470 Silver), 128GB 6GB RAM", 300, "mobilePhones", "picture_1", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Samsung Galaxy A12", "Samsung Galaxy A12, 48MP, 6.5 inches IPS, 15W fast charging, Octa-core (4x2.35 GHz Cortex-A53 & 4x1.8 GHz Cortex-A53), 64GB 4GB RAM", 160, "mobilePhones", "picture_2", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Huawei P50 Pro", "Huawei P50 Pro, 50MP, 6.6 inches 120Hz OLED, 66W fast charging, Octa-core (1x2.84 GHz Kryo 680 & 3x2.42 GHz Kryo 680 & 4x1.80 GHz Kryo 680), 256GB 8GB RAM", 900, "mobilePhones", "picture_3", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("OnePlus 10 Pro", "OnePlus 10 Pro, 50MP, 6.7 inches 120Hz Amoled, 80W fast charging, Octa-core (1x3.00 GHz Cortex-X2 & 3x2.50 GHz Cortex-A710 & 4x1.80 GHz Cortex-A510), 256GB 8GB RAM", 820, "mobilePhones", "picture_4", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Google Pixel 6 Pro", "Google Pixel 6 Pro, 50MP, 6.67 inches 120Hz Amoled, 30W fast charging, Octa-core (2x2.80 GHz Cortex-X1 & 2x2.25 GHz Cortex-A76 & 4x1.80 GHz Cortex-A55), 256GB 12GB RAM", 950, "mobilePhones", "picture_5", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Apple iPhone 13", "Apple iPhone 13, 12MP, 6.1 inches OLED, 23W fast charging, Hexa-core (2x3.22 GHz Avalanche + 4xX.X GHz Blizzard), 128GB 4GB RAM", 860, "mobilePhones", "picture_6", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("WILSON NBA Basketball", "Wilson NBA Forge Series Indoor/Outdoor Basketball - Forge Pro, Black, Size 5 - 27.5'", 30, "sport", "picture_7", 2)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("SENNHEISER HD 660 S", "SENNHEISER HD 660 S - HiRes Audiophile Open Back Headphone, Frequency response 10 to 41,000 Hz ( 10 dB), Impedance 150 ohm", 500, "audio", "picture_8", 3)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("FiiO E10K", "FiiO E10K USB DAC and Headphone Amplifier (Black), Output Wattage 10 Watts, Item Weight 77 Grams", 75, "audio", "picture_9", 3)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("LINSOUL TIN Audio T2", "LINSOUL TIN Audio T2 HiFi 2DD Double Dynamic Drive in Ear Earphone Bass DJ Metal Headphones, 3.5 mm MMCX", 50, "audio", "picture_10", 3)

INSERT INTO sales (percentage, from_date, to_date, text, seller_id) VALUES (30, "2021-10-20 12:00:00.000000", "2022-10-20 12:00:00.000000", "godisnje snizenje", 1)
INSERT INTO sales (percentage, from_date, to_date, text, seller_id) VALUES (20, "2021-12-25 12:00:00.000000", "2022-02-25 12:00:00.000000", "zimsko snizenje", 3)

INSERT INTO product_sale (product_id, sale_id) VALUES (3, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (4, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (6, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (8, 2)