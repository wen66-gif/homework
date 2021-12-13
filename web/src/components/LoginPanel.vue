<template>
    <div>
        <a-form
                id="components-form-demo-normal-login"
                :form="form"
                class="login-form"
                @submit="handleSubmit"
        >
            <a-form-item>
                <!-- 使用具名插槽，输入账号的表单项由父组件决定-->
                <slot name="username"></slot>

            </a-form-item>
            <a-form-item>
                <a-input
                        v-decorator="[
                                      'password',
                                      { rules: [{ required: true, message: '请输入密码!' }] },
                                    ]"
                        type="password"
                        placeholder="请输入密码"
                >
                    <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)"/>
                </a-input>
            </a-form-item>
            <a-form-item>
                <div style="display: flex">
                    <a-input
                            v-decorator="[
                                            'validCode',
                                            { rules: [{ required: true, message: '请输入验证码!' }] },
                                        ]"
                            placeholder="请输入验证码"
                            style="width: 70%"
                    >
                        <a-icon slot="prefix" type="key" style="color: rgba(0,0,0,.25)"/>
                    </a-input>
                    <ValidCode @input="createValidCode" ref="validCode"/>
                </div>
            </a-form-item>
            <a-button type="primary" html-type="submit" class="login-form-button">
                登录
            </a-button>
            <a-form-item >
                <a-checkbox
                        v-decorator="[
                                      'remember',
                                      {
                                        valuePropName: 'checked',
                                        initialValue: true,
                                      },
                                    ]"
                        class="login-form-remember"
                >
                    记住密码
                </a-checkbox>
                <router-link
                        :to="{
                    path:'/regist',
                    query:{
                        activeKey:this.role
                    }
                }"
                        class="login-form-regist"
                        v-if="role!=3"
                >
                    没有账号？
                </router-link>

            </a-form-item>

        </a-form>
    </div>
</template>

<script>
    import ValidCode from "../components/ValidCode";
    import axios from "axios";
    import {mapMutations} from "vuex";
    export default {
        name: "StudentLogin",
        props:["role"],
        components: {ValidCode},
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'normal_login'});
        },
        mounted() {
            var userName = this.$cookie.get("userName")
            var password = this.$cookie.get("password")
            this.form.setFieldsValue({"userName":userName,"password":password})
        },

        methods: {
            ...mapMutations(['SET_TOKEN']),
            // 接收验证码组件提交的 4位验证码
            createValidCode(data) {
                this.validCode = data
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        if (this.validCode.toLowerCase() != this.form.getFieldValue("validCode").toLowerCase()){
                            this.$message.error("验证码错误");
                        }
                        else {
                            console.log('Received values of form: ', values);
                            let url = "/login/"+this.role;
                            axios.post(url,values).then(res=>{
                                if (res.data.code === "0"){
                                    // 记住账号密码
                                    if (this.form.getFieldValue("remember")) {
                                        //传入账号名，密码，和保存天数3个参数
                                        this.$cookie.set("userName", this.form.getFieldValue("userName"), 7);
                                        this.$cookie.set("password", this.form.getFieldValue("password"), 7);
                                        this.$cookie.set("role",this.role,7)
                                    }
                                    else {
                                        // 没有勾选记住密码，清除cookie内容
                                        this.$cookie.delete('userName')
                                        this.$cookie.delete('password')
                                    }
                                    this.SET_TOKEN(res.data.data)
                                    if (this.role === "1")
                                        this.$router.push("/stu_course");
                                    else if (this.role === "2")
                                        this.$router.push("/tea_course");
                                    else
                                        this.$router.push("/managerHome");
                                    this.$message.success("登录成功");
                                }
                                else {
                                    this.$message.error("用户名或密码错误")
                                }
                            })
                        }
                    }
                });
            },
        },
        data() {
            return {
                activeKey:"1",
                validCode:'',
            }
        },
    }
</script>

<style scoped>

</style>