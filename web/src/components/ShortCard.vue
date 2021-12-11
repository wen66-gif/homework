<template>
    <div >
        <div >{{question.no}}.{{question.title}}（{{question.score}}分）</div>
        <div style="margin-top: 10px">
            <a-form
                    @blur.native.capture="handleSubmit"
                    :form="form"
                    :label-col="{ span: 2 }"
                    :wrapper-col="{ span: 15 }"
            >
                <a-form-item >
                    <a-textarea
                            style="width: 600px;"
                            v-decorator="['answer', { rules: [{ required: true, message: '请输入正确答案！' }] }]"
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
        name: "ShortCard",
        props:['question','studentAnswer'],
        data(){
            return{

                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        mounted() {

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
        },
    }
</script>

<style scoped>
    div {
        color: black;
    }
</style>