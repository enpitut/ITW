<html>
<head>
<script type="text/javascript">
    jQuery(function($){
        $('.table').footable(
            breakpoints:{
                phone: 480,
                tablet: 800
            });
    });
</script>
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
            echo "<td><a data-reveal-id='deletemenu".$dat['User']['id']."'><span class='glyphicon glyphicon-remove' onclick=window.sessionStorage.setItem(['delete_id'],".$dat['User']['id'].")></span></a></td>";
            echo "</tr>";
        }
        echo "<tr>
        <td colspan=1><a id='#registermenu' href='#registermenu' data-reveal-id='registermenu'>"."<span class='glyphicon glyphicon-plus'></span> 追加"."</a></td>
    </tr>";
    ?>
</tbody>
</table>
<body>

<div id="registermenu" class="reveal-modal" data-reveal>
<h2>登録</h2>

<?php
        echo $this->Form->create('register', array(
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
                'class' => 'btn btn-default'
            ));
            echo $this->Form->end();
?>
</div>

<?php
    foreach ($userdata as $dat) {
        echo "<div id='deletemenu".$dat['User']['id']."' class='reveal-modal' data-reveal>";
        echo $dat['User']['username']."を削除しますか？";
        echo $this->Session->read('delete_id');
        echo $this->Form->create('delete', array(
            'inputDefaults' => array(
                'div' => 'form-group',
                'label' => false,
                'wrapInput' => false,
                'class' => 'form-control'
            ),
            'class' => 'well form-inline'
        ));
        echo $this->Form->hidden('deleteid' ,array('value' => $dat['User']['id']));
        echo $this->Form->submit('削除', array(
            'div' => 'form-group',
            'class' => 'btn btn-danger '
        ));
        echo $this->Form->end();
        echo "</div>";
    }
?>
</body>
</html>
