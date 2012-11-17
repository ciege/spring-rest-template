
SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;


INSERT INTO templateprivilege VALUES (1, NULL, false, NULL, NULL, 'PRV_0001');
INSERT INTO templateprivilege VALUES (2, NULL, false, NULL, NULL, 'PRV_0002');


INSERT INTO templaterole VALUES (1, NULL, false, NULL, NULL, 'ROLE_ADMIN');
INSERT INTO templaterole VALUES (2, NULL, false, NULL, NULL, 'ROLE_USER');



INSERT INTO templaterole_templateprivilege VALUES (1, 1);
INSERT INTO templaterole_templateprivilege VALUES (1, 2);



INSERT INTO templateuser(
            oid, creationdate, deleted, deletiondate, modificationdate, accountnonexpired, 
            accountnonlocked, credentialsnonexpired, enabled, facebookid, 
            firstname, lastname, password, username)
    VALUES (1, NULL, false, NULL, '2012-10-27 13:39:45.698', true, 
            true, true, true, NULL, 
            NULL, NULL, '7lLEodyoRSvB9W6Rhjc+xfabU0ITmcdbjaW4MfARG5TOb/N7TeMxDB85j/HSm8t1h6pTrATIXySR+yQ5jMo39Q==', 'admin');


INSERT INTO templateuser_templaterole VALUES (1, 1);
INSERT INTO templateuser_templaterole VALUES (1, 2);
