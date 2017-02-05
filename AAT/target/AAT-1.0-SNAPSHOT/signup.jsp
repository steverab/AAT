<%@ include file="header.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('#form-signup').submit(function(e) {
            e.preventDefault();
            var formData = $(this).serialize();
            console.log(formData);

            $.post('/api/users', formData).done(function(data) {
                window.location = '/login.jsp';
            }).fail(function(xhr) {
                console.log("fail", xhr);
                alert("Signup failed");
            });
        });
    });
</script>

<form id="form-signup">
    <div class="col-sm-12">
        <div class="row">
            <div class="col-sm-6 form-group">
                <label>First Name</label>
                <input name="firstName" type="text" placeholder="Enter First Name Here.." class="form-control">
            </div>
            <div class="col-sm-6 form-group">
                <label>Last Name</label>
                <input name="lastName" type="text" placeholder="Enter Last Name Here.." class="form-control">
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 form-group">
                <label>Email</label>
                <input name="email" type="text" placeholder="Enter Email Here.." class="form-control">
            </div>
            <div class="col-sm-4 form-group">
                <label>Password</label>
                <input name="password" type="password" placeholder="Enter Password Here.." class="form-control">
            </div>
            <div class="col-sm-4 form-group">
                <label>Tutor Password (optional)</label>
                <input name="tutorPassword" type="password" placeholder="Enter Tutor Password Here.." class="form-control">
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Submit</button>
    </div>
</form>
<%@ include file="footer.jsp" %>