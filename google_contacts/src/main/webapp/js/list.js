;
(function() {
    var td = $('.j-familyName');

    td.dblclick(function() {
        
        var field=$(this);
        if (field.html().indexOf('<input') === -1) {
            field.html('<input type="text" class="input-small" value="' + field.html() + '" />');
        } else {
            /*
             * $.get('xxxx',function(result){ ... });
             * 
             * 
             */
            field.html(field.find('input').val());
        }
    });

})();

(function() {
    var btn = $('#j-query-btn');
    btn.click(function() {
        
        $.ajax({
            url: 'query',
            data: {
                query: $('#j-query-text').val()   
            },
            success: function(result) { 
                $('#contactlist').html(result);
            }
        });
        
    });

})();