Vue.component('ask-talk-template', {
    data: function () {
        return {
            askTalkEntity: this.askTalkEntity,
            optContList: [],
            answerTypeDiv: ''
        }
    },
    props: {
        askTalkEntity: {
            type: Object,
            default: function () {
                return {}
            }
        }
    },
    mounted:function () {
        this.init();
    },
    template: '<li class="question">\n' +
        '            <label>質問{{index(askTalkEntity.askNum)}}</label>\n' +
        '            <textarea oninput="vm.input(this)" onfocus="vm.focus(this)" onblur="vm.blur(this)" class="questionName textarea" maxlength="50" placeholder="設問名" v-model="askTalkEntity.questionName"></textarea>\n' +
        '            <select required class="answerTypeDiv" v-model="answerTypeDiv">\n' +
        '            <option value="" style="display: none;">設問形式</option>' +
        '            <option class="blank" value="-1"></option>\n' +
        '                <option v-for="dto in $parent.mstCodDEntityList" :value="dto.codCd" v-if="askTalkEntity.itemTypeDiv !== \'1\' || dto.codCd !== \'3\'">\n' +
        '                    {{dto.codValue}}\n' +
        '                </option>\n' +
        '            </select>\n' +
        '            <textarea oninput="vm.input(this)" onfocus="vm.focus(this)" onblur="vm.blur(this)" class="textarea" v-show="askTalkEntity.answerTypeDiv !== \'0\' && askTalkEntity.answerTypeDiv !== \'3\'" v-for="opt in optContList" maxlength="50" :placeholder="\'選択肢\' + index(opt.index)" v-model="opt.cont"></textarea>\n' +
        '            <button id="addOption" class="add-option" v-if="optContList.length < 10 && askTalkEntity.answerTypeDiv !== \'0\'&& askTalkEntity.answerTypeDiv !== \'3\'" @click="addOption()">+</button>' +
        '        </li>',
    methods: {
        init: function (){
            this.optContList = [];
            for (var i = 1; i <= 5; i++) {
                this.optContList.push(
                    {
                        index: i,
                        cont: this.askTalkEntity["optCont" + i]
                    }
                )
            }
            var index = 6;
            for (; i <= 10; i++){
                if (this.askTalkEntity["optCont" + i] && this.askTalkEntity["optCont" + i] !== ''){
                    this.optContList.push(
                        {
                            index: index,
                            cont: this.askTalkEntity["optCont" + i]
                        }
                    );
                    index ++;
                }
            }
            this.answerTypeDiv = this.askTalkEntity.answerTypeDiv === ' ' ? '' : this.askTalkEntity.answerTypeDiv;
        },
        index: function (index){
            if (index === 1){
                return '１';
            }else if (index === 2){
                return '２';
            }else if (index === 3){
                return '３';
            }else if (index === 4){
                return '４';
            }else if (index === 5){
                return '５';
            }else if (index === 6){
                return '６';
            }else if (index === 7){
                return '７';
            }else if (index === 8){
                return '８';
            }else if (index === 9){
                return '９';
            }else if (index === 10){
                return '１０';
            }
        },
        addOption: function (){
            if (this.optContList.length < 10){
                var last_index = this.optContList[this.optContList.length - 1].index;
                this.optContList.push({
                    index: last_index + 1,
                    cont: ''
                })
            }
        }
    },
    computed: {
        entity: function () {
            var index = 1;
            for (var i = 0; i < this.optContList.length; i++) {
                var optCont = this.optContList[i];
                if (optCont.cont !== ''){
                    this.askTalkEntity['optCont' + index] = optCont.cont;
                    index ++;
                }
            }
            for (; index <= 10; index ++){
                this.askTalkEntity['optCont' + index] = '';
            }
            if (this.askTalkEntity.answerTypeDiv === ' '){
                this.askTalkEntity.answerTypeDiv = '';
            }
            return this.askTalkEntity;
        }
    },
    watch: {
        answerTypeDiv(newValue, oldValue){
            var value = (newValue === '-1' ? '' : newValue);
            this.askTalkEntity.answerTypeDiv = value;
            this.answerTypeDiv = value;
            if (value === ''){
                this.optContList.forEach(function (value, index, array){
                    value.cont = '';
                })
                this.askTalkEntity.questionName = '';
            }
        },
        askTalkEntity(newValue, oldValue){
            this.init();
        }
    },
})