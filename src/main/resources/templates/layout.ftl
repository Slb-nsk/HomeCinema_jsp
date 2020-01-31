       <#if searchResult?size gt 0>
            <div style="float: left">

                    <#list searchResult as item>
                     <table style="width:20%; float:left;" frame="border">
                       <tr><td align="center"><img src="${item.imageUrl!}"></td></tr>
                       <#if !(item.movieRussianName == "")>
                       <tr><td align="center"><b>"${item.movieRussianName!}"</b> </td></tr>
                       </#if>
                       <tr><td align="center"><b>("${item.movieOriginalName!}")</b> </td></tr>
                       <tr><td><b>Год выхода на экран:</b> #{item.movieYear!} </td></tr>
                       <tr><td><a href="/${item.movieId}">Подробнее</a></td></tr></tr>
                     </table>
                     <#if (item?counter +5)%5 == 0>
                     </div>
                     <div style="float: left">
                     </#if>
                    </#list>

            </div>
        </#if>