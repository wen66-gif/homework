<template>
    <div >
        <div >{{question.no}}.{{question.title}}（{{question.score}}分）</div>
        <div style="margin-top: 10px">
            <a-form
                    @blur.native.capture="handleSubmit"
                    :form="form"
                    :label-col="{ span: 1 }"
                    :wrapper-col="{ span: 15 }"
            >
                <a-form-item  v-for="(t,index) in blankId" :key="t" :label="['（'+(index+1)+'）']" :wrapper-col="{ span: 15 }" style="">
                    <a-textarea
                            style="width: 600px;"
                            v-decorator="[`${t}`, { rules: [{ required: true, message: '请输入正确答案！' }] }]"
                            :auto-size="{ minRows: 3, maxRows: 5 }"
                    />
                </a-form-item>
            </a-form>

        </div>
    </div>
</template>

<script>
    import {nanoid} from "nanoid";
    import {mapMutations} from "vuex";

    export default {
        name: "BlankCard",
        props:['question','studentAnswer'],
        data(){
            return{

                blankCount:'',
                blankContent:[],
                blankId:[],
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        mounted() {
            // 解析问题空数
            this.blankCount = this.question.trueAnswer.split("_").length
            for (let i = 0; i < this.blankCount; i++){
                this.blankId.push(nanoid())
            }
        },
        methods:{
            ...mapMutations(['UPDATE_STUDENTANSWER']),
            // 失去焦点的时候将答案保存到vuex
            handleSubmit(){
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let answer = []
                        for(let i = 0;i < this.blankId.length;i++){
                            let id = this.blankId[i]
                            // 将values中的属性值拿出来
                            answer.push(values[id])
                            // 删除key为当前id的属性
                            delete values[id]
                        }
                        answer = answer.join("_")
                        this.studentAnswer.answer = answer
                        // 要改成update///////////////////////////////////////////
                        this.UPDATE_STUDENTANSWER(this.studentAnswer)
                    }
                })
            },
        },
    }
</script>

<style scoped>
    div {
        color: black;
    }
</style>