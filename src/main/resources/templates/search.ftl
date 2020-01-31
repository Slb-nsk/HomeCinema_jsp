<form action="/search">
 <table>
 <tr>
 <th align="left">Тип:</th>
   <td> <input name="kind" type="radio" value="Any" checked>Любой</td>
   <td> <input name="kind" type="radio" value="Mov">Фильм</td>
   <td> <input name="kind" type="radio" value="Ser">Сериал</td>
 </tr>
<tr>
 <th align="left">Страна:</th>
   <td> <input name="country" type="radio" value="Any" checked>Любая</td>
   <td> <input name="country" type="radio" value="СССР">СССР</td>
   <td> <input name="country" type="radio" value="Россия">Россия</td>
   <td> <input name="country" type="radio" value="КНР">КНР</td>
 </tr>
 <tr>
 <th align="left">Жанр:</th>
   <td> <input name="genre" type="radio" value="Any" checked>Любой</td>
   <td> <input name="genre" type="radio" value="История">История</td>
   <td> <input name="genre" type="radio" value="Военный">Военный</td>
   <td> <input name="genre" type="radio" value="Боевик">Боевик</td>
 </tr>
 </table>
<p><input type="submit" value="Выбрать"></p>
</form>

<p><a href="/add">Добавить новый фильм/сериал</a></p>

