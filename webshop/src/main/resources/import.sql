INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Taylor", "Ellis", "taylor", "$2a$10$6j2bUrZQoa3e2/fVtEMqvOkkGnY6teB1tx1nhhKyViwHUoMKWmYu2", 0);
INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Josh", "Paul", "josh", "$2a$10$l.zn./MiJ7XC4kSif89MD.27jW8hfmYUnwnB6dy7nwfl4NHGJNWay", 0);
INSERT INTO admins (first_name, last_name, username, password, blocked) VALUES ("Cody", "Cook", "cody", "$2a$10$0W3JYr5E437b62AMv/dLKeOn/DrdchHPn9nyDTgOogdMgojgPIxEO", 0);

INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Isabel", "Burns", "isabel", "$2a$10$Hr2h.8YJd3j0lZe/w8aq7.Z7UL88.SHLvti41JVc3hSiptNEhGVjm", 0, "2125 Chestnut St.");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Nathan", "Gibson", "nathan", "$2a$10$pJpCGq.h9pN33tVaNhdVpeAbu0quQkpXZVb2Jlfja0ydiE8CSwNDa", 1, "84 Fairfield Ave.");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Jacob", "Hill", "jacob", "$2a$10$zvsn/xMz98EdOkoQSDHm4.X0X9cyjAl1eNC/MtOyIPkTQg4JBjrmq", 0, "8564 Fairfield St.");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Lara", "Gill", "lara", "$2a$10$zvsn/xMz98EdOkoQSDHm4.X0X9cyjAl1eNC/MtOyIPkTQg4JBjrmq", 0, "33 Thompson Lane");
INSERT INTO buyers (first_name, last_name, username, password, blocked, address) VALUES ("Holly", "Francis", "holly", "$2a$10$zvsn/xMz98EdOkoQSDHm4.X0X9cyjAl1eNC/MtOyIPkTQg4JBjrmq", 0, "31 Blue Spring St.");

INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Toby", "Smith", "toby", "$2a$10$seFhCMfK4WXhbgJvSItUKOk.vVg4gDIIfmGRUDWDfGqEshoQKrUlm", 0, "2021-10-20 10:00:00.000000", "tobysmith@example.com", "9579 Prince Rd.", "Toby Mobile")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Isaac", "Hill", "isaac", "$2a$10$mhDt2DFKQj8ok0u9oPYeYuH2jHUjIatBIwpIEREsA9bVZHxrFQeQS", 1, "2021-11-25 11:00:00.000000", "isaachill@example.com", "7553 Argyle St.", "Isaac business")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Emma", "Cortez", "emma", "$2a$10$59qX/j7b6z4wjMBqMbx7COeqQHjsb9NRHoiVh2Cv9qPrBI9RRXR9O", 0, "2021-12-30 12:00:00.000000", "emmacortez@example.com", "834 Selby Rd.", "Emma Audio")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Nolan", "Sanchez", "nolan", "$2a$10$59qX/j7b6z4wjMBqMbx7COeqQHjsb9NRHoiVh2Cv9qPrBI9RRXR9O", 0, "2021-12-30 12:00:00.000000", "nolansanchez@example.com", "7856 Poplar Ave.", "Sanchez pet shop")
INSERT INTO sellers (first_name, last_name, username, password, blocked, operates_from, email, address, name) VALUES ("Elliott", "Hess", "elliott", "$2a$10$59qX/j7b6z4wjMBqMbx7COeqQHjsb9NRHoiVh2Cv9qPrBI9RRXR9O", 1, "2021-12-30 12:00:00.000000", "elliotthess@example.com", "97 Middle River Ave.", "Hess business")

INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Redmi Note 10 Pro", "Xiaomi Redmi Note 10 Pro, 108MP, 6.67 inches 120Hz Amoled, 33W fast charging, Octa-core (2x2.3 GHz Kryo 470 Gold & 6x1.8 GHz Kryo 470 Silver), 128GB 6GB RAM", 300, "mobilePhones", "https://mi-srbija.rs/storage/product/images/df/91/redmi-note-10-pro-1246.jpg", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Samsung Galaxy A12", "Samsung Galaxy A12, 48MP, 6.5 inches IPS, 15W fast charging, Octa-core (4x2.35 GHz Cortex-A53 & 4x1.8 GHz Cortex-A53), 64GB 4GB RAM", 160, "mobilePhones", "https://www.tehnomedia.rs/products/73066_huge_0_cached.jpg", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Huawei P50 Pro", "Huawei P50 Pro, 50MP, 6.6 inches 120Hz OLED, 66W fast charging, Octa-core (1x2.84 GHz Kryo 680 & 3x2.42 GHz Kryo 680 & 4x1.80 GHz Kryo 680), 256GB 8GB RAM", 900, "mobilePhones", "https://media.router-switch.com/media/catalog/product/cache/b90fceee6a5fa7acd36a04c7b968181c/h/u/huawei-p50-pro-black-1_1.jpg", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("OnePlus 10 Pro", "OnePlus 10 Pro, 50MP, 6.7 inches 120Hz Amoled, 80W fast charging, Octa-core (1x3.00 GHz Cortex-X2 & 3x2.50 GHz Cortex-A710 & 4x1.80 GHz Cortex-A510), 256GB 8GB RAM", 820, "mobilePhones", "https://www.bajtbox.com/wp-content/uploads/2022/01/OnePlus-10-Pro-1-1.jpg", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Google Pixel 6 Pro", "Google Pixel 6 Pro, 50MP, 6.67 inches 120Hz Amoled, 30W fast charging, Octa-core (2x2.80 GHz Cortex-X1 & 2x2.25 GHz Cortex-A76 & 4x1.80 GHz Cortex-A55), 256GB 12GB RAM", 950, "mobilePhones", "https://shop.ee.co.uk/medias/pixel-6-pro-black-buds-desktop-Format-488?context=bWFzdGVyfHJvb3R8MjQ0OTIyfGltYWdlL3BuZ3xzeXMtbWFzdGVyL3Jvb3QvaGQxL2gzZS85NzgzNzgwODM1MzU4L3BpeGVsLTYtcHJvLWJsYWNrLWJ1ZHMtZGVza3RvcF9Gb3JtYXQtNDg4fGQ5YjFlZGRmYWI2ODIxMzZhODA5ZjIyNWJkMWE2YzBhZjY3OTlmMWRhNzFlYjFiNjhiYTJhMWM3ZWEyMGYyNzY", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Apple iPhone 13", "Apple iPhone 13, 12MP, 6.1 inches OLED, 23W fast charging, Hexa-core (2x3.22 GHz Avalanche + 4xX.X GHz Blizzard), 128GB 4GB RAM", 860, "mobilePhones", "https://cellbuddy.in/wp-content/uploads/2021/09/Apple-iPhone-13-Pro-Smartphones-491997730-i-2-1200Wx1200H.jpeg", 1)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("WILSON NBA Basketball", "Wilson NBA Forge Series Indoor/Outdoor Basketball - Forge Pro, Black, Size 5 - 27.5'", 30, "sport", "https://www.wilson.com/en-us/media/catalog/product/8/5/8522ac14-ec8a-4ccb-aaa4-267b88b69472_mpmjr6mmj60yscaj.png", 2)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("SENNHEISER HD 660 S", "SENNHEISER HD 660 S - HiRes Audiophile Open Back Headphone, Frequency response 10 to 41,000 Hz ( 10 dB), Impedance 150 ohm", 500, "audio", "https://shop.4audio.rs/wp-content/uploads/2017/11/product_detail_x2_desktop_HD_660_S_Isofront_RGB_red.jpg", 3)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("FiiO E10K", "FiiO E10K USB DAC and Headphone Amplifier (Black), Output Wattage 10 Watts, Item Weight 77 Grams", 75, "audio", "https://www.ubuy.vn/productimg/?image=aHR0cHM6Ly9lbmNyeXB0ZWQtdGJuMC5nc3RhdGljLmNvbS9zaG9wcGluZz9xPXRibjpBTmQ5R2NRbDEyVHhxc1ZRZldiN01adzBVRnVyVWhDWTR1cEpyNVdXa3pqM0VJa29hajR6MG9SVGhjaXdZRVRYM0hZb044WWpDeEl3bFpZJmFtcDthbXA7dXNxcD1DQVk.jpg", 3)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("LINSOUL TIN Audio T2", "LINSOUL TIN Audio T2 HiFi 2DD Double Dynamic Drive in Ear Earphone Bass DJ Metal Headphones, 3.5 mm MMCX", 50, "audio", "https://m.media-amazon.com/images/I/51LQuMegcbL._AC_SL1000_.jpg", 3)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("EveryYay Memory Foam Dog Bed", "EveryYay Grey Lounger Memory Foam Dog Bed offers your pet ultimate support as they stretch out. Its channeled chipped memory foam molds to your pet's body for a custom fit experience every time they lay down to rest, 28'' L X 28'' W", 50, "pets", "https://assets.petco.com/petco/image/upload/c_pad,dpr_1.0,f_auto,q_auto,h_636,w_636/c_pad,h_636,w_636/3283536-center-8", 4)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Donut Bed", "Super Soft Cozy Fleece Pet Bed Small dog, Puppy Cat Kitten bed Mat 21'' diameter 7'' height Machine Gentle cycle Washable Inside cushion can be removable", 20, "pets", "https://i0.wp.com/petshopamerica.com/wp-content/uploads/2020/11/122152359_423024615773944_7140073385841948977_n.jpg?fit=600%2C600&ssl=1", 4)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Automatic Pet Water Bottle Food Dispenser", "Gravity Feeder Cat Dog Bowl 2.5 cups 600 ml, size 7'' x5 '' height 7'', non slip bottom", 15, "pets", "https://i0.wp.com/petshopamerica.com/wp-content/uploads/2021/04/silo-plastik-mama-su-kabi-600-ml-su-kaplari-flip-22532-24-B.jpg?fit=600%2C900&ssl=1", 4)
INSERT INTO products (name, description, price, product_type, picture_path, seller_id) VALUES ("Dog Leash", "Dog Leash Lead Heavy Duty Climbing Rope Shock Absorption Spring 1.2m 1.5cm Large XL, soft and thick padded handle. Durable met-able D-ring for easy clip and 360 rotate clasp swivel.", 22, "pets", "https://i0.wp.com/petshopamerica.com/wp-content/uploads/2020/10/SLL310purplespringrope.jpg?fit=800%2C1200&ssl=1", 4)

INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2021-10-20 12:00:00.000000", 1, 5, "Awesome phone, I'm very happy with the purchase!", 0, 0, 1)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2021-10-20 12:00:00.000000", 1, 2, "I'm very disappointed, the headphones were broken when they arrived! As for dac amp nothing special, but for that price it's fine I guess.", 0, 0, 2)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2021-10-20 12:00:00.000000", 1, 4, "It may be a little overpriced, but overall I'm happy with this purchase.", 0, 0, 3)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2022-02-08 12:00:00.000000", 1, 5, "I'm so happy that I finally got this headphones, love them!", 1, 0, 3)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2021-10-20 12:00:00.000000", 1, 5, "As great as expected!", 0, 0, 5)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2022-02-05 12:00:00.000000", 1, 5, "Great ball, meets my needs.", 0, 0, 1)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2022-01-27 12:00:00.000000", 1, 4, "Pretty good ball, not the best though.", 1, 0, 3)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2022-02-03 12:00:00.000000", 1, 3, "Nice budget dac amp, but I was expecting more to be honest...", 0, 0, 1)
INSERT INTO orders (time, delivered, rate, comment, anonymous_comment, archived_comment, buyer_id) VALUES ("2022-02-04 12:00:00.000000", 1, 3, "I like it a lot, but I waited over 2 weeks for delivery...", 0, 0, 4)

INSERT INTO items (quantity, product_id, order_id) VALUES (1, 1, 1)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 8, 2)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 9, 2)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 9, 3)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 8, 4)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 1, 5)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 7, 6)
INSERT INTO items (quantity, product_id, order_id) VALUES (2, 7, 7)
INSERT INTO items (quantity, product_id, order_id) VALUES (2, 9, 8)
INSERT INTO items (quantity, product_id, order_id) VALUES (1, 9, 9)

INSERT INTO sales (percentage, from_date, to_date, text, seller_id) VALUES (30, "2021-10-20 12:00:00.000000", "2022-10-20 12:00:00.000000", "super sale", 1)
INSERT INTO sales (percentage, from_date, to_date, text, seller_id) VALUES (20, "2021-12-25 12:00:00.000000", "2022-02-25 12:00:00.000000", "winter sale", 3)

INSERT INTO product_sale (product_id, sale_id) VALUES (1, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (2, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (3, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (4, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (5, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (6, 1)
INSERT INTO product_sale (product_id, sale_id) VALUES (8, 2)
INSERT INTO product_sale (product_id, sale_id) VALUES (9, 2)
INSERT INTO product_sale (product_id, sale_id) VALUES (10, 2)