;
(function() {

  // common
  function dblClickHandler(selector, fieldname) {
    $('#contactlist').delegate(selector, 'dblclick', function() {
      var field = $(this);
      if (field.html().indexOf('<input') === -1) {
        var txt = field.html();
        field.html('');
        $('<input type="text" class="input-small j-input-small" value="' + txt + '" />').appendTo(field).keydown(function(e) {
          if (e.keyCode === 13) {
            var contactId = $(this).parent().parent().find('input[name="contactId"]').val();
            contactId = contactId.substring(contactId.lastIndexOf('/') + 1);
            var value = $(this).val();

            $.get('contacts/update?contactId=' + contactId + "&field=" + fieldname + '&value=' + value, function() {});
            field.html(field.find('input').val());
          }
        });

      } else {
        // do nothing
      }
    });
  }

  dblClickHandler('.j-familyName', 'familyname');
  dblClickHandler('.j-givenName', 'givenname');

  $('#j-query-btn').click(function() {
    $.get('contacts/query?query=' + $('#j-query-text').val(), function(result) {
      $('#contactlist').html(result);
    });
  });

})();