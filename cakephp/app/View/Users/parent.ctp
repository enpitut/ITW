<html>
<head>

</head>

<table class="table" style="margin-right : auto;margin-left : auto;width : auto">
    <thead>
        <tr>
            <th>アイコン</th>
            <th>名前</th>
        </tr>
    </thead>

    <tbody>
        <?php
        foreach ($userdata as $dat) {
            echo "<tr>";
            echo "<td>".$this->Html->image("marker/".$dat['User']['imgpath'],array('width'=>'50','height'=>'50'))."</td>";
            echo "<td>".$dat['User']['username']."</td>";
            echo "<td><a>"."<span class='glyphicon glyphicon-remove'></span>"."</a></td>";
            echo "</tr>";
        }
        echo "<tr>
        <td colspan=1><a id='#resistmenu' href='#registmenu' data-reveal-id='registmenu'>"."<span class='glyphicon glyphicon-plus'></span> 追加"."</a></td>
    </tr>";
    ?>
</tbody>
</table>
<script type="text/javascript">
    jQuery(function($){
        $('.table').footable(
            breakpoints: {
                phone: 480,
                tablet: 800
            });
    });
</script>




<body>

<div id="registmenu" class="reveal-modal" data-reveal>
<h2>登録</h2>

<?php
echo $this->Session->read('Visitor.id');
        echo $this->Form->create('BoostCake', array(
            'inputDefaults' => array(
                'div' => 'form-group',
                'label' => false,
                'wrapInput' => false,
                'class' => 'form-control'
            ),
            'class' => 'well form-inline'
        ));
            echo $this->Form->create('User');
            echo $this->Form->input('username', array(
                'label'=>false,
                'placeholder' => 'ユーザ名',
                'class'=>'form-control'
            ));
            echo $this->Form->input('password', array(
                'label'=>false,
                'placeholder' => 'パスワード',
                'class'=>'form-control'
            ));
            echo $this->Form->hidden('parentid' ,array('value' => $this->Session->read('Visitor.id')));
            echo $this->Form->submit('登録', array(
                'div' => 'form-group',
                'class' => 'btn btn-de  fault'
            ));
            echo $this->Form->end();
?>
</div>
</body>
</html>
