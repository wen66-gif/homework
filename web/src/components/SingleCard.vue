<template>
    <div>
        <div >{{question.no}}.{{question.title}}（{{question.score}}分）</div>
        <div >
            <a-form
                    @blur.native.capture="handleSubmit"
                    :form="form"
                    :label-col="{ span: 1 }"
                    :wrapper-col="{ span: 15 }"
            >
                <a-form-item>
                    <a-radio-group
                            @change="onChange"
                            v-decorator="['answer', { rules: [{ required: true, message: '请输入正确答案！' }] }]"
                    >
                        <a-radio value='1' :style="radioStyle">
                            A.{{question.A}}
                        </a-radio>
                        <a-radio value='2' :style="radioStyle">
                            B.{{question.B}}
                        </a-radio>
                        <a-radio value='3' :style="radioStyle">
                            C.{{question.C}}
                        </a-radio>
                        <a-radio value='4' :style="radioStyle">
                            D.{{question.D}}
                        </a-radio>
                    </a-radio-group>
                </a-form-item>
            </a-form>
        </div>
    </div>

</template>

<script>
    import {mapMutations} from "vuex";

    export default {
        name: "SingleAnswer",
        props:['question','studentAnswer'],
        data(){
            return{
                form: this.$form.createForm(this, { name: 'coordinated' }),

                value:'',
                radioStyle: {
                    display: 'block',
                    height: '30px',
                    lineHeight: '30px',
                },
            }
        },
        methods:{
            ...mapMutations(['ADD_STUDENTANSWER','UPDATE_STUDENTANSWER']),
            // 失去焦点的时候将答案保存到vuex
            handleSubmit(){
                this.form.validateFields((err, values) => {
                    if (!err) {
                        this.studentAnswer.answer = values.answer
                        // 要改成update///////////////////////////////////////////

                        this.UPDATE_STUDENTANSWER(this.studentAnswer)
                    }
                })
            },
            // 单选按钮改变
            onChange(e) {
            },
        }
    }
</script>

<style scoped>
    div {
        color: black;
    }
</style>