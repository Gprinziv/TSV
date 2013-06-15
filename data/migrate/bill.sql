/* Fill in Bill table */
INSERT INTO opengov.Bill (bid, type, number, state, status, house, session)
   SELECT leginfo.bill_id,
          leginfo.measure_type,
          leginfo.measure_num,
          leginfo.measure_state,
          leginfo.current_status,
          leginfo.current_house,
          CONVERT(leginfo.session_num, UNSIGNED)
   FROM capublic.bill_tbl leginfo;

SHOW WARNINGS;


