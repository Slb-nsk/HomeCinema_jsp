<!DOCTYPE html>

<html>
    <head>
    <title>Фильмотека</title>
    </head>
    <body>

    <#include "search.ftl">

    <hr>

<h3>${movie.movieRussianName!}</h3>

<p><img src="${movie.imageUrl!}"></p>
<p>Название фильма на русском: <b>"${movie.movieRussianName!}"</b></p>
<p>Оригинальное название фильма: <b>("${movie.movieOriginalName!}")</b> </p>
<p>Год выхода на экраны: <b>${movie.movieYear!}</b> </p>
<p>Страна производства: <b>${movie.countries!}</b> </p>
<p>Жанр: <b>${movie.genres!}</b> </p>
<p><b>Описание:</b> ${movie.description!}</p>

<#list movie.sourceUrl as seria>
<p>Ссылка: <a href="${seria!}">Фильм</a> </p>
</#list>

<a href="/">На главную</a>

<hr>

<a href="/update/${movie.movieId}">Обновить данные о "${movie.movieRussianName!}"</a>
<a href="/delete/${movie.movieId}">Удалить данные о "${movie.movieRussianName!}"</a>



    </body>
</html>
