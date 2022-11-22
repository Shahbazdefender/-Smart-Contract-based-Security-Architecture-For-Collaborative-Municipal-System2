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
						<img src="Smarthome1.jpg" alt="Norway" width="560" height="200">
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

<?php
	define('const_issue_custom_fields', 10);
	
	$max_upload_size=multichain_max_data_size()-512; // take off space for file name and mime type

	$success=false; // set default value

	if (@$_POST['issueasset']) {
		$multiple=(int)round(1/$_POST['units']);
		
		$addresses=array( // array of addresses to issue units to
			$_POST['to'] => array(
				'issue' => array(
					'raw' => (int)($_POST['qty']*$multiple)
				)
			)
		);
		
		$custom=array();
		
		for ($index=0; $index<const_issue_custom_fields; $index++)
			if (strlen(@$_POST['key'.$index]))
				$custom[$_POST['key'.$index]]=$_POST['value'.$index];

		$datas=array( // to create array of data items
			array( // metadata for issuance details
				'create' => 'asset',
				'name' => $_POST['name'],
				'multiple' => $multiple,
				'open' => true,
				'details' => $custom,
			)
		);
		
		$upload=@$_FILES['upload'];
		$upload_file=@$upload['tmp_name'];

		if (strlen($upload_file)) {
			$upload_size=filesize($upload_file);

			if ($upload_size>$max_upload_size) {
				output_html_error('Uploaded file is too large ('.number_format($upload_size).' > '.number_format($max_upload_size).' bytes).');
				return;
		} else {
				$datas[0]['details']['@file']=fileref_to_string(2, $upload['name'], $upload['type'], $upload_size); // will be in output 2
				$datas[1]=bin2hex(file_to_txout_bin($upload['name'], $upload['type'], file_get_contents($upload_file)));
			}
		}
		
		if (!count($datas[0]['details'])) // to ensure it's converted to empty JSON object rather than empty JSON array
			$datas[0]['details']=new stdClass();
		
		$success=no_displayed_error_result($issuetxid, multichain('createrawsendfrom', $_POST['from'], $addresses, $datas, 'send'));
				
		if ($success)
			output_success_text('Asset successfully issued in transaction '.$issuetxid);
	}

	$getinfo=multichain_getinfo();

	$issueaddresses=array();
	$keymyaddresses=array();
	$receiveaddresses=array();
	$labels=array();

	if (no_displayed_error_result($getaddresses, multichain('getaddresses', true))) {

		if (no_displayed_error_result($listpermissions,
			multichain('listpermissions', 'issue', implode(',', array_get_column($getaddresses, 'address')))
		))
			$issueaddresses=array_get_column($listpermissions, 'address');
		
		foreach ($getaddresses as $address)
			if ($address['ismine'])
				$keymyaddresses[$address['address']]=true;
				
		if (no_displayed_error_result($listpermissions, multichain('listpermissions', 'receive')))
			$receiveaddresses=array_get_column($listpermissions, 'address');

		$labels=multichain_labels();
	}
?>

			<div class="row">

				<div class="col-sm-4">
					<h3><strong>Device Configration</strong></h3>
			
<?php
	if (no_displayed_error_result($listassets, multichain('listassets', '*', true))) {

		foreach ($listassets as $asset) {
			$name=$asset['name'];
			$issuer=$asset['issues'][0]['issuers'][0];
?>
<style>
tr, td {
  border-style:solid;
  border-color: #96D4D4;
}
</style>

						<table  style="width:1200%" class="table table-bordered table-condensed table-break-words <?php echo ($success && ($name==@$_POST['name'])) ? 'bg-success' : 'table-striped'?>">
							<tr>
								<th style="width:30%;">Device Name</th>
								<td ><?php echo html($name)?> <?php echo $asset['open'] ? '' : '(closed)'?></td>
							</tr>
							<tr>
								<th >KW</th>
								<td ><?php echo html($asset['issueqty'])?></td>
							</tr>
							<tr>
								<th >Units</th>
								<td ><?php echo html($asset['units'])?></td>
							</tr>
							<tr>
								<th >Issuer Adrress</th>
								<td class="td-break-words small" ><?php echo format_address_html($issuer, @$keymyaddresses[$issuer], $labels)?></td>
							</tr>
<?php
			$details=array();
			$detailshistory=array();
			
			foreach ($asset['issues'] as $issue)
				foreach ($issue['details'] as $key => $value) {
					$detailshistory[$key][$issue['txid']]=$value;
					$details[$key]=$value;
				}
				
			if (count((array)@$detailshistory['@file'])) {
?>
							<tr>
								<th>File:</th>
								<td><?php
								
				$countoutput=0;
				$countprevious=count($detailshistory['@file'])-1;

				foreach ($detailshistory['@file'] as $txid => $string) {
					$fileref=string_to_fileref($string);
					if (is_array($fileref)) {
					
						$file_name=$fileref['filename'];
						$file_size=$fileref['filesize'];
						
						if ($countoutput==1) // first previous version
							echo '<br/><small>('.$countprevious.' previous '.(($countprevious>1) ? 'files' : 'file').': ';
						
						echo '<a href="./download-file.php?chain='.html($_GET['chain']).'&txid='.html($txid).'&vout='.html($fileref['vout']).'">'.
							(strlen($file_name) ? html($file_name) : 'Download').
							'</a>'.
							( ($file_size && !$countoutput) ? html(' ('.number_format(ceil($file_size/1024)).' KB)') : '');
						
						$countoutput++;
					}
				}
				
				if ($countoutput>1)
					echo ')</small>';
								
								?></td>
							</tr>	
<?php
			}
			
			foreach ($details as $key => $value) {
				if ($key=='@file')
					continue;
?>
							<tr>
								<th><?php echo html($key)?></th>
								<td><?php echo html($value)?><?php
								
				if (count($detailshistory[$key])>1)
					echo '<br/><small>(previous values: '.html(implode(', ', array_slice(array_reverse($detailshistory[$key]), 1))).')</small>';
				
								?></td>
							</tr>
<?php
			}
?>							
						</table>
<?php
		}
	}
?>
				</div>
				
