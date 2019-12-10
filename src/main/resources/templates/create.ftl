<!DOCTYPE html>

<html>

<body>

<h3>Внесение в базу нового фильма/сериала</h3>


<div>
<form action="add" method="post">

  <p><b>Название на русском:</b>
  <input type="text" name="movieRussianName">
  </p>

  <p><b>Название на языке оригинала:</b>
  <input type="text" name="movieOriginalName">
  </p>

  <p><b>Год выхода на экраны:</b>
  <input type="number" name="movieYear">
  </p>

  <p><b>Количество серий:</b>
  <input type="number" name="seriesAmount">
  </p>

  <p><b>Страна:</b>
  <input type="text" name="movieCountries">
  </p>

  <p><b>Жанр:</b>
  <input type="text" name="movieGenres">
  </p>


  <p><b>Описание:</b>
  <textarea rows="10" cols="45" name="description"></textarea>
  </p>


  <p><b>URL картинки:</b>
  <input type="text" name="imageUrl">
  </p>

  <p>URL места в интернете, где находится фильм/сериал вносится путём
  редактирования данных об уже имеющемся в базе объекте.
  </p>

  <p>
  <input type="submit" value="Внести">
  </p>

</form>
</div>

<a routerLink="">На главную</a>

</body>

</html>