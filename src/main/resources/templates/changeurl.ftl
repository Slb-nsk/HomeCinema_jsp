<!DOCTYPE html>

<html>

<body>

<h3>"${movie.movieRussianName!}": исправление ссылок на видео</h3>


<div>
<form action="/changeurl/${movie.movieId}" method="post">

   <ul>
    <#list movie.sourceUrl as seria>
     <li><b>Серия ${seria?counter}<b>:
     <input type="text" name="seria${seria?counter}" value="${seria!}">
    </#list>
   </ul>

   <p>
  <input type="submit" value="Внести">
  </p>

</form>
</div>

</body>

</html>