$(document).ready(function () {
  var root = 'https://localhost:8443/api/user/profile/';
  var profile_fields = ['username','email','password','confirmPassword','currentPassword'];

  $('#cancel-btn').click(function (event) {
    event.preventDefault();
    reset_error_fields(profile_fields);
    fire__ajax__submit(root + 'reset', 'GET', {}, set_profile, handle_unexpected_error);
  });

  $('#save-btn').click(function (event) {
    event.preventDefault();
    reset_error_fields(profile_fields);
    var data = $('form').serialize();
    data = JSON.stringify(data);
    fire__ajax__submit(root + 'update', 'PUT', data, set_profile, set_profile_error);
    $('#info-alert').css("display","block");
  });
});

function fire__ajax__submit(link, method, sendData, onSuccess, onError) {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  $.ajax({
      url: link,
      type: method,
      data: sendData,
      dataType: "json",
      accepts: {
        json: "application/json, text/javascript"
      },
      beforeSend: function(request) {
        request.setRequestHeader(header, token);
      },
      success: function(json, status, xhr) {
        console.log("Success: ", json);
        onSuccess(json);
      },
      error: function(error, status, xhr) {
        console.log("Error: ", error);
        var json = JSON.parse(error.responseText);
        onError(json, xhr.status);
      }
  });
}

function set_profile(json) {
  $('#username').val(json.username);
  $('#email').val(json.email);
  $('#password').val("");
  $('#confirmPassword').val("");
  $('#currentPassword').val("");
  $('#info-alert').text("Your account details have been updated successfully.");
}

function set_profile_error(json, status) {
  fieldErrors = json.object.fieldErrors;
  if (fieldErrors) {
    for(key in fieldErrors) {
      var id = '#'+key+'-error';
      $(id).text(fieldErrors[key]);
      $(id).parent().css("display","block");
    }
  } else {
    handle_unexpected_error();
  }

}

function reset_error_fields(fields) {
  fields.forEach(f => {
    var id = '#'+f+'-error';
    $(id).text("");
    $(id).parent().css("display","none");
  });
  $('#info-alert').css("display","none");
}

function handle_unexpected_error() {
  window.location.replace("/error");
}