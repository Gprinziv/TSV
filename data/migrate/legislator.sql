/* Fill Legislator table */
INSERT INTO opengov.Legislator (last, first)
   SELECT distinct leginfo.last_name, leginfo.first_name
   FROM capublic.legislator_tbl leginfo;

SHOW WARNINGS;

