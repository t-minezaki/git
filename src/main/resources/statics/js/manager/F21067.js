var params = getParam();
var vm = new Vue({
    el: ".page",
    data: {
        mstMessageEntity: '',
        messageCont: '',
        stuList: [],
        mstOrgEntity: '',
        path:''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        //初期表示
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F21067/init',
                type: 'GET',
                data: {
                    messageId: params.id
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.mstOrgEntity) {
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    /*2020/11/19 liyuhuan add start */
                    if (data.mstMessageEntity.attachFilePath!="" && data.mstMessageEntity.attachFilePath!=null) {
                        var str = data.mstMessageEntity.attachFilePath.substring(data.mstMessageEntity.attachFilePath.lastIndexOf('\\') + 1);
                        vm.path = str.slice(str.lastIndexOf("/") + 1);
                    }
                    /*2020/11/19 liyuhuan add end */
                    if (data.mstMessageEntity) {
                        vm.mstMessageEntity = data.mstMessageEntity;
                        vm.messageCont = vm.mstMessageEntity.messageCont;
                        vm.mstMessageEntity.updDatime = vm.mstMessageEntity.updDatime.replace(/(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2}).?(\d{1,3})?/, '$1/$2/$3 $4:$5');
                        if (vm.messageCont == null) {
                            $("#messageCont").html("");
                        } else {
                            $("#messageCont").html(decodeURIComponent(vm.messageCont));
                            //背景色
                            uParse('#messageCont', {
                                rootPath: '../plugins/ueditor-1.4.3.3'
                            })
                        }
                        vm.stuList = data.stuList;
                    }
                    if (data.mstMessageEntity.attachFilePath!="" && data.mstMessageEntity.attachFilePath!=null) {
                        //一致文字列
                        var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                        var re = new RegExp(rgx);
                        var fileList = data.mstMessageEntity.attachFilePath.split(',');
                        var tag = '';
                        for (var i=0;i<fileList.length;i++ ){
                            if (IsPicture(fileList[i])) {
                                var src = encodeURIComponent(fileList[i].replace(/\\/g,"/"));
                                tag  +="<a id='showFile' onclick=previewImg('"+src+"')>"+ handleFileName(fileList[i]) +"</a>";
                            }else if (!re.test(fileList[i].replace(/\\/g,"/"))) {
                                tag +="<a id='showFile' onclick='pdfClick()' title='"+ fileList[i] +"' >"+ handleFileName(fileList[i]) +"</a>";
                            }else {
                                tag +="<a onclick='pdfClick()' href='"+ fileList[i]+"' >"+ handleFileName(fileList[i]) +"</a>";
                            }
                            tag +="<br>"
                        }
                        $('.drag_area').append(tag);
                    }
                }
            })
        }
    }
});

//戻るボタン押下
$("#backBtn").click(function () {
    window.location.href = '../manager/F21062.html'
});