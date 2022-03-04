var parma = getParam()
var id = parma.id;
var stuId= parma.stuId;
var inx = parent.layer.getFrameIndex(window.name);
var flag =false ;
var stats= 1;
if (id!="undefined") {
    flag = true;
}

var vm = new Vue({
    el:"#page",
    data:{
        flag:flag,
        title:'',
        time:'',
        content:''
    },
    mounted: function () {
        laydate.render({
            elem: '#showTime',
            eventElem: '#timeBtn',
            trigger: 'click',
            type: 'datetime',
            format: 'yyyy/MM/dd HH:mm',
            done: function (value) {
                $("#showTime").val(value)
            }
        });
        if (id!="undefined"){
            this.getInfo()
        }


    },
    update: function () {

    },
    methods: {
        getInfo: function () {
            $.ajax({
                url:ctxPath + '/manager/F21041/getAbout',
                data:{
                    id:id
                },
                type:'GET',
                datatype:"json",
                success:function (data) {
                    $(".title").val(data.askAboutRecordEntity.askTitle);
                    $("#showTime").val(data.askAboutRecordEntity.askDatime.substring(0,16).replace(/-/g,'/'));
                    vm.content = data.askAboutRecordEntity.askCont;
                }
            })
        }
    }
});
var msg='';
function back() {
    parent.layer.close(inx);
}
function add() {
    stats=1;
    msg='登録';
    addOrUpdate();
}

function update() {
    stats=2;
    msg='修正';
    addOrUpdate();
}
function getDel() {
    stats=3;
    msg='削除';
    addOrUpdate();
}
function addOrUpdate() {
if (stats==1||stats==2){
    if ($(".title").val()==""){
        var index=layer.confirm($.format($.msg.MSGCOMD0001,'タイトル'), {
            skin: 'layui-layer-molv',
            closeBtn:0,
            shadeClose:false,
            btn:['確認'],
            yes:function () {
                layer.close(index);
            },
        });
        return false;
    }
    if ($("#showTime").val()==""){
        var index=layer.confirm($.format($.msg.MSGCOMD0001,'問合せ日時'), {
            skin: 'layui-layer-molv',
            closeBtn:0,
            shadeClose:false,
            btn:['確認'],
            yes:function () {
                layer.close(index);
            },
        });
        return false;
    }
    if ($(".detail").val()==""){
        var index=layer.confirm($.format($.msg.MSGCOMD0001,'内容'), {
            skin: 'layui-layer-molv',
            closeBtn:0,
            shadeClose:false,
            btn:['確認'],
            yes:function () {
                layer.close(index);
            },
        });
        return false;
    }
}
        var ind=layer.confirm($.format($.msg.MSGCOMN0021,msg), {
            skin: 'layui-layer-molv',
            closeBtn:0,
            shadeClose:false,
            btn:['キャンセル','確認'],
            btn1:function () {
                layer.close(ind);
            },
            btn2:function () {
                $.ajax({
                    url:ctxPath + '/manager/F21041/addOrUpdate',
                    data:{
                        id:id,
                        stats:stats,
                        stuId:stuId,
                        title:$(".title").val(),
                        time:new Date($("#showTime").val()+":"+"00"),
                        content:$(".detail").val().trim()
                    },
                    type:'POST',
                    datatype:'json',
                    success:function (data) {
                        if (data.code==0){
                                // var index=layer.confirm($.format($.msg.MSGCOMN0022,msg), {
                                //     skin: 'layui-layer-molv',
                                //     closeBtn:0,
                                //     shadeClose:false,
                                //     btn:['確認'],
                                //     btn1:function () {
                                        parent.layer.close(inx);
                                        parent.vm.getInit();

                                //     },
                                // });
                        }
                    }
                })
            }
        });
}