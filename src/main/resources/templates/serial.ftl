<!DOCTYPE html>

<html>
    <head>
    <title>Фильмотека</title>
    </head>
    <body>

    <#include "search.ftl">

    <hr>

<h3>${movie.movieRussianName!}</h3>

<table>
<tr>
<td>
<p><img src="${movie.imageUrl!}"></p>
<p>Название сериала на русском: <b>"${movie.movieRussianName!}"</b></p>
<p>Оригинальное название сериала: <b>("${movie.movieOriginalName!}")</b> </p>
<p>Год выхода на экраны: <b>${movie.movieYear!}</b> </p>
<p>Количество серий: <b>${movie.seriesAmount!}</b> </p>
<p>Страна производства: <b>${movie.countries!}</b> </p>
<p>Жанр: <b>${movie.genres!}</b> </p>
<p><b>Описание:</b> ${movie.description!}</p>
</td>

<td width=50%>
  <#list movie.sourceUrl as seria>
  <table style="width:`9%; float:left;" frame="border">
   <tr><td><center><a href="${seria!}">Серия ${seria?counter}</a></center></td></tr>
   </table>
  </#list>
</td>
</tr>
</table>

<p><a href="/">На главную</a></p>

<hr>

<a href="/update/${movie.movieId}">Обновить данные о "${movie.movieRussianName!}"</a>
<a href="/delete/${movie.movieId}">Удалить данные о "${movie.movieRussianName!}"</a>


    </body>
</html>
