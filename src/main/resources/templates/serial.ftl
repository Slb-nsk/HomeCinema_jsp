<#import "ui.ftl" as ui/>

<!DOCTYPE html>

<html>
    <head>
    <title>Фильмотека</title>
    </head>
    <body>

    <#include "search.ftl">

    <hr>

<h1 align=center>${movie.movieRussianName!}</h1>

<table>
<tr>
<td width=70%>

<#list movie.sourceUrl as seria>
<#if seria?counter == seriaSelected>
<#assign link = seria>
</#if>
</#list>
<#if link?has_content>
<@ui.videoPlayer url="${link!}"/>
</#if>


</td>

<td>
  <#list movie.sourceUrl as seria>
  <table style="width:`9%; float:left;" frame="border">
   <tr><td><center><a href="/${movie.movieId}/${seria?counter}">Серия #{seria?counter}</a></center></td></tr>
   </table>
  </#list>
</td>
</tr>
</table>


<#-- <p><img src="${movie.imageUrl!}"></p> -->
<p>Название сериала на русском: <b>"${movie.movieRussianName!}"</b></p>
<p>Оригинальное название сериала: <b>("${movie.movieOriginalName!}")</b> </p>
<p>Год выхода на экраны: <b>#{movie.movieYear!}</b> </p>
<p>Количество серий: <b>#{movie.seriesAmount!}</b> </p>
<p>Страна производства: <b>${movie.countries!}</b> </p>
<p>Жанр: <b>${movie.genres!}</b> </p>
<p><b>Описание:</b> ${movie.description!}</p>


<hr>

<a href="/update/${movie.movieId}">Обновить данные о "${movie.movieRussianName!}"</a>
<a href="/delete/${movie.movieId}">Удалить данные о "${movie.movieRussianName!}"</a>


    </body>
</html>
