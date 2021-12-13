<template>
    <div style="text-align: center;">
        <div id="mystyle" class="mystyle">
            <a-tabs v-model="activeKey" @change="callback">
                <a-tab-pane key="1" tab="学生" :forceRender="true">
                        <LoginPanel role="1" ref="slogin">
                        <div slot="username">
                            <a-input

                                    v-decorator="[
                                          'userName',
                                          { rules: [{ required: true, message: `请输入学号！` }] },
                                        ]"
                                    placeholder="请输入学号"
                            >
                                <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)"/>
                            </a-input>
                        </div>

                    </LoginPanel>
                </a-tab-pane>
                <a-tab-pane key="2" tab="教师" :forceRender="true">
                    <LoginPanel role="2" ref="tlogin">
                        <div slot="username">
                            <a-input

                                    v-decorator="[
                                          'userName',
                                          { rules: [{ required: true, message: `请输入工号！` }] },
                                        ]"
                                    placeholder="请输入工号"
                            >
                                <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)"/>
                            </a-input>
                        </div>

                    </LoginPanel>
                </a-tab-pane>
                <a-tab-pane key="3" tab="管理" :forceRender="true">
                    <LoginPanel role="3" ref="mlogin">
                        <div slot="username">
                            <a-input

                                    v-decorator="[
                                          'userName',
                                          { rules: [{ required: true, message: `请输入账号！` }] },
                                        ]"
                                    placeholder="请输入账号"
                            >
                                <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)"/>
                            </a-input>
                        </div>

                    </LoginPanel>
                </a-tab-pane>
            </a-tabs>
            <span v-if="activeKey!=3" style="float: right;color: red;font-size: 10px;font-weight: bold">注：忘记密码请联系网站管理员</span>
        </div>

    </div>
</template>

<script>
    import LoginPanel from "../components/LoginPanel";
    export default {
        name: "Login",
        components: {LoginPanel},
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'normal_login'});
        },
        data() {
            return {
                activeKey:"1",
            }
        },
        mounted() {
            if (this.$route.query.activeKey){
                this.activeKey = this.$route.query.activeKey
            }
            if (this.$cookie.get("userName")){
                this.activeKey = this.$cookie.get("role")
            }
        },
        methods: {

            callback(key) {
                if (this.$refs.slogin)
                    this.$refs.slogin.form.resetFields();
                if (this.$refs.tlogin)
                    this.$refs.tlogin.form.resetFields();
                if (this.$refs.mlogin)
                    this.$refs.mlogin.form.resetFields();
            },
        },
    };
</script>
<style >

    #mystyle {
        padding: 30px 50px;
        width: 35%;
        height: 55%;
        display: inline-block;
        background-color: #d1e6e9;
        position: absolute;
        top: 20%;
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
