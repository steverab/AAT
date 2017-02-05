<%@ include file="header.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('#form-signin').submit(function(e) {
            e.preventDefault();
            var formData = $(this).serialize();
            console.log(formData);

            $.post('/api/authenticate', formData).done(function(data) {
                Cookies.set('authenticate', JSON.stringify(data));
                window.location = '/home.jsp';
            }).fail(function(xhr) {
                console.log("fail", xhr);
                alert("Login failed");
            });
        });
    });
</script>

<form id="form-signin">
    <h2 class="form-signin-heading">Please sign in</h2>
    <label for="inputEmail" class="sr-only">Email address</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>

<%@ include file="footer.jsp" %>