<template>
    <div>
        <a-form
                :form="form"
                @submit="handleSubmit"
                :label-col="{ span: 1 }"
                :wrapper-col="{ span: 12 }"
        >
            <a-form-item label="分数">
                <a-input-number
                        :min="1"
                        v-decorator="['score', { rules: [{ required: true, message: '请输入分数！' }] }]"/>
            </a-form-item>
            <a-form-item label="题干">
                <a-input
                        v-decorator="['title', { rules: [{ required: true, message: '请输入题干！' }] }]"
                />

            </a-form-item>
            <div>
                <label style="color: black;margin-left: 10px">选项：</label>
                <span style="color: red;margin-left: 10px;font-size: 10px">（选中为正确答案）</span>
            </div>
            <a-form-item
                    style="margin-top: 10px;margin-left: 10px"
                    :wrapper-col="{ span: 6 }"
            >
                <a-radio-group
                        @change="onChange"
                        v-decorator="[
              'trueAnswer',
              {rules: [{ required: true, message: '请选择正确答案！' }],
              }
            ]"
                >
                    <a-radio value='A' style="margin-bottom: 20px;">
                        A
                        <a-input style="margin-left: 20px;width: 400px" v-model="A"/>
                        <span style="margin-left: 10px;color: red" v-if="!A">请输入选项内容</span>
                    </a-radio>
                    <a-radio value='2' style="margin-bottom: 20px">
                        B
                        <a-input style="margin-left: 20px;width: 400px" v-model="B"/>
                        <span style="margin-left: 10px;color: red" v-if="!B">请输入选项内容</span>
                    </a-radio>
                    <a-radio value='3' style="margin-bottom: 20px">
                        C
                        <a-input style="margin-left: 20px;width: 400px" v-model="C"/>
                        <span style="margin-left: 10px;color: red" v-if="!C">请输入选项内容</span>
                    </a-radio>
                    <a-radio value='4' style="margin-bottom: 20px">
                        D
                        <a-input style="margin-left: 20px;width: 400px" v-model="D"/>
                        <span style="margin-left: 10px;color: red" v-if="!D">请输入选项内容</span>
                    </a-radio>

                    </a-radio-group>
            </a-form-item>
        </a-form>
        <a-button shape="round" type="primary" size="large" style="margin-left: 45%" @click="handleSubmit">保存题目</a-button>
    </div>
</template>

<script>
    import qs from "qs";
    import axios from "axios";
    import {mapActions} from "vuex";

    export default {
        name: "SingleChoice",
        data(){
            return{
                // id作用：判断要更新还是新增
                id:'',
                A:'',
                B:'',
                C:'',
                D:'',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        methods:{
            ...mapActions(['addOrUpdate']),
            handleSubmit(){
                this.form.validateFields((err, values) => {
                    if (!err && this.A && this.B && this.C && this.D) {
                        values.A = this.A
                        values.B = this.B
                        values.C = this.C
                        values.D = this.D
                        values.id = this.id
                        values.type = 1
                        this.addOrUpdate(values)
                        this.$message.success("保存成功")
                        this.form.resetFields()
                        this.A=''
                        this.B=''
                        this.C=''
                        this.D=''
                        console.log('Received values of form: ', values);
                    }
                });
            },
            // 单选按钮改变
            onChange(e) {
                console.log('radio checked', e.target.value);
            },

        }
    }
</script>

<style scoped>

</style>