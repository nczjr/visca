<html>

<head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visca</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<body>
<form method="get">
    <table>
        <tr>
            <td>
                Speed:
            <input type="text" id="command" value="8"/>
            </td>
            <td>
                Camera number:
                <input type="text" id="camno" value="1"/>
            </td>
        </tr>
        <tr>
            <td/><td><input class="button buttonUp" type="button" value="up" id="up"> </td>
        </tr>
        <tr>
            <td><input class="button" type="button" value="left" id="left"> </td>
            <td><input class="button" type="button" value="home" id="home"> </td>
            <td/><td><input class="button" type="button" value="right" id="right"> </td>
        </tr>
        <tr>
            <td/><td><input class="button" type="button" value="down" id="down"> </td><td/>
        </tr>
    </table>
    <br/>
    <table>
        <tr>
            <td><input class="button" type="button" value="zoomWide" id="zoomWide"> </td>
            <td><input class="button" type="button" value="zoomTele" id="zoomTele"> </td>
        </tr>
    </table>
    <div id="data"></div>
</form>
</body>
<style type="text/css">
    .button {
        background-color: #4CAF50;
        border: none;
        color: white;
        padding: 10px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        border-radius: 4px;

    }
    .buttonUp {
        width: 60px;
    }
</style>
<script type="text/javascript">
    $(document).ready(function(){
        var val = "";

        $("#up").click(function(event){
            event.preventDefault();
            console.log($("#command")[0].value);
            var url = "http://localhost:8080/visca/up?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#down").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/down?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#right").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/right?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#left").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/left?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#home").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/home?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#zoomWide").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/zoomWide?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        $("#zoomTele").click(function(event){
            event.preventDefault();
            console.log($("#command"));
            var url =  "http://localhost:8080/visca/zoomTele?speed=" + $("#command")[0].value + "&camno=" + $("#camno")[0].value;
            handleClick(url);
        });

        function handleClick(url) {
            event.preventDefault();
            console.log($("#command"))
            $.ajax({
                type: "GET",
                dataType:"text",
                url: url,
                success: function(data) {
                    console.log("response:" + data);
                    var currentData = $("#data").html();
                    currentData = currentData + "<br/>" + data;
                    $("#data").html(currentData);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(errorThrown);
                    console.log(' Error in processing! '+textStatus);
                }
            });
        }

    });
</script>
</head>


</html>