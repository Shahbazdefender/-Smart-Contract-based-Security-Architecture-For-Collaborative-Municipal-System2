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
					<table class="table table-bordered table-striped">
						<tr>
							<th>Name</th>
							<td><?php echo html($getinfo['chainname'])?></td>
						</tr>
						<tr>
							<th>Node address</th>
							<td><?php echo html($getinfo['nodeaddress'])?></td>
						</tr>
						<tr>
							<th>Blocks</th>
							<td><?php echo html($getinfo['blocks'])?></td>
						</tr>
						<tr>
						<img src="Smarthome1.jpg" alt="Norway" width="550" height="200">

<p class="text-white"><strong>In This Use-case of Smart Home  we Implement a Smart Meter DashBaord responsible to Note the Energy Consumption of Smart Home </strong> .</p>

</tr>
					</table>
<?php
	}
?>

	

<?php
	if (no_displayed_error_result($peerinfo, multichain('getpeerinfo'))) {
?>
					<table class="table table-bordered table-striped table-break-words">
<?php
		foreach ($peerinfo as $peer) {
?>
						<tr>
							<th>Node IP address</th>
							<td><?php echo html(strtok($peer['addr'], ':'))?></td>
						</tr>
						<tr>
							<th>Handshake address</th>
							<td class="td-break-words small"><?php echo html($peer['handshake'])?></td>
						</tr>
						<tr>
							<th>Latency</th>
							<td><?php echo html(number_format($peer['pingtime'], 3))?> sec</td>
						</tr>
<?php
		}
?>
					</table>
<?php
	}
?>
  

				</div>
				<div class="col-sm-6">
				<style>
h3{text-align: center;}
</style>

					<h3><strong>Connected Devies Addresses</strong></h3>

<?php
	if (no_displayed_error_result($getaddresses, multichain('getaddresses', true))) {
		$addressmine=array();

		foreach ($getaddresses as $getaddress)
			$addressmine[$getaddress['address']]=$getaddress['ismine'];

		$addresspermissions=array();

		if (no_displayed_error_result($listpermissions,
			multichain('listpermissions', 'all', implode(',', array_keys($addressmine)))
		))
			foreach ($listpermissions as $listpermission)
				$addresspermissions[$listpermission['address']][$listpermission['type']]=true;

		no_displayed_error_result($getmultibalances, multichain('getmultibalances', array(), array(), 0, true));

		$labels=multichain_labels();

		foreach ($addressmine as $address => $ismine) {
			if (count(@$addresspermissions[$address]))
				$permissions=implode(', ', @array_keys($addresspermissions[$address]));
			else
				$permissions='none';

			$label=@$labels[$address];
			$cansetlabel=$ismine && @$addresspermissions[$address]['send'];

			if ($ismine && !$cansetlabel)
				$permissions.=' (cannot set label)';
?>
						<table class="table table-bordered table-condensed table-break-words <?php echo ($address==@$getnewaddress) ? 'bg-success' : 'table-striped'?>">
<?php
			if (isset($label) || $cansetlabel) {
?>
							<tr>
								<th style="width:30%;">Label</th>
								<td><?php echo html(@$label)?><?php

				if ($cansetlabel)
					echo (isset($label) ? ' &ndash; ' : '').
					'<a href="'.chain_page_url_html($chain, 'label', array('address' => $address)).'">'.
					(isset($label) ? 'change label' : 'Set label').
					'</a>';

								?></td>
							</tr>
<?php
			}
?>
							<tr>
								<th style="width:30%;">Address</th>
								<td class="td-break-words small"><?php echo html($address)?><?php echo $ismine ? '' : ' (watch-only)'?></td>
							</tr>

                            

							<tr>
								<th>Permissions</th>
								<td><?php echo html($permissions)?><?php

					echo ' &ndash; <a href="'.chain_page_url_html($chain, 'permissions', array('address' => $address)).'">change</a>';

							?></td></tr>
<?php
			if (isset($getmultibalances[$address])) {
				foreach ($getmultibalances[$address] as $addressbalance) {
?>
							<tr>
								<th><?php echo html($addressbalance['name'])?></th>
								<td><?php echo html($addressbalance['qty'])?></td>
							</tr>
<?php
				}
			}
?>
						</table>
<?php
		}
	}
?>



					<form class="form-horizontal" method="post" action="<?php echo chain_page_url_html($chain)?>">
						<div class="form-group">
							<div class="col-xs-12">
								<input class="btn btn-default" name="getnewaddress" type="submit" value="Get new address">
							</div>
						</div>
					</form>
				</div>
			</div>
