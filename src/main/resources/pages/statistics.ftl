<!DOCTYPE html>
<html>
<head>
    <title>Statistics</title>
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
</head>
<body>
<h1>Statistics</h1>
<hr>
<table align="center" border="1">
    <tr>
        <th>Total requests</th>
        <th>Unique requests</th>
    </tr>
    <tr>
        <td>${countRequests}</td>
        <td>${countUniqueRequests}</td>
    </tr>
</table>

<p>IP requests counter :</p>

<table border='1'>
    <tbody>
    <tr>
        <th>IP</th>
        <th>Request count</th>
        <th>Last request</th>
    </tr>
    <#list requestPerIP?keys as key>
        <tr>
            <td>${key}</td>
            <td>${requestPerIP[key].count}</td>
            <td>${requestPerIP[key].timeLastRequest}</td>
        </tr>
    </#list>
    </tbody>
</table>
<hr>
<h4>URL redirection counter :</h4>
<table border='1'>
    <tr>
        <th>URL</th>
        <th>Redirection count</th>
    </tr>
    <#list redirectionPerURL?keys as key>
        <tr>
            <td>${key}</td>
            <td>${redirectionPerURL[key]}</td>
        </tr>
    </#list>
</table>
Open connections: ${openConnections}</br>
<hr>
<h4>Connections log :</h4>
<table border='1'>
    <tr>
        <th>SRC_IP</th>
        <th>URI</th>
        <th>timestamp</th>
        <th>sent bytes</th>
        <th>received bytes</th>
        <th>speed (bytes/sec)</th>
    </tr>
    <#list connectionLog as log>
        <tr>
            <td>${log.srcIp}</td>
            <td>${log.uri}</td>
            <td>${log.timestamp}</td>
            <td>${log.sentBytes}</td>
            <td>${log.receivedBytes}</td>
            <td>${log.speed}</td>
        </tr>
    </#list>

</table>
<hr>
</body>
</html>

