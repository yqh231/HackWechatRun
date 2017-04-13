<?php

$steps = 0;
$users = array(
    'uid1' => 'pc1',
    'uid2' => 'pc2',
    # 多用户以此类推
    );

class HackWechatRun{
    function genertateUrl($uid){
        return "http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=".$uid;
    }
    function genertatePostData($pc, $steps){
        $calories = $distance = $duration = 1;
        $date = mktime(0, 0, 0, date("n"), date("j"), date("Y"));
        $key = md5("ldl_pro@".$date."#".$steps."$".$calories."^".$distance."&".$duration);
        $post = "pc=".$pc."&stats=[{\"calories\": ".$calories.", \"distance\": ".$distance.", \"duration\": ".$duration.", \"steps\": ".$steps.", \"key\": \"".$key."\", \"date\": ".$date."}]";
        return $post;
    }
    function upload($url, $post){
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
        $rsp = curl_exec($ch);
        curl_close($ch);
    }
}

$API = new HackWechatRun;
foreach ($users as $key => $value) {
    $API->upload($API->genertateUrl($key), $API->genertatePostData($value, $steps));
}
