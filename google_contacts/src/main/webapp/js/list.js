;
(function() {
    var modBtn = $('.j-modify');

    modBtn.click(function() {
        var tr = $(this).parent().parent();
        
        var tdFamilyName = tr.find('.j-familyName');
        
        if (tdFamilyName.html().indexOf('<input') === -1) {
            $(this).removeClass("icon-edit").addClass("icon-ok");
            tdFamilyName.html('<input type="text" value="' + tdFamilyName.html() + '" />');
        } else {
            /*
             * $.get('xxxx',function(result){ ... });
             * 
             * 
             */
            $(this).removeClass("icon-user").addClass("icon-edit");
            tdFamilyName.html(tdFamilyName.find('input').val());
        }
    });

})();