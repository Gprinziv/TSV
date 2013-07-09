LOAD DATA LOCAL
  INFILE "LOCATION_CODE_TBL.dat"
  REPLACE
  INTO TABLE capublic.location_code_tbl
  FIELDS TERMINATED BY '\t'
  OPTIONALLY ENCLOSED BY '`'
  LINES TERMINATED BY '\n'
(
   SESSION_YEAR
  ,LOCATION_CODE
  ,LOCATION_TYPE
  ,CONSENT_CALENDAR_CODE
  ,DESCRIPTION
  ,LONG_DESCRIPTION
  ,ACTIVE_FLG
  ,TRANS_UID
  ,TRANS_UPDATE
  ,INACTIVE_FILE_FLG
)
