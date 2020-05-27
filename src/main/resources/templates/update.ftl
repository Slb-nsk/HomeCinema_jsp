<!DOCTYPE html>

<html>

<body>

<h3>Исправление данных о фильме/сериале</h3>


<div>
<form action="/put/${movie.movieId}" method="post">

  <p><b>Название на русском:</b>
  <input type="text" name="movieRussianName" value="${movie.movieRussianName!}">
  </p>

  <p><b>Название на языке оригинала:</b>
  <input type="text" name="movieOriginalName" value="${movie.movieOriginalName!}">
  </p>

  <p><b>Год выхода на экраны:</b>
  <input type="text" name="movieYear" value="#{movie.movieYear!}">
  </p>

  <p><b>Количество серий:</b>
  <input type="text" name="seriesAmount" value="#{movie.seriesAmount!}">
  </p>

  <p><b>Страна:</b>
  <input type="text" name="movieCountries" value="${movie.countries!}">
  </p>

  <p><b>Жанр:</b>
  <input type="text" name="movieGenres" value="${movie.genres!}">
  </p>

  <p><b>Описание:</b>
  <textarea rows="10" cols="45" name="description">${movie.description!}</textarea>
  </p>

  <p><b>URL картинки:</b>
  <input type="text" name="imageUrl" value="${movie.imageUrl!}">
  </p>

  <p>
  <input type="submit" value="Внести">
  </p>

</form>
</div>

 <p><a href="/changeurl/${movie.movieId}">Обновление собственно ссылок на видео</a>
  </p>

<p><a href="/">На главную</a></p>

</body>

</html>