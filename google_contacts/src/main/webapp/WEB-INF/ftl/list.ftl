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
</head>
<body>
  <div class="navbar navbar-static-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="brand" href="">Google Contacts</a>
      </div>
    </div>
  </div>

  <div class="container">

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

    <!-- contacts table start -->
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th width="1px"><input type="checkbox"></th>
          <th width="1px"></th>
          <th width="10px">F.Name</th>
          <th width="10px">G.Name</th>
          <th width="10px">Email</th>
          <th width="10px">Phone</th>
        </tr>
      </thead>
      <tbody>
        <#list contactList as contact>
          <tr>
            <td><input type="checkbox"></td>
            <td><label class="j-modify icon-edit"/></td>
            <td class="j-familyName">${(contact.name.familyName.value)!}</td>
            <td class="j-givenName">${(contact.name.givenName.value)!}</td>
            <td>
              <#if contact.emailAddresses?exists>
                <#list contact.emailAddresses as email>
                  <#if email.rel?exists>
                    <span class="label">${email.rel?substring(33)}</span>
                  </#if>
                  ${(email.address)!},
                </#list>
              </#if>
            </td>
            <td>
              <#if contact.phoneNumbers?exists>
                <#list contact.phoneNumbers as phone >
                  <#if phone.rel?exists>
                    <span class="label">${phone.rel?substring(33)}</span>
                  </#if>
                  ${(phone.phoneNumber)!},
                </#list>
              </#if>
            </td>
          </tr>
        </#list>
      </tbody>
    </table>
    <!-- contacts table end -->
  </div>
  <script src="js/jquery.js"></script>
  <script src="js/list.js"></script>
</body>
</html>