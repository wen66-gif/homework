<template>
    <div>
        <a-form
                :form="form"
                @submit="handleSubmit"
                :label-col="{ span: 2 }"
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
            <!--<div>
                <label style="color: black;margin-left: 10px">参考答案：</label>
            </div>-->
            <a-form-item  label="参考答案：">
                <a-textarea
                        style="width: 600px;"
                        v-decorator="[`trueAnswer`, { rules: [{ required: true,message:'请输入参考答案'}] }]"
                        :auto-size="{ minRows: 3, maxRows: 10 }"
                />
            </a-form-item>
        </a-form>
        <a-button shape="round" type="primary" size="large" style="margin-left: 45%" @click="handleSubmit">保存题目</a-button>
    </div>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "ShortAnswer",
        data(){
            return{
                id:'',
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        methods:{
            ...mapActions(['addOrUpdate']),
            handleSubmit(){
                this.form.validateFields((err, values) => {
                    if (!err) {
                        values.type = 5
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