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


INSERT INTO movies(movieRussianName, movieOriginalName, seriesAmount) VALUES ('Пираты XX века', 'Пираты XX века', '1');
INSERT INTO movies(movieRussianName, movieOriginalName, seriesAmount) VALUES ('Герои', '??', '30');


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

INSERT INTO countries(country) VALUES ('СССР');
INSERT INTO countries(country) VALUES ('Китай');


CREATE TABLE genres(
  genreId SERIAL PRIMARY KEY,
  genre TEXT UNIQUE
);

INSERT INTO genres(genre) VALUES ('Боевик');
INSERT INTO genres(genre) VALUES ('История');



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

