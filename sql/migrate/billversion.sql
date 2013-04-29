/* Fill in Version table */
UPDATE capublic.bill_version_tbl SET appropriation = FALSE WHERE appropriation = 'No';
UPDATE capublic.bill_version_tbl SET appropriation = TRUE WHERE appropriation = 'Yes';

INSERT INTO opengov.BillVersion (vid, bid, date, state, subject, appropriation,
   substantive_changes, text)
   SELECT leginfo.bill_version_id,
          leginfo.bill_id,
          leginfo.bill_version_action_date,
          leginfo.bill_version_action,
          leginfo.subject,
          leginfo.appropriation,
          leginfo.substantive_changes,
          leginfo.bill_xml
   FROM capublic.bill_version_tbl leginfo;

SHOW WARNINGS;


