<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Hello</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/array/one.html",
                    "type": "post",
                    "data": {
                        "array": [1,2,3]
                    /*
                    Request payload
                        array[]: 1
                        array[]: 2
                        array[]: 3

                        array%5B%5D=1&array%5B%5D=2&array%5B%5D=3
                     */
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });
            $("#btn3").click(function () {
                $.ajax({
                    "url": "send/array/two.html",
                    "type": "post",
                    "data": {
                        "array[0]": 1,
                        "array[1]" : 2,
                        "array[2]" : 3
                        /*
                        Request payload
                      array[0]: 1
                      array[1]: 2
                      array[2]: 3
                      array%5B0%5D=1&array%5B1%5D=2&array%5B2%5D=3
                         */
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn4").click(function () {
                var array = [5, 8, 12];
                var requestBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array/three.html",
                    "type": "post",
                    "contentType" : "application/json;charset=UTF-8",
                    "data": requestBody,
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn5").click(function () {
               var student = {
                   "stuId" : 1,
                   "stuName" : "Joe",
                   "role" : {
                       "id" : 10,
                       "name" : "dev"
                   },
                   "roles": [
                       {
                           "id":1,
                           "name" : "boss"
                       },
                       {
                           "id":2,
                           "name" : "boss2"
                       }
                   ],
                   "map" : {
                       "k1": {
                                   "id":1,
                                   "name" : "boss"
                               },
                       "k2": {
                           "id":2,
                           "name" : "boss2"
                       }
                   }

               }

               var requestBody = JSON.stringify(student);

                $.ajax({
                    "url": "send/compose/object.html",
                    "type": "post",
                    "contentType" : "application/json;charset=UTF-8",
                    "data": requestBody,
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn2").click(function() {
                layer.msg("Hello")
            });
        });
    </script>
</head>

<body>
    <a href="test/ssm.html">testing ssm</a>

    <br/>

    <button id="btn1">Send [1,2,3] One</button>

    <br/>

    <button id="btn3">Send [1,2,3] Two</button>

    <br/>

    <button id="btn4">Send [1,2,3] Three</button>

    <br/>

    <button id="btn5">Send Compose Object</button>

    <br/>

    <button id="btn2">Click me</button>
</body>
</html>