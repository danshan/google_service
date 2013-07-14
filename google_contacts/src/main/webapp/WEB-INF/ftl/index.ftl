<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Google Contacts Editor Online</title>

  <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
  <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

  <!-- Le styles -->
  <link href="css/bootstrap.css" rel="stylesheet">
  <link href="css/bootstrap-responsive.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">
  <script src="js/jquery.js"></script>
</head>
<body>
  <div class="navbar navbar-static-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="brand" href="">Google Contacts</a>
      </div>
    </div>
  </div>

  <div class="container" style="padding-top: 20px;">

    <div class="input-append">
      <input class="span3" type="text" id='j-query-text' placeholder="Username">
      <button class="btn" id="j-query-btn">Search</button>
    </div>


    <div class="btn-toolbar" style="display:block">
      <div class="btn-group">
        <button class="btn"><i class="icon-user"></i></button>
        <button class="btn"><i class="icon-user"></i></button>
      </div>
      <div class="btn-group">
        <button class="btn"><i class="icon-refresh"></i></button>
        <div class="btn-group">
          <button class="btn dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i></button>
        </div>
      </div>
    </div>

    <div id="contactlist">
    <script type="text/javascript">
    $.get('list', function(result){ 
      $('#contactlist').html(result);
    });
    </script>
  </div>
  <script src="js/list.js"></script>
</body>
</html>