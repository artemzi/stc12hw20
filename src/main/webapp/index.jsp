<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello, I am a Java web app!</title>
</head>
<style>
    @import url(http://fonts.googleapis.com/css?family=Open+Sans:400italic,400);

    body {
        font: normal normal 13px/16px "Open Sans", sans-serif;
        background: #ccc;
    }

    .container{
        padding: 20px;
        width: 300px;
        margin: 0 auto;
        margin-top: 40px;
        background: white;
        border-radius: 5px;}

    form {
        display: inline-block;
    }

    input{
        padding: 4px 15px 4px 5px;
    }

    #button{
        display: inline-block;
        background-color:#fc999b;
        color:#ffffff;
        border-radius: 5px;
        text-align:center;
        margin-top:2px;
        padding: 5px 15px;
    }

    #button:hover{
        cursor: pointer;
        opacity: .8;}

    ul {padding-left: 20px;}

    ul li {padding: 5px;color:#000;}

    ul li:nth-child(even){background: #dfdfdf;}

    .strike{text-decoration: line-through;}

    li:hover{
        cursor: pointer;
    }
</style>
<body>

<div class="container">
    <h2>Simple To Do List</h2>
    <p><em>Click and drag to reorder, double click to cross an item off.</em></p>

    <form name="toDoList">
        <input type="text" name="ListItem"/>
    </form>

    <div id="button">Add</div>
    <br/>
    <ul></ul>
</div>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="application/javascript">
    $(document).ready(
        function(){
            $(document).on("click", "#button", function() {
                var currentTask = $('input[name=ListItem]').val();
                $.ajax({
                    url: 'handler',
                    type: 'GET',
                    data: {
                        task: currentTask
                    },
                    success: function(data){
                        if (data === "success") {
                            $("ul").append($("<li>").text(currentTask));
                        }
                    },
                    error: function(data) {
                        console.log('cannot get response from server'); //or whatever
                    }
                });
            });

            $("input[name=ListItem]").keyup(function(event){
                if(event.keyCode == 13){
                    $("#button").click();
                }
            });

            $(document).on('dblclick','li', function(){
                var text = $(this).text();
                var $li = $(this);
                $.ajax({
                    url: 'handler',
                    type: 'POST',
                    data: {
                        task: text
                    },
                    success: function(data){
                        if (data === "success") {
                            console.debug('success'); //or whatever
                            $li.toggleClass('strike').fadeOut('slow');
                        }
                    },
                    error: function(data) {
                        console.log('cannot get response from server'); //or whatever
                    }
                });
            });

            $('input').focus(function() {
                $(this).val('');
            });

            $('ol').sortable();

        }
    );
</script>
</body>
</html>