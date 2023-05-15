
INSERT INTO `plazoleta`.`restaurant`
(
`address`,
`id_owner`,
`name`,
`nit`,
`phone`,
`url_logo`
)
VALUES
(
234,
1,
"testRestaurant",
12345677,
"+53322222222",
"http://url_test.com");

INSERT INTO `plazoleta`.`food_order`(`date`,`id_chef`,`id_client`,`status`,`id_restaurant`)
VALUES("2020-06-01 00:00:00.000000",1,2,1,1);

INSERT INTO `plazoleta`.`category`(`description`,`name`)VALUES("TestDescription","pizza");

INSERT INTO `plazoleta`.`dish`(`active`,`description`,`name`,`price`,`url_image`,`id_category`,`id_restaurant`)
VALUES(1,"dishTest","dishDescription",123455,"http://testImage.com",1,1);

INSERT INTO `plazoleta`.`order_dish`(`amount`,`id_dish`,`id_order`)VALUES(2,1,1);

