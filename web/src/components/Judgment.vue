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
                        :min="0"
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
                        v-decorator="[
              'trueAnswer',
              {rules: [{ required: true, message: '请选择正确答案！' }],
              }
            ]"
                >
                    <a-radio value='1' style="margin-bottom: 20px;">
                        正确
                    </a-radio>
                    <a-radio value='2' style="margin-bottom: 20px;">
                        错误
                    </a-radio>
                </a-radio-group>
            </a-form-item>
        </a-form>
        <a-button shape="round" type="primary" size="large" style="margin-left: 45%" @click="handleSubmit">保存题目</a-button>
    </div>

</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "Judgment",
        data(){
            return{
                id:'',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        methods: {
            ...mapActions(['addOrUpdate']),
            handleSubmit() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        values.type = 3
                        values.id = this.id
                        this.addOrUpdate(values)
                        this.$message.success("保存成功")
                        this.form.resetFields()
                        console.log('Received values of form: ', values);
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>