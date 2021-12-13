<template>
    <div style="text-align: center;">
        <div id="main">
            <a-tabs v-model="activeKey" @change="callback">
                <a-tab-pane key="1" tab="学生">
                    <a-form
                            :form="studentForm"
                            id="components-form-demo-normal-login"
                            class="login-form"
                            @submit="studentSubmit"
                            :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                        <a-form-item label="学号">
                            <a-input
                                    v-decorator="['no', { rules: [{ required: true, message: '学号只包括数字，长度为8！',pattern: /^[0-9]{8}$/ }] }]"
                                    placeholder="请填写学生学号"
                            />
                        </a-form-item>
                        <a-form-item label="姓名">
                            <a-input
                                    v-decorator="['name', { rules: [{ required: true, message: '请输入姓名！' }] }]"
                                    placeholder="请填写学生姓名"
                            />
                        </a-form-item>
                        <a-form-item label="性别">
                            <a-radio-group v-decorator="['sex',{ rules: [{ required: true, message: '请选择性别！' }] }]">
                                <a-radio :value=1>
                                    男
                                </a-radio>
                                <a-radio :value=2>
                                    女
                                </a-radio>
                            </a-radio-group>
                        </a-form-item>
                        <a-form-item label="班级">
                            <a-select
                                    show-search
                                    :filter-option="filterOption"
                                    placeholder="请选择班级"
                                    v-decorator="[
                                    'classId',
                                    { rules: [{ required: true, message: '请选择班级！' }] }
                                ]"
                                    style="width: 180px;margin-left: 10px">
                                <a-select-option v-for="c in classes" :key="c.id">
                                    {{c.name}}
                                </a-select-option>
                            </a-select>
                        </a-form-item>
                        <a-form-item label="密码">
                            <a-input
                                    v-decorator="['password',
                                 { rules: [{
                                     required: true,
                                     message: '密码必须由字母或数字组成，长度6-12',
                                     pattern: /^[\w]{6,12}$/ }] }]"
                                    placeholder="请输入密码"
                            />
                        </a-form-item>
                        <a-form-item label="验证码">
                                <div style="display: flex">
                                    <a-input
                                            v-decorator="[
                                            'validCode',
                                            { rules: [{ required: true, message: '请输入验证码!' }] },
                                        ]"
                                            placeholder="请输入验证码"
                                            style="width: 60%"
                                    />
                                    <ValidCode @input="createValidCode" ref="validCode"/>
                                </div>
                        </a-form-item>
                        <a-button type="primary" html-type="submit" class="login-form-button">
                            注册
                        </a-button>
                    </a-form>
                </a-tab-pane>
                <a-tab-pane key="2" tab="教师">
                    <a-form
                            :form="teacherForm"
                            id="components-form-demo-normal-login"
                            class="login-form"
                            @submit="teacherSubmit"
                            :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                        <a-form-item label="工号">
                            <a-input
                                    v-decorator="['no', { rules: [{ required: true, message: '工号只包括数字，长度为8！',pattern: /^[0-9]{8}$/ }] }]"
                                    placeholder="请填写教师工号"
                            />
                        </a-form-item>
                        <a-form-item label="姓名">
                            <a-input
                                    v-decorator="['name', { rules: [{ required: true, message: '请输入姓名！' }] }]"
                                    placeholder="请填写教师姓名"
                            />
                        </a-form-item>
                        <a-form-item label="性别">
                            <a-radio-group v-decorator="['sex',{ rules: [{ required: true, message: '请选择性别！' }] }]">
                                <a-radio :value=1>
                                    男
                                </a-radio>
                                <a-radio :value=2>
                                    女
                                </a-radio>
                            </a-radio-group>
                        </a-form-item>
                        <a-form-item label="密码">
                            <a-input
                                    v-decorator="['password',
                                 { rules: [{
                                     required: true,
                                     message: '密码必须由字母或数字组成，长度6-12',
                                     pattern: /^[\w]{6,12}$/ }] }]"
                                    placeholder="请输入密码"
                            />
                        </a-form-item>
                        <a-form-item label="验证码">
                            <div style="display: flex">
                                <a-input
                                        v-decorator="[
                                            'validCode',
                                            { rules: [{ required: true, message: '请输入验证码!' }] },
                                        ]"
                                        placeholder="请输入验证码"
                                        style="width: 60%"
                                />
                                <ValidCode @input="createValidCode" ref="validCode"/>
                            </div>
                        </a-form-item>
                        <a-button type="primary" html-type="submit" class="login-form-button">
                            注册
                        </a-button>
                    </a-form>
                </a-tab-pane>
            </a-tabs>
        </div>

    </div>
</template>

<script>
    import ValidCode from "../components/ValidCode";
    import axios from "axios";
    export default {
        name: "Regist",
        components: {ValidCode},
        data() {
            return {
                classes:[],
                studentForm: this.$form.createForm(this, { name: 'coordinated' }),
                teacherForm:this.$form.createForm(this, { name: 'coordinated' }),
                activeKey:"1",
            }
        },
        mounted() {
            if (this.$route.query.activeKey){
                this.activeKey = this.$route.query.activeKey
            }
            axios.get("/allClasses").then(res=>{
                if (res.data.code==="0"){
                    this.classes = res.data.data
                }
                else
                    this.$message.error(res.data.msg)
            })
        },
        methods: {
            // 接收验证码组件提交的 4位验证码
            createValidCode(data) {
                this.validCode = data
            },
            filterOption(input, option) {
                return (
                    option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
                );
            },
            studentSubmit(e) {
                e.preventDefault();
                this.studentForm.validateFields((err, values) => {
                    if (!err) {
                        if (this.validCode.toLowerCase() != this.studentForm.getFieldValue("validCode").toLowerCase()){
                            this.$message.error("验证码错误");
                        }
                        else {

                                axios.post("/saveStudent",values).then(res=>{
                                    if (res.data.code === "0"){
                                        this.$message.success("新增学生成功")
                                        this.$cookie.set("userName", this.studentForm.getFieldValue("no"), 7);
                                        this.$cookie.set("password", this.studentForm.getFieldValue("password"), 7);
                                        this.$cookie.set("role",this.activeKey,1)
                                        this.$router.push({
                                            path:"/login",
                                            query:{
                                                activeKey:this.activeKey
                                            }
                                        })
                                    }
                                    else{
                                        this.$message.error(res.data.msg)
                                    }
                                })

                        }

                    }

                });
            },
            teacherSubmit(e) {
                e.preventDefault();
                this.teacherForm.validateFields((err, values) => {
                    if (!err) {
                        if (this.validCode.toLowerCase() != this.teacherForm.getFieldValue("validCode").toLowerCase()){
                            this.$message.error("验证码错误");
                        }
                        else {

                            axios.post("/saveTeacher",values).then(res=>{
                                if (res.data.code === "0"){
                                    this.$message.success("新增教师成功")
                                    this.$cookie.set("userName", this.teacherForm.getFieldValue("no"), 7);
                                    this.$cookie.set("password", this.teacherForm.getFieldValue("password"), 7);
                                    this.$cookie.set("role",this.activeKey,1)
                                    this.$router.push({
                                        path:"/login",
                                        query:{
                                            activeKey:this.activeKey
                                        }
                                    })
                                }
                                else{
                                    this.$message.error(res.data.msg)
                                }
                            })

                        }

                    }

                });
            },
            callback(key) {
                this.studentForm.resetFields()
                this.teacherForm.resetFields()
            },
        },
    };
</script>
<style scoped>

    #main {
        padding: 20px 30px;
        width: 35%;
        height: 70%;
        display: inline-block;
        background-color: #d1e6e9;
        position: absolute;
        top: 15%;
        left: 30%;
    }

    #components-form-demo-normal-login .login-form {
        max-width: 300px;
    }

    #components-form-demo-normal-login .login-form-regist {
        float: right;
    }

    #components-form-demo-normal-login .login-form-remember {
         float: left;
     }

    #components-form-demo-normal-login .login-form-button {
        width: 100%;
    }
</style>
