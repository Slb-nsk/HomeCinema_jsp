<#macro videoPlayer url>

<#if url[12..18] == "youtube">
  <#assign urlTail = url[32..]>
  <#assign youtubeVideoUrl = "https://www.youtube.com/embed/" + urlTail + "?enablejsapi=1">
  <iframe id="player" type="text/html" width="640" height="360" src="${youtubeVideoUrl}" frameborder="0"></iframe>
</#if>

<#if url[10..14] == "youku">
  <#assign urlTail = url[30..url?length-7]>
  <#assign youkuVideoUrl = "https://player.youku.com/embed/" + urlTail + "?rel=0">
  <embed src="${youkuVideoUrl}" allowFullScreen='true' quality='high' width='480' height='400' align='middle' allowScriptAccess='always'></embed>
</#if>

</#macro>