;
(function() {
  // Panel Class
   var Panel = function(config){
    config = config || {};
    this.wrap = $('<div class="m-panel"></div>');
    this.content = config.content || '';
    this.wrap.html(this.content);
  };
  Panel.prototype = {
    'show' : function(){
      this.wrap.appendTo($('body'));
    },
    'setContent' : function(content){
      this.content = content || '';
      this.wrap.html(content);
    },
    'hide' : function(){
      this.wrap.remove();
    }
  };


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

            $.get('update?contactId=' + contactId + "&field=" + fieldname + '&value=' + value, function() {});
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

  $('#contactlist').delegate('.j-editprofile', 'click', function() {
    var field = $(this);
    var contactId = $(this).parent().parent().find('input[name="contactId"]').val();
    contactId = contactId.substring(contactId.lastIndexOf('/') + 1);

    $.get('contacts/get?contactId=' + contactId, function(data) {alert(data);
      (new Panel({
        'content' : data
      })).show();
    });
  });

})();