/* Fill Legislator table */
INSERT INTO opengov.Legislator (last, first)
   SELECT DISTINCT leginfo.last_name, leginfo.first_name
   FROM capublic.legislator_tbl leginfo;

SHOW WARNINGS;

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

/* Fill in Version table */
UPDATE capublic.bill_version_tbl SET appropriation = FALSE WHERE appropriation = 'No';
UPDATE capublic.bill_version_tbl SET appropriation = TRUE WHERE appropriation = 'Yes';

INSERT INTO opengov.BillVersion (vid, bid, date, state, subject, appropriation,
   substantive_changes)
   SELECT leginfo.bill_version_id,
          leginfo.bill_id,
          leginfo.bill_version_action_date,
          leginfo.bill_version_action,
          leginfo.subject,
          leginfo.appropriation,
          leginfo.substantive_changes
   FROM capublic.bill_version_tbl leginfo;

SHOW WARNINGS;

/* Fill in Action table */
INSERT INTO opengov.Action (bid, date, text)
   SELECT leginfo.bill_id,
          leginfo.action_date,
          leginfo.action
   FROM capublic.bill_history_tbl leginfo;

SHOW WARNINGS;

/* Fill in Motion table */
INSERT INTO opengov.Motion (mid, bid, date, text)
   SELECT DISTINCT a.motion_id,
                   b.bill_id,
                   DATE(b.vote_date_time),
                   a.motion_text
   FROM capublic.bill_motion_tbl a INNER JOIN capublic.bill_detail_vote_tbl b
   ON a.motion_id = b.motion_id;

SHOW WARNINGS;

/* Fill in votesOn table */
/*
UPDATE capublic.bill_detail_vote_tbl SET vote_code = 1 WHERE vote_code = 'AYE';
UPDATE capublic.bill_detail_vote_tbl SET vote_code = 2 WHERE vote_code = 'NO' OR vote_code = 'NOE';
UPDATE capublic.bill_detail_vote_tbl SET vote_code = 3 WHERE vote_code = 'ABS';

INSERT INTO opengov.votesOn (pid, mid, vote)
   SELECT leginfo.
*/
