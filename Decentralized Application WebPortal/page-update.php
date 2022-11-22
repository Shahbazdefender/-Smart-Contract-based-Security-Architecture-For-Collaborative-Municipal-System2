
<?php
	define('const_update_custom_fields', 5);
	
	$max_upload_size=multichain_max_data_size()-512; // take off space for file name and mime type

	$success=false; // set default value

	if (@$_POST['updateasset']) {
		if (!no_displayed_error_result($listassets, multichain('listassets', $_POST['issuetxid'], true)))
			return;
			
		$from=$listassets[0]['issues'][0]['issuers'][0];
		$multiple=$listassets[0]['multiple'];

		$addresses=array( // array of addresses to issue units to
			$_POST['to'] => array(
				'issuemore' => array(
					'asset' => $_POST['issuetxid'],
					'raw' => (int)($_POST['qty']*$multiple),
				)
			)
		);
		
		$custom=array();
		
		for ($index=0; $index<const_update_custom_fields; $index++)
			if (strlen(@$_POST['key'.$index]))
				$custom[$_POST['key'.$index]]=$_POST['value'.$index];

		$datas=array( // to create array of data items
			array( // metadata for reissuance details
				'update' => $_POST['issuetxid'],
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
		
		$success=no_displayed_error_result($issuetxid, multichain('createrawsendfrom', $from, $addresses, $datas, 'send'));
			
		if ($success)
			output_success_text('Asset successfully updated in transaction '.$issuetxid);
	}

	$getinfo=multichain_getinfo();

	$issueaddresses=array();
	$keymyaddresses=array();
	$receiveaddresses=array();
	$labels=array();

	if (no_displayed_error_result($getaddresses, multichain('getaddresses', true))) {

		if (no_displayed_error_result($listpermissions,
			multichain('listpermissions', 'send', implode(',', array_get_column($getaddresses, 'address')))
		))
			$followaddresses=array_get_column($listpermissions, 'address');
		
		foreach ($getaddresses as $address)
			if ($address['ismine'])
				$keymyaddresses[$address['address']]=true;
				
		if (no_displayed_error_result($listpermissions, multichain('listpermissions', 'receive')))
			$receiveaddresses=array_get_column($listpermissions, 'address');

		$labels=multichain_labels();
	}
?>

			
<?php
	if (no_displayed_error_result($listassets, multichain('listassets', '*', true))) {

		foreach ($listassets as $asset) {
			$name=$asset['name'];
			$issuer=$asset['issues'][0]['issuers'][0];
			
			if (!($asset['open'] && @$keymyaddresses[$issuer]))
				continue;
?>
					<?php
		}
	}
?>

<style>
.col-sm-8 {
 border-style:solid;
  border-color: #96D4D4;
  text-align: center;
  border: 3px solid green;
 
}

</style>>				
				<div class="col-sm-8">
					<h3>Update Configration</h3>
					
					<form class="form-horizontal" method="post" enctype="multipart/form-data" action="./?chain=<?php echo html($_GET['chain'])?>&page=<?php echo html($_GET['page'])?>">
						<div class="form-group">
							<label for="issuetxid" class="col-sm-2 control-label">Device Name:</label>
							<div class="col-sm-9">
							<select class="form-control col-sm-6" name="issuetxid" id="issuetxid">
<?php
	foreach ($listassets as $asset) {
		$issuer=$asset['issues'][0]['issuers'][0];

		if (($asset['open'] && @$keymyaddresses[$issuer])) {
?>
								<option value="<?php echo html($asset['issuetxid'])?>"><?php echo html($asset['name'])?></option>
<?php
		}
	}
?>
							</select>
							</div>
						</div>

						<div class="form-group">
							<label for="qty" class="col-sm-2 control-label">KW:</label>
							<div class="col-sm-9">
								<input class="form-control" name="qty" id="qty" value="0">
							</div>
						</div>

						<div class="form-group">
							<label for="to" class="col-sm-2 control-label">To address:</label>
							<div class="col-sm-9">
							<select class="form-control col-sm-6" name="to" id="to">
<?php
	foreach ($receiveaddresses as $address) {
		if ($address==$getinfo['burnaddress'])
			continue;
?>
								<option value="<?php echo html($address)?>"><?php echo format_address_html($address, @$keymyaddresses[$address], $labels)?></option>
<?php
	}
?>						
							</select>
							</div>
						</div>

						<div class="form-group">
							<label for="upload" class="col-sm-2 control-label">Update file:<br/><span style="font-size:75%; font-weight:normal;">Max <?php echo floor($max_upload_size/1024)?> KB</span></label>
							<div class="col-sm-9">
								<input class="form-control" type="file" name="upload" id="upload">
							</div>
						</div>
<?php
	for ($index=0; $index<1; $index++) {
?>
						<div class="form-group">
							<label for="key<?php echo $index?>" class="col-sm-2 control-label"><?php echo $index ? '' : 'Update fields:'?></label>
							<div class="col-sm-3">
								<input class="form-control input-sm" name="key<?php echo $index?>" id="key<?php echo $index?>" placeholder="key" value="Trust">
							</div>
							<div class="col-sm-6">
								<input class="form-control input-sm" name="value<?php echo $index?>" id="value<?php echo $index?>" placeholder="value" value=0>
							</div>
						</div>
<?php
	}
?>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-9">
								<input class="btn btn-default" type="submit" name="updateasset" value="Update Asset">
							</div>
						</div>
					</form>

				</div>
			</div>
