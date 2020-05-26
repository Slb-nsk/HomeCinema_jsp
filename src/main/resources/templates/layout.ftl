       <#if searchResult?size gt 0>


            <table width="100%">
            <tr>

                    <#list searchResult as item>
                    <td> <table style="width:20%; float:left;" frame="border">
                       <tr><td align="center"><img src="${item.imageUrl!}"></td></tr>
                       <#if !(item.movieRussianName == "")>
                       <tr><td align="center"><b>"${item.movieRussianName!}"</b> </td></tr>
                       </#if>
                       <tr><td align="center"><b>("${item.movieOriginalName!}")</b> </td></tr>
                       <tr><td><b>Год выхода на экран:</b> #{item.movieYear!} </td></tr>
                       <tr><td><a href="/${item.movieId}/1">Подробнее</a></td></tr></tr>
                     </table></td>
                     <#if (item?counter +5)%5 == 0>
                     </tr>
                     <tr>
                     </#if>
                    </#list>

             </tr>
             </table>


        </#if>