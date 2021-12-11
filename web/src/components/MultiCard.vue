<template>
    <div>
        <div >{{question.no}}.{{question.title}}（{{question.score}}分）</div>
        <div>
            <a-form
                    @blur.native.capture="handleSubmit"
                    :form="form"
                    :label-col="{ span: 1 }"
                    :wrapper-col="{ span: 15 }"
            >
                <a-form-item>
                    <a-checkbox-group
                            @change="onChange"
                            v-decorator="['answer', { rules: [{ required: true, message: '请输入正确答案！' }] }]"

                    >
                        <a-row id="main">
                            <a-col :style="radioStyle">
                                <a-checkbox value="1">
                                    A.{{question.A}}
                                </a-checkbox>
                            </a-col>
                            <a-col :style="radioStyle">
                                <a-checkbox value="2">
                                    B.{{question.B}}
                                </a-checkbox>
                            </a-col>
                            <a-col :style="radioStyle">
                                <a-checkbox value="3">
                                    C.{{question.C}}
                                </a-checkbox>
                            </a-col>
                            <a-col :style="radioStyle">
                                <a-checkbox value="4">
                                    D.{{question.D}}
                                </a-checkbox>
                            </a-col>
                        </a-row>
                    </a-checkbox-group>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script>
    import {mapMutations} from "vuex";

    export default {
        name: "MultiCard",
        props:['question','studentAnswer'],
        data(){
            return{
                form: this.$form.createForm(this, { name: 'coordinated' }),

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
                        this.studentAnswer.answer = values.answer.join("_")
                        // 要改成update///////////////////////////////////////////
                        this.UPDATE_STUDENTANSWER(this.studentAnswer)
                    }
                })
            },
            onChange(checkedValues) {
            },
        }
    }
</script>

<style scoped>
    #main {
        margin-top: 5px;
    }
    div {
        color: black;
    }
</style>