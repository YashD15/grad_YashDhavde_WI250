CREATE TABLE sites (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    ownername VARCHAR(100),
    ownerpassword VARCHAR(100),
	deposit DECIMAL(10,2),
	maintenanceRem DECIMAL(10,2) DEFAULT 0,
	update_type TEXT DEFAULT null,
	update_value TEXT DEFAULT null,
    update_status VARCHAR(20) DEFAULT 'APPROVED'
);

DROP TABLE sites;
TRUNCATE TABLE sites;

--Dummy entries
INSERT INTO sites (type, area, ownername, ownerpassword, deposit) VALUES
('Villa', 2400, 'Rajesh', 'rajesh123', 21000),
('Apartment', 2400, 'Rakesh', 'rakesh123', 20000),
('IndependentHouse', 1500, 'Ramesh', 'ramesh123', 19000),
('OpenSite', 1200, 'Ritesh', 'ritesh123', 18000);

SELECT * FROM sites ORDER BY id;

--For grouping the areas to check for limit of each.
SELECT area, COUNT(*) as count FROM sites GROUP BY area ORDER BY area DESC;


