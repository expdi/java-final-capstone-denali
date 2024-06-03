
--CREATE database musicApp;

CREATE TABLE artist(
                       artist_id INT GENERATED ALWAYS AS IDENTITY,
                       name VARCHAR(100) NOT NULL,
                       role VARCHAR(25),
                       height decimal(5,2),
                       music_genre VARCHAR(15),
                       country VARCHAR(40),
                       birthday date,
                       PRIMARY KEY(artist_id)
);

CREATE TABLE track(
                      track_id INT GENERATED ALWAYS AS IDENTITY,
                      track_Identifier int NOT NULL,
                      title VARCHAR(80) NOT NULL,
                      album VARCHAR(50),
                      genre VARCHAR(15),
                      duration DECIMAL(5,2),
                      last_price DECIMAL(6,2),
                      media_type VARCHAR(5),
                      issue_date date,
                      PRIMARY KEY(track_id)
);

CREATE TABLE rel_track_artist (
                                  rel_track_artist_id INT GENERATED ALWAYS AS IDENTITY,
                                  artist_id INT,
                                  track_id INT,
                                  CONSTRAINT rel_track_artist_pkey PRIMARY KEY (artist_id, track_id)
                                  FOREIGN KEY (artist_id) REFERENCES artist(artist_id),
                                  FOREIGN KEY (track_id) REFERENCES track(track_id)
);

-- ALTER ROLE larku WITH superuser;
grant all privileges on database musicApp to larku;

INSERT INTO artist(name,role,height,music_genre,country,birthday) VALUES('Coldplay',null,null,'Pop','United Kingdom',null);
INSERT INTO artist(name,role,height,music_genre,country,birthday) VALUES('Red Hot Chili Peppers',null,null,'Rock','United States',null);
INSERT INTO artist(name,role,height,music_genre,country,birthday) VALUES('Rihanna','Singer',1.60,'Pop','Barbados','1983/10/2');
INSERT INTO artist(name,role,height,music_genre,country,birthday) VALUES('Michael Jackson','Singer',1.80,'Pop','United States',null);
INSERT INTO artist(name,role,height,music_genre,country,birthday) VALUES('Chad Smith','Drummer',1.89,'Rock','United States','1961/10/25');

INSERT INTO track(track_identifier,title,album,genre,duration,last_price,media_type,issue_date) VALUES(1,'Viva La Vida','Viva la Vida or Death and All His Friends','Pop',2.40,null,'MP3','2008/04/20');
INSERT INTO track(track_identifier,title,album,genre,duration,last_price,media_type,issue_date) VALUES(2,'Strawberry Swing','Viva la Vida or Death and All His Friends','Pop',1.56,null,'MP3','2008/04/10');
INSERT INTO track(track_identifier,title,album,genre,duration,last_price,media_type,issue_date) VALUES(3,'21st Century','Stadium Arcadium','Rock',1.45,null,'Ogg','2008/04/10');
INSERT INTO track(track_identifier,title,album,genre,duration,last_price,media_type,issue_date) VALUES(4,'All of the Lights','My Beautiful Dark Twisted Fantasy','Pop',2.25,null,'Ogg','2011/05/16');
INSERT INTO track(track_identifier,title,album,genre,duration,last_price,media_type,issue_date) VALUES(5,'Beat It','Thriller','Pop',2.55,null,'WAV','1995/11/24');

INSERT INTO rel_track_artist(artist_id, track_id ) values (1,1);
INSERT INTO rel_track_artist(artist_id, track_id ) values (1,2);
INSERT INTO rel_track_artist(artist_id, track_id ) values (2,3);
INSERT INTO rel_track_artist(artist_id, track_id ) values (3,4);
INSERT INTO rel_track_artist(artist_id, track_id ) values (4,5);
INSERT INTO rel_track_artist(artist_id, track_id ) values (5,3);