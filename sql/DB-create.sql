USE opengov;

CREATE TABLE IF NOT EXISTS Legislator (
   pid   INTEGER AUTO_INCREMENT,
   last  VARCHAR(50) NOT NULL,
   first VARCHAR(50) NOT NULL,

   PRIMARY KEY (pid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Term (
   pid      INTEGER,
   year     YEAR,
   district INTEGER(3),
   house    ENUM('Assembly', 'Senate') NOT NULL,
   party    ENUM('Republican', 'Democrat', 'Other') NOT NULL,
   start    DATE,
   end      DATE,

   PRIMARY KEY (pid, year, district, house),
   FOREIGN KEY (pid) REFERENCES Legislator(pid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Committee (
   cid  INTEGER(3),
   name VARCHAR(200) NOT NULL,

   PRIMARY KEY (cid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS servesOn (
   pid      INTEGER,
   year     YEAR,
   district INTEGER(3),
   house    ENUM('Assembly', 'Senate') NOT NULL,
   cid      INTEGER(3),

   PRIMARY KEY (pid, year, district, house, cid),
   FOREIGN KEY (pid, year, district, house) REFERENCES Term(pid, year, district, house),
   FOREIGN KEY (cid) REFERENCES Committee(cid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Bill (
   bid     VARCHAR(20),
   type    VARCHAR(3) NOT NULL,
   number  INTEGER NOT NULL,
   state   ENUM('Chaptered', 'Introduced', 'Amended Assembly', 'Amended Senate', 'Enrolled',
      'Proposed', 'Amended', 'Vetoed') NOT NULL,
   status  VARCHAR(60),
   house   ENUM('Assembly', 'Senate', 'Secretary of State', 'Governor', 'Legislature'),
   session INTEGER(1),

   PRIMARY KEY (bid),
   INDEX name (type, number)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Hearing (
   date DATE,
   bid  VARCHAR(20),
   cid  INTEGER(3),

   PRIMARY KEY (date, bid, cid),
   FOREIGN KEY (bid) REFERENCES Bill(bid),
   FOREIGN KEY (cid) REFERENCES Committee(cid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Action (
   bid  VARCHAR(20),
   date DATE,
   text TEXT,
   
   FOREIGN KEY (bid) REFERENCES Bill(bid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS Motion (
   mid  INTEGER(20),
   bid  VARCHAR(20),
   date DATE,
   text TEXT,

   PRIMARY KEY (mid, bid, date),
   FOREIGN KEY (bid) REFERENCES Bill(bid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS votesOn (
   pid  INTEGER(3),
   mid  INTEGER(20),
   vote ENUM('Yea', 'Nay', 'Abstain') NOT NULL,

   PRIMARY KEY (pid, mid),
   FOREIGN KEY (pid) REFERENCES Legislator(pid),
   FOREIGN KEY (mid) REFERENCES Motion(mid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS BillVersion (
   vid                 VARCHAR(30),
   bid                 VARCHAR(20),
   date                DATE,
   state               ENUM('Chaptered', 'Introduced', 'Amended Assembly', 'Amended Senate',
                            'Enrolled', 'Proposed', 'Amended', 'Vetoed') NOT NULL,
   subject             TEXT,
   appropriation       BOOLEAN,
   substantive_changes BOOLEAN,
   title               TEXT,
   digest              MEDIUMTEXT,
   text                MEDIUMTEXT,

   PRIMARY KEY (vid),
   FOREIGN KEY (bid) REFERENCES Bill(bid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS authors (
   pid          INTEGER(3),
   bid          VARCHAR(20),
   vid          VARCHAR(30),
   contribution ENUM('Lead Author', 'Principal Coauthor', 'Coauthor') DEFAULT 'Coauthor',

   PRIMARY KEY (pid, bid, vid),
   FOREIGN KEY (pid) REFERENCES Legislator(pid),
   FOREIGN KEY (bid, vid) REFERENCES BillVersion(bid, vid)
)
ENGINE = INNODB
CHARACTER SET utf8 COLLATE utf8_general_ci;
