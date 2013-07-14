<!-- contacts table start -->
<table class="table table-striped table-hover">
  <thead>
    <tr>
      <th width="0px"><input type="checkbox"></th>
      <th width="0px">F.Name</th>
      <th width="0px">G.Name</th>
      <th width="0px">Email</th>
      <th width="20px">Phone</th>
    </tr>
  </thead>
  <tbody>
    <#list contactlist as contact>
      <tr>
        <td><input type="checkbox"></td>
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