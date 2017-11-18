<html>
<title>Fraud Detection Engine</title>
<body>
<form action="/FinalProject1/submitDetails.html" method="post">
<h2><center><font color="blue">ENTER TRANSACTION DETAILS</font></center></h2>
<center>
<table>
<tr><td>Credit Card Number:</td><td></td><td><input type="text" name="ccno" pattern="[0-9]+-[0-9]+-[0-9]+-[0-9]+"></td></tr>

<tr><td>Transaction Amount:</td><td></td><td><input type="number" name="trxamt"></td></tr>

<tr><td>Location Of Current Transaction:</td><td></td><td><input type="text" name="cloc" required></td></tr>
<tr><td>Date Of Current Transaction:</td><td></td><td><input type="text" name="cdate"></td></tr>
<tr><td>Time Of Current Transaction:</td><td></td><td><input type="text" name="ctime"></td></tr>

</table>
</center>

<br/><br/><br/><br/>
<center><input type="submit" value="Submit the Test Data"  style="height:50px; width:200px"></center>
</form>

</body>
</html>
