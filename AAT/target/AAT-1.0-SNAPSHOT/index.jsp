<html>
    <head>
        <title>Automated Attendance Tracker</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#signup').submit(function() {
                    var formData = $(this).serialize();

                    $.ajax({
                        type: 'POST',
                        url:'/api/users',
                        data: formData,
                        success: function(data, textStatus, request){
                            alert(request.getResponseHeader('Authorization'));
                        },
                        error: function (request, textStatus, errorThrown) {
                            //alert(request.getResponseHeader('some_header'));
                        }
                    });

                    return false;
                });
            });
        </script>
    </head>
    <body>
        <p>Hello there!</p>
        <!--
        <form id="signup">
            New User<br>
            Email: <input type="email" name="email"><br>
            Password: <input type="password" name="password"><br>
            Secret Tutor Password: <input type="password" name="tutorPassword"><br>
            <input type="submit" value="Sign up">
        </form>
        -->
    </body>
</html>