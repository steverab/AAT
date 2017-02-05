<%@ include file="header.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
            },
            url: "/api/groups",
            success: function(msg) {
                processGroups(msg)
            }
        });
    });

    function processGroups(msg) {
        msg.forEach(function (entry) {
            $('#groupSignupSelect').append($('<option>', {
                value: entry.id,
                text: entry.id
            }));
        })
        msg.forEach(function (entry) {
            $('#groupsTable > tbody:last-child').append('<tr><td>' + entry.id + '</td></tr>');
        })
        if(parseInt(JSON.parse(Cookies.get('authenticate')).groupId) == -1) {
            getSessionsForGroupId(parseInt(msg[0].id));
        }
    }
</script>

<%
    if (userType.equals("student")) {
%>

<!-- BEGIN STUDENT PART -->

<script type="text/javascript">
    $(document).ready(function() {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
            },
            url: "/api/contributions/attendances",
            success: function(msg) {
                processAttendances(msg)
            }
        });

        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
            },
            url: "/api/contributions/presentations",
            success: function(msg) {
                processPresentations(msg)
            }
        });

        $('#signUpForGroup').on('click', function(event) {
            $.ajax({
                type: "POST",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
                },
                url: "/api/groups/" + $("#groupSignupSelect :selected").text() + "/signup",
                success: function(msg) {
                    var cookieJSON = JSON.parse(Cookies.get('authenticate'));
                    cookieJSON.groupId = parseInt($("#groupSignupSelect :selected").text());
                    Cookies.set('authenticate', JSON.stringify(cookieJSON));
                    location.reload();
                }
            });
        });
    });

    function processAttendances(msg) {
        msg.forEach(function (entry) {
            var date = new Date(entry.date);
            $('#attendancesTable > tbody:last-child').append('<tr><th scope="row">' + entry.id + '</th>' +
                '<td>' + entry.code + '</td> ' +
                '<td>' + date.toUTCString() + '</td> ' +
                '<td>' + entry.confirmed + '</td>' +
                '<td>' + entry.sessionId + '</td></tr>');
        })
    }

    function processPresentations(msg) {
        msg.forEach(function (entry) {
            var date = new Date(entry.date);
            $('#presentationsTable > tbody:last-child').append('<tr><th scope="row">' + entry.id + '</th>' +
                '<td>' + entry.code + '</td> ' +
                '<td>' + date.toUTCString() + '</td> ' +
                '<td>' + entry.confirmed + '</td>' +
                '<td>' + entry.sessionId + '</td></tr>');
        })
    }

    function getSessionsForGroupId(groupId) {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
            },
            url: "/api/sessions?groupId=" + groupId,
            success: function(msg) {
                processPreviewSessions(msg)
            }
        });
    }

    function processPreviewSessions(msg) {
        $("#previewSessionsTable").find("tr:gt(0)").remove();
        msg.forEach(function (entry) {
            console.log("test");
            var startDate = new Date(entry.startDate);
            var endDate = new Date(entry.endDate);
            $('#previewSessionsTable > tbody:last-child').append('<tr><th scope="row">' + entry.id + '</th>' +
                '<td>' + entry.room + '</td> ' +
                '<td>' + startDate.toUTCString() + '</td> ' +
                '<td>' + endDate.toUTCString() + '</td></tr>');
        })
    }
</script>

<%
    if (groupId == -1) {
%>

<script type="text/javascript">
    $(document).ready(function() {
        $('#groupSignupSelect').on('change', function() {
            getSessionsForGroupId(parseInt(this.value));
        });
    });
</script>

<p>Please sign up for one of the following groups:</p>

<select id="groupSignupSelect" class="custom-select">
</select>

<button type="button" id="signUpForGroup" class="btn btn-primary">Sign up</button>

<h3>Sessions</h3>

<table id="previewSessionsTable" class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Room</th>
        <th>Start date</th>
        <th>End date</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<%
    } else {
%>

<script type="text/javascript">
    $(document).ready(function() {
        getSessionsForGroupId(parseInt(JSON.parse(Cookies.get('authenticate')).groupId));
    });
</script>

<p>You are registered for group: <%= groupId %></p>

<h3>Sessions</h3>

<table id="previewSessionsTable" class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Room</th>
        <th>Start date</th>
        <th>End date</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<h3>Attendances</h3>
<table id="attendancesTable" class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Code</th>
        <th>Date</th>
        <th>Confirmed</th>
        <th>Session ID</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<h3>Presentations</h3>
<table id="presentationsTable" class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Code</th>
        <th>Date</th>
        <th>Confirmed</th>
        <th>Session ID</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<%
    }
%>

<!-- END STUDENT PART -->

<%
} else {
%>

<!-- BEGIN TUTOR PART -->

<script type="text/javascript">
    $(document).ready(function() {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
            },
            url: "/api/sessions",
            success: function(msg) {
                processSessions(msg)
            }
        });

        $('#form-newSession').submit(function(e) {
            e.preventDefault();
            var formData = $(this).find("select,input").serialize();
            $.ajax({
                type: "POST",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
                },
                url: "/api/sessions",
                data: formData,
                success: function(msg) {
                    location.reload();
                }
            });
        });

        $('#addGroupButton').on('click', function(event) {
            event.preventDefault(); // To prevent following the link (optional)
            $.ajax({
                type: "POST",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", "Bearer " + JSON.parse(Cookies.get('authenticate')).token);
                },
                url: "/api/groups",
                success: function(msg) {
                    $('#groupsTable > tbody:last-child').append('<tr><td>' + msg + '</td></tr>');
                    $('#groupSignupSelect').append($('<option>', {
                        value: msg,
                        text: msg
                    }));
                }
            });
        });
    });

    function processSessions(msg) {
        msg.forEach(function (entry) {
            var startDate = new Date(entry.startDate);
            var endDate = new Date(entry.endDate);
            $('#sessionsTable > tbody:last-child').append('<tr><th scope="row">' + entry.id + '</th>' +
                '<td>' + entry.room + '</td> ' +
                '<td>' + startDate.toUTCString() + '</td> ' +
                '<td>' + endDate.toUTCString() + '</td>' +
                '<td>' + entry.groupId + '</td></tr>');
        })
    }
</script>


<h3>Groups</h3>
<table id="groupsTable" class="table">
    <thead>
    <tr>
        <th>#</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<h4>Create new group</h4>
<button type="button" id="addGroupButton" class="btn btn-primary">Create new group</button>

<hr />

<h3>Sessions</h3>

<table id="sessionsTable" class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Room</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Group ID</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>


<h4>Create new session</h4>
<div class="col-sm-12">
    <form id="form-newSession">
        <div class="row">
            <div class="col-sm-3 form-group">
                <label>Group ID</label>
                <select id="groupSignupSelect" name="groupId" class="custom-select">
                </select>
            </div>
            <div class="col-sm-3 form-group">
                <label>Room</label>
                <input name="room" type="text" placeholder="Enter Room Number Here.." class="form-control">
            </div>
            <div class="col-sm-3 form-group">
                <label>Start date</label>
                <input name="startDate" type="text" placeholder="Enter Start Date Here.." class="form-control" value="2017-02-08T10:00:00">
            </div>
            <div class="col-sm-3 form-group">
                <label>End date</label>
                <input name="endDate" type="text" placeholder="Enter End Date Here.." class="form-control" value="2017-02-08T12:00:00">
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create new session</button>
    </form>
</div>

<!--
<h3>Students</h3>
<table class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Username</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">1</th>
        <td>Mark</td>
        <td>Otto</td>
        <td>@mdo</td>
    </tr>
    <tr>
        <th scope="row">2</th>
        <td>Jacob</td>
        <td>Thornton</td>
        <td>@fat</td>
    </tr>
    <tr>
        <th scope="row">3</th>
        <td>Larry</td>
        <td>the Bird</td>
        <td>@twitter</td>
    </tr>
    </tbody>
</table>
-->
<%
    }
%>
<%@ include file="footer.jsp" %>