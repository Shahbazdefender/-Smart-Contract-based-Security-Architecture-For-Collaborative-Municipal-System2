<?php
	if (@$_POST['getnewaddress'])
		no_displayed_error_result($getnewaddress, multichain('getnewaddress'));
?>

			<div class="row">

				<div class="col-sm-6">
					
					<h3><strong>Smart Meter Configration</strong></h3>
<?php
	$getinfo=multichain_getinfo();

	if (is_array($getinfo)) {
?>
<style>
tr, td {
  border-style:solid;
  border-color: #96D4D4;
}
</style>
<style>
p {
  color: Blue;
  text-indent: 18px;
  
}
</style>

					<table class="table table-bordered table-striped">
                    <th>Architecture Diagram</th>
                    <th>Detail Information</th>
  
						<tr>
                         <td >   
						<img src="Shahbaz New -Framework 3.jpg" alt="Norway" width="550" height="400">
                        </td>
                        <td > 
                        <div class="gfg">
        <img src="usecase1.jpg"width="550" height="20">
 <strong>       Utilities may better manage their cash flow and reduce the risk of bad debt by accepting payments in advance from their consumers via a prepaid electricity meter. Prepayment meters, on the other hand, allow utilities to schedule generation depending on how many units they have sold in advance. Prepaid energy meters also make it easier for customers to keep track of their energy expenses and prevent getting into debt. The prepaid energy meter reduces the financial burden on utilities while enabling users to better manage their own budgets.
<br>
        Using a scratch card, internet, SMS, or IVR (Interactive Voice Response), customers may top up their smart prepayment meter and get an email confirmation of the transaction. At least four tariff slabs may be programmed into the prepaid electric meter, which can be used in the present NEPRA tariff and updated using Smart Contracts. Smart prepayment meters let customers know how much money they have left and when their credit is about to run out
 </strong>
          </div>

                        </td>

                    </tr>
                        <tr>
						
</tr>

					</table>
<?php
	}
?>

</head>
<body>

