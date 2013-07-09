/* Fill in Motion table */
INSERT INTO opengov.Motion (mid, bid, date, text)
   SELECT DISTINCT a.motion_id,
                   b.bill_id,
                   DATE(b.vote_date_time),
                   a.motion_text
   FROM capublic.bill_motion_tbl a INNER JOIN capublic.bill_detail_vote_tbl b
   ON a.motion_id = b.motion_id;

SHOW WARNINGS;


