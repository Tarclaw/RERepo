INSERT INTO clients(id, email, mobile_number, skype, first_name, last_name, login, password, is_buyer, is_leaser, is_renter, is_seller) VALUES(3, 'r.rich@gmail.com', '+1-097-999-3355', 'r.rich', 'Richie', 'Rich', 'dineiro_login', 'babosiki_pass', false, true, false, false);
INSERT INTO clients(id, email, mobile_number, skype, first_name, last_name, login, password, is_buyer, is_leaser, is_renter, is_seller) VALUES(4, 'd.donaldson@gmail.com', '+1-066-534-6842', 'd.donaldson', 'Donald', 'Donaldson', 'duck_login', 'kr9kr9', false, false, true, false);
INSERT INTO clients(id, email, mobile_number, skype, first_name, last_name, login, password, is_buyer, is_leaser, is_renter, is_seller) VALUES(5, 'j.johnson@gmail.com', '+1-099-897-3511', 'j.johnson', 'John', 'Johnson', 'johny_login', 'rieltoruzlo', false, false, true, true);

INSERT INTO real_estate_agents(id, email, mobile_number, skype, first_name, last_name, login, password, hired_date, salary) VALUES(1, 'm.public@realestate.com', '+1-014-777-3355', 'm.public', 'Marry', 'Public', 'marry_login', '123456', '2020-04-27', 5000.00);
INSERT INTO real_estate_agents(id, email, mobile_number, skype, first_name, last_name, login, password, hired_date, salary) VALUES(2, 'b.billy@realestate.com', '+1-014-515-2288', 'b.billy', 'Billy', 'Butkiss', 'billy_login', 'qwerty', '2020-04-27', 4500.00);

INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(1, 2000.00, 4000.00, 1000000.00, 'FOR_RENT', 1);
INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(2, 15000.00, 4200.00, 1500000.00, 'FOR_SALE', 2);
INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(3, 20000.00, 6000.00, 2000000.00, 'FOR_SALE', 1);
INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(4, 1500.00, 3000.00, 700000.00, 'FOR_RENT', 1);
INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(5, 12000.00, 5000.00, 1200000.00, 'FOR_SALE', 2);
INSERT INTO facility_objects(id, commission_amount, month_rent, price, status, agent_id) VALUES(6, 33000.00, 9000.00, 3300000.00, 'SOLD', 2);

INSERT INTO apartments(id, description, number_of_rooms, published_date_time, total_area, facility_object_id, apartment_number, floor) VALUES(1, 'top rated xata', 5, '2020-04-27 19:53:26.298205', 150, 1, 788, 25);

INSERT INTO facilities(id, description, number_of_rooms, published_date_time, total_area, facility_object_id) VALUES(6, 'generic description', 5, '2020-04-27 19:53:26.298205', 150, 6);

INSERT INTO basements(id, description, number_of_rooms, published_date_time, total_area, facility_object_id, it_commercial) VALUES(2, 'barbershop, ready business model', 3, '2020-04-27 19:53:26.298205', 200, 2, true);

INSERT INTO garages(id, description, number_of_rooms, published_date_time, total_area, facility_object_id, has_equipment, has_pit) VALUES(3, 'vehicles repair', 3, '2020-04-27 19:53:26.298205', 600, 3, true, true);

INSERT INTO houses(id, description, number_of_rooms, published_date_time, total_area, facility_object_id, has_backyard, has_garden, number_of_storeys) VALUES(4, 'new home with repair for big family', 8, '2020-04-27 19:53:26.298205', 300, 4, true, true, 3);

INSERT INTO storages(id, description, number_of_rooms, published_date_time, total_area, facility_object_id, commercial_capacity, has_cargo_equipment) VALUES(5, 'huge storage for drug dealers', 3, '2020-04-27 19:53:26.298205', 700, 5, 600, true);

INSERT INTO addresses(city, district, facility_number, postcode, street, facility_id) VALUES('New York', 'NY', 255, 100305, 'Manhattan', 1);
INSERT INTO addresses(city, district, facility_number, postcode, street, facility_id) VALUES('New York', 'NY', 28, 55322, 'Madison Ave', 2);
INSERT INTO addresses(city, district, facility_number, postcode, street, facility_id) VALUES('New York', 'NY', 143, 200645, 'Bronx', 3);
INSERT INTO addresses(city, district, facility_number, postcode, street, facility_id) VALUES('New York', 'NY', 657, 99887, 'Brooklyn', 4);
INSERT INTO addresses(city, district, facility_number, postcode, street, facility_id) VALUES('New York', 'NY', 665, 888132, 'Queens', 5);

INSERT INTO clients_agents(agent_id, client_id) VALUES(1, 3);
INSERT INTO clients_agents(agent_id, client_id) VALUES(1, 4);
INSERT INTO clients_agents(agent_id, client_id) VALUES(2, 5);

INSERT INTO clients_facilities(client_id, facility_id) VALUES(4, 5);
INSERT INTO clients_facilities(client_id, facility_id) VALUES(3, 4);
INSERT INTO clients_facilities(client_id, facility_id) VALUES(5, 3);
INSERT INTO clients_facilities(client_id, facility_id) VALUES(4, 2);
INSERT INTO clients_facilities(client_id, facility_id) VALUES(3, 1);
