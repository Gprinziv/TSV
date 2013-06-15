/* Fill in Action table */
INSERT INTO opengov.Action (bid, date, text)
   SELECT leginfo.bill_id,
          leginfo.action_date,
          leginfo.action
   FROM capublic.bill_history_tbl leginfo;

SHOW WARNINGS;


