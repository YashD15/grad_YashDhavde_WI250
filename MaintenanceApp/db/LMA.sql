CREATE TABLE layout_sites (
    id SERIAL PRIMARY KEY,
    site_type VARCHAR(50) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    owner_name VARCHAR(100),
    owner_password VARCHAR(100),
    pending_update TEXT DEFAULT null,
    update_status VARCHAR(20) DEFAULT 'APPROVED'
);

DROP TABLE layout_sites;
TRUNCATE TABLE layout_sites;

--Dummy entries
INSERT INTO layout_sites (site_type, area, owner_name, owner_password) VALUES
('villa', 2400, 'Rajesh', 'rajesh123'),
('apartment', 2400, 'Rakesh', 'rakesh123'),
('independent_house', 1500, 'Ramesh', 'ramesh123'),
('open_site', 1200, 'Ritesh', 'ritesh123');

SELECT * FROM layout_sites;

--For grouping the areas to check for limit of each.
SELECT area, COUNT(*) as count FROM layout_sites GROUP BY area ORDER BY area DESC;


