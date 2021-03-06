<?php
include "../database/database.php";
// POST
// add_recommand.php?name={1}&jump_mode={2}&jump_url={3}&jump_text={4}

$name = $_POST["name"];
$jump_mode = $_POST["jump_mode"];
$jump_url = $_POST["jump_url"];
$jump_text = $_POST["jump_text"];
$image_file = $_FILES["image"];
$big_qr = $_FILES["qr"];

if ($name==="" || ($jump_mode != "0" && $jump_mode != "1")) {
	header("Location:add_fail.php");
} else {
	$ret = doAddRecommand($name, $jump_mode, $jump_url, $jump_text, $image_file, $big_qr);
	if ($ret != 0) {
		header("Location:add_succ.php");
	} else {
		header("Location:add_fail.php");
	}
}

function doAddRecommand($n, $jm, $ju, $jt, $img, $qr) {
	$extend =get_extend($img["name"]);
	if ($extend === "") {
		return "0";
	}
	$filename = "file_".date("YYmmddhhiiss").".".$extend;
        move_uploaded_file($img["tmp_name"], "./recommand/".$filename);

	$qr_extend = get_extend($qr["name"]);
	$qr_filename = "";
	if ($qr_extend != "") {
		$qr_filename = "qr_".date("YYmmddhhiiss").".".$qr_extend;
		move_uploaded_file($qr["tmp_name"], "./recommand/".$qr_filename);
	}

	$id = generateId("yugioh_recommand", "id");
	$sql = "insert into yugioh_recommand (id,name,jump_mode,jump_url,jump_text,image_name, big_qr) values ($id,'$n',$jm,'$ju','$jt','$filename','$qr_filename')";
	$db = openConnection();
	$result = query($db, $sql);
	closeConnection($db);
	return $result;
	
}

function get_extend($file_name) {
        $extend =explode("." , $file_name);
        $va=count($extend)-1;
        return strtolower($extend[$va]);
}
?>
