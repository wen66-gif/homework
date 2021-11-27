<template>
    <a-form
            id="components-form-demo-normal-login"
            :form="form"
            class="login-form"
            @submit="handleSubmit"
    >
        <a-form-item>
            <a-input
                    v-decorator="[
          'no',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
                    placeholder="Username"
            >
                <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
            </a-input>
        </a-form-item>
        <a-form-item>
            <a-input
                    v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
                    type="password"
                    placeholder="Password"
            >
                <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
            </a-input>
        </a-form-item>
        <a-form-item>
            <a-checkbox
                    v-decorator="[
          'remember',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
            >
                Remember me
            </a-checkbox>
            <a class="login-form-forgot" href="">
                Forgot password
            </a>
            <a-button type="primary" html-type="submit" class="login-form-button" >
                Log in
            </a-button>
            Or
            <a href="">
                register now!
            </a>
        </a-form-item>
    </a-form>
</template>

<script>
    import qs from 'qs'
    import axios from 'axios'
    import {mapMutations,mapState} from "vuex";
    export default {
        name: "Login",
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'normal_login' });
        },
        computed:{
            ...mapState(['userToken'])
        },
        methods: {
            ...mapMutations(['SET_TOKEN','SET_USERNAME','ADD_CHOOSETYPE']),
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        // 将数据格式化为字符串
                        let data = qs.stringify({no:values.no,password:values.password})
                        console.log("data"+data)
                        axios.post("/login",data).then(res=>{

                            if (res.data.code === "0"){
                                this.SET_TOKEN(res.data.data)
                                this.$router.push({
                                    name:"mycourse"
                                })
                                this.$message.success("登录成功")
                            }
                            else {
                                this.$message.error("用户名或密码错误")
                            }
                        })

                        console.log('Received values of form: ', values);
                    }
                });
            },
        },
    }

</script>

<style scoped>
    #components-form-demo-normal-login .login-form {
        max-width: 300px;
    }
    #components-form-demo-normal-login .login-form-forgot {
        float: right;
    }
    #components-form-demo-normal-login .login-form-button {
        width: 100%;
    }
</style>