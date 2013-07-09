/* Fill Term table */
UPDATE capublic.legislator_tbl SET house_type = 1 WHERE house_type = 'A';
UPDATE capublic.legislator_tbl SET house_type = 2 WHERE house_type = 'S';

UPDATE capublic.legislator_tbl SET party = 1 WHERE party = 'REP';
UPDATE capublic.legislator_tbl SET party = 2 WHERE party = 'DEM';
UPDATE capublic.legislator_tbl SET party = 3 WHERE party = 'OTHE';

INSERT INTO opengov.Term (year, district, house, pid, party)
   SELECT DISTINCT SUBSTRING(leginfo.session_year, 1, 4),
                   CONVERT(SUBSTRING(leginfo.district, -2), UNSIGNED),
                   leginfo.house_type,
                   self.pid,
                   leginfo.party
   FROM capublic.legislator_tbl leginfo INNER JOIN opengov.Legislator self
   ON self.last = leginfo.last_name AND self.first = leginfo.first_name;

SHOW WARNINGS;


