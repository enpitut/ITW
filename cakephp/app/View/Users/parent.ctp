<html>
<head>
</head>
<body>
    <div class="text-center container">
      <div class="
      col-xs-offset-1 col-sm-offset-1 col-md-offset-1
      col-xs-2 col-sm-7 col-md-7">
      <?php
      echo $this->Form->create('BoostCake', array(
        'inputDefaults' => array(
            'div' => 'form-group',
            'label' => false,
            'wrapInput' => false,
            'class' => 'form-control'
            ),
        'class' => 'well form-inline'
        ));
		///ここに，配列に入っているフレンドをすべて表示
      $num = 9;
      $count=0;
      print('<p>子供の名前情報など'.$count.'</p>');

      print('<form>');
      print('<input type="button" value="1" onclick="entryPlan(0)">');
      print('<input type="button" value="2" onclick="entryPlan(1)">');
      print('</form>');


      $this->Form->end();

      echo '<br>'.$this->Html->link(
        'メイン画面へ..',
        './	index'
        );
        ?>
    </div>


    <div class="
    col-xs-offset-1 col-sm-offset-1 col-md-offset-1
    col-xs-1 col-sm-3 col-md-3">
    <?php
    echo $this->Form->create('BoostCake', array(
        'inputDefaults' => array(
            'div' => 'form-group',
            'label' => false,
            'wrapInput' => false,
            'class' => 'form-control'
            ),
        'class' => 'well form-inline'
        ));

    print(
        $this->Form->create('Parent') .
        $this->Form->hidden('user_id',array('value' => $user['id'])).
        $this->Form->input('friendsid', array(
            'label'=>false,
            'placeholder' => '子供のユーザID',
            'class'=>'form-control'
            )) .
        $this->Form->input('friendsid', array(
            'label'=>false,
            'placeholder' => '子供のパスワード',
            'class'=>'form-control'
            )) .

        $this->Form->submit('登録', array(
            'div' => 'form-group',
            'class' => 'btn btn-default'
            )));

    $this->Form->end();
    ?>
    <script>


      function entryChange1(){
       radio = document.getElementById('entryPlan') 
       if(radio==0) {
			//フォーム
            document.getElementById('1stBox').style.display = "";
            document.getElementById('2ndBox').style.display = "none";
        }else if(radio==1) {
				//フォーム
				document.getElementById('1stBox').style.display = "none";
				document.getElementById('2ndBox').style.display = "";
			}

		}

	</script>







</div>
</div>


</body>
</html>
