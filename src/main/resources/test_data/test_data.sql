
INSERT INTO `bidlist` (`account`, `bid_quantity`,`type`)
	VALUES 	('account1', '10.00', 'type1'),
			('account2', '20.00', 'type2'),
			('account3', '30.00', 'type3'),
			('account4', '40.00', 'type4'),
			('account1', '50.00', 'type1');


INSERT INTO `curvepoint` (`curve_id`, `term`, `value`)
	VALUES 		('1', '1.00', '10.00'),
			('2', '2.00', '20.00'),
			('3', '3.00', '30.00'),
			('4', '4.00', '40.00'),
			('5', '5.00', '50.00');

INSERT INTO `rating` (`fitch_rating`, `moodys_rating`, `order_number`, `sand_prating`)
	VALUES 	('fitch1', 'moodys1', '1', 'sandp1'),
			('fitch2', 'moodys2', '2', 'sandp2'),
			('fitch3', 'moodys3', '3', 'sandp3'),
			('fitch4', 'moodys4', '4', 'sandp4'),
			('fitch5', 'moodys5', '1', 'sandp5');




			INSERT INTO `trade` (`account`,`buy_quantity`,`type`)
	 VALUES ('account1', '10.00', 'type1'),
			('account2', '20.00', 'type2'),
			('account3', '30.00', 'type3'),
			('account4', '40.00', 'type4'),
			('account1', '50.00', 'type1');
			
INSERT INTO `rulename` (`description`, `json`, `name`, `sql_part`, `sql_str`, `template`)
	VALUES 	('description1', 'json1', 'name1', 'sql_part1', 'sql_str1', 'template1'),
			('description2', 'json2', 'name2', 'sql_part2', 'sql_str2', 'template2'),
			('description3', 'json3', 'name3', 'sql_part3', 'sql_str3', 'template3'),
			('description4', 'json4', 'name4', 'sql_part4', 'sql_str4', 'template4'),
			('description5', 'json5', 'name5', 'sql_part5', 'sql_str5', 'template5');

insert into Users(fullname, username, password, role) values("User1", "user1", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "USER");
insert into Users(fullname, username, password, role) values("User2", "user2", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "USER");
insert into Users(fullname, username, password, role) values("User3", "user3", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "USER");
insert into Users(fullname, username, password, role) values("User4", "user4", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "USER");
insert into Users(fullname, username, password, role) values("User5", "user5", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "USER");			
insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$12$Oy/UKrmNbRccE9Gc6LZ3C.n20H//ezfWYKJAuM8XwMrqbcq6V1qPK", "ADMIN")
