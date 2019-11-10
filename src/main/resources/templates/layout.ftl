       <#if searchResult?size gt 0>
            <div>

                    <#list searchResult as item>
                     <table style="width:25%; float:left;" frame="border">
                       <tr><td align="center"><img src="${item.imageUrl!}"></td></tr>
                       <tr><td align="center"><b>"${item.movieRussianName!}"</b> </td></tr>
                       <tr><td align="center"><b>("${item.movieOriginalName!}")</b> </td></tr>
                       <tr><td><b>Год выхода на экран:</b> ${item.movieYear!} </td></tr>
                       <tr><td><a href="/${item.movieId}">Подробнее</a></td></tr></tr>
                     </table>
                    </#list>

            </div>
        </#if>