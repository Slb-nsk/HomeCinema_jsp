CREATE TABLE movies(
  movieId SERIAL PRIMARY KEY,
  movieRussianName TEXT NOT NULL,
  movieOriginalName TEXT,
  movieYear INT,
  seriesAmount INT,
  description TEXT,
  imageUrl TEXT,
  UNIQUE (movieRussianName)
);


INSERT INTO movies(movieRussianName, movieOriginalName, seriesAmount) VALUES ('������ XX ����', '������ XX ����', '1');
INSERT INTO movies(movieRussianName, movieOriginalName, seriesAmount) VALUES ('�����', '??', '30');


CREATE TABLE series(
  seriesId SERIAL,
  movieId INT NOT NULL REFERENCES movies(movieId),
  seriaNumber INT,
  fileUrl TEXT,
  PRIMARY KEY (movieId, seriaNumber)
);


CREATE TABLE countries(
  countryId SERIAL PRIMARY KEY,
  country TEXT UNIQUE
);

INSERT INTO countries(country) VALUES ('����');
INSERT INTO countries(country) VALUES ('�����');


CREATE TABLE genres(
  genreId SERIAL PRIMARY KEY,
  genre TEXT UNIQUE
);

INSERT INTO genres(genre) VALUES ('������');
INSERT INTO genres(genre) VALUES ('�������');



CREATE TABLE moviegenres(
  movieId INT NOT NULL REFERENCES movies(movieId),
  genreId INT NOT NULL REFERENCES genres(genreId),
  PRIMARY KEY (movieId, genreId)
);



CREATE TABLE moviecountries(
  movieId INT NOT NULL REFERENCES movies(movieId),
  countryId INT NOT NULL REFERENCES countries(countryId),
  PRIMARY KEY (movieId, countryId)
);

