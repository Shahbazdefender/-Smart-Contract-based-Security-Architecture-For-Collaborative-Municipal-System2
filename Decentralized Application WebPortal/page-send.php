
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
						<style>
.col-sm-8 {
 border-style:solid;
  border-color: #96D4D4;
  text-align: center;
  border: 3px solid green;
 
}
</style>


				
				<div class="col-sm-8">
					<h3><strong>Registration Setup</strong></h3>
					
					<form class="form-horizontal" method="post" enctype="multipart/form-data" action="./?chain=<?php echo html($_GET['chain'])?>&page=<?php echo html($_GET['page'])?>">
						<div class="form-group">
							<label for="from" class="col-sm-2 control-label">From address:</label>
							<div class="col-sm-9">
							<select class="form-control col-sm-6" name="from" id="from">
<?php
	foreach ($issueaddresses as $address) {
?>
								<option value="<?php echo html($address)?>"><?php echo format_address_html($address, true, $labels)?></option>
<?php
	}
?>						
							</select>
							</div>
						</div>
						<style>
.form-group {
 border-style:solid;
  border-color: #96D4D4;
  text-align: center;
  border: 3px solid green;
 
}
</style>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">Email Address</label>
							<div class="col-sm-9">
								<input class="form-control" name="name" id="name" placeholder="shahbaz@nu.edu.pk">
							</div>
						</div>
						<div class="form-group">
							<label for="qty" class="col-sm-2 control-label">NIC Number:</label>
							<div class="col-sm-9">
								<input class="form-control" name="qty" id="qty" placeholder="42101-XXXX-XXXX">
								
							</div>
						</div>

						
<?php
?>
<?php
    $message3 = hash('md5', '42101-7620650-5');
?>

<?php
$my_array = array("42101-7620650-5","42101-7620650-2","42101-7620650-3");

?>

<html>
   
   <head>
      <title>Writing a file using PHP</title>
   </head>
   
   <body>
   <?php
   $message = "";
   if(isset($_POST['SubmitButton'])){ //check if form was submitted
	 $input = $_POST['inputText']; //get input text
	 list($a, $b, $c) = $my_array;
	 //echo "I have several animals, a $a, a $b and a $c.";
	 list($a, , $c) = $my_array;
	 echo strcmp($a,"42101-7620650-5");
		  
	 $message = "Success! You entered: ".$input;
	 
   }    
   ?>
   <?php
    if(isset($_POST['SubmitButton'])){ // Check if form was submitted
       
		
        $input = $_POST['inputText']; // Get input text Shahbaz
		//Study this Shahbaz

		$rand = rand(12346678,9);
		$input2 =hash('md5', $rand);
		?>
		<script>window.location.href="http://127.0.0.1/Grand%20Blockchain/?chain=default&page=publish&text=<?php echo $input2; ?>";</script>
		<?php
        $message = "Your Token For Registration is: " . $input2;
		//Shahbaz Code End 
 
	
	}
	


	
?>


<html>
    <body>
        <form action="#" method="post">
            <?php echo $message; ?>
            <input type="text" name="inputText"/>
            <input type="submit" name="SubmitButton"/>
        </form>
    </body>
</html>   
      <?php
         $filename = "newfile.txt";
         $file = fopen( $filename, "r" );
         
         if( $file == false ) {
            echo ( "Error in opening file" );
            exit();
         }
         
         $filesize = filesize( $filename );
         $filetext = fread( $file, $filesize );
         
         fclose( $file );
         
         
      ?>
      
   </body>
</html>
			


				
			<?php
   $filename = "newfile.txt";
   $file = fopen( $filename, "w" );
   
   if( $file == false ) {
      echo ( "Error in opening new file" );
      exit();
   }
   fwrite( $file, "\n" );
   fclose( $file );

   