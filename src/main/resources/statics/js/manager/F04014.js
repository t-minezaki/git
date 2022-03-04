var param = getParam();
var vm = new Vue({
    el:".page",
    data:{
        mstNoticeEntity: '',
        noticeCont:'',
        orgList:[],
        mstOrgEntity:'',
        path:''
    },
    mounted:function () {
        this.getInfo();
    },
    methods:{
        //初期表示
        getInfo:function () {
            $.ajax({
                url:ctxPath + '/manager/F04014/init',
                type:'GET',
                data:{
                    id: param.id
                },
                async:true,
                datatype:'json',
                success:function (data) {
                    if (data.mstOrgEntity){
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    /*2020/11/19 liyuhuan add start */
                    if (data.mstNoticeEntity.attachFilePath!="" && data.mstNoticeEntity.attachFilePath!=null) {
                        //一致文字列
                        var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                        var re = new RegExp(rgx);
                        var fileList = data.mstNoticeEntity.attachFilePath.split(',');
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
                    /*2020/11/19 liyuhuan add end */
                    if(data.mstNoticeEntity){
                        vm.mstNoticeEntity = data.mstNoticeEntity;
                        vm.noticeCont = vm.mstNoticeEntity.noticeCont;
                        if (vm.noticeCont==null){
                            $("#noticeCont").html("");
                        } else {
                            $("#noticeCont").html(decodeURIComponent(vm.noticeCont));
                            //背景色
                            uParse('#noticeCont', {
                                rootPath: '../plugins/ueditor-1.4.3.3'
                            })
                        }
                        vm.orgList = data.orgList;
                    }
                }
            })
        }
    }
});

//戻るボタン押下
$("#backBtn").click(function () {
    window.location.href = '../manager/F04011.html'
})