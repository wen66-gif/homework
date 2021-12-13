<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #cccccc; display: flex;background-color: #3894FF">
        <div style="width: 200px; padding-left: 30px; font-weight: bold; color: white" >欢迎使用作业管理系统</div>
        <div style="flex: 1"></div>
        <div style="width: 100px; ">
            <a-dropdown>
                <a class="ant-dropdown-link" @click="e => e.preventDefault()" style="color: white">
                    {{username}} <a-icon type="down" />
                </a>
                <a-menu slot="overlay">
                    <a-menu-item>
                        <span @click="updatePassword">修改密码</span>
                    </a-menu-item>
                    <a-menu-item>
                        <span @click="logout">退出登录</span>
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
        <a-modal
                title="修改密码"
                :visible="visible"
                :confirm-loading="confirmLoading"
                @ok="handleOk"
                @cancel="handleCancel"
                okText="确定"
                cancelText="取消"
        >
            <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
                <a-form-item label="原密码">
                    <a-input-password
                            v-decorator="[
                              'oldPassword',
                              {
                                rules: [
                                  {
                                    required: true,
                                    message: '密码必须由字母或数字组成，长度6-12',
                                    pattern: /^[\w]{6,12}$/
                                  },
                                ],
                              },
                            ]"
                    />
                </a-form-item>
                <a-form-item label="新密码" has-feedback>
                    <a-input-password
                            v-decorator="[
                              'newPassword',
                              {
                                rules: [
                                  {
                                    required: true,
                                    message: '密码必须由字母或数字组成，长度6-12',
                                    pattern: /^[\w]{6,12}$/
                                  },
                                ],
                              },
                            ]"
                    />
                </a-form-item>
                <a-form-item  label="确认密码" has-feedback>
                    <a-input-password
                            v-decorator="[
                              'confirmPassword',
                              {
                                rules: [
                                  {
                                    required: true,
                                    message: '请确认你的密码!',
                                  },
                                  {
                                    validator: compareToNewPassword,
                                  },
                                ],
                              },
                            ]"
                            @blur="handleConfirmBlur"
                    />
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
    import {mapMutations, mapState} from "vuex";
    import axios from "axios"
    export default {
        name: "Header",
        data(){
            return{
                confirmDirty: false,
                visible:false,
                confirmLoading:false,
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        computed:{
            ...mapState(['username'])
        },
        methods:{
            handleConfirmBlur(e) {
                const value = e.target.value;
                this.confirmDirty = this.confirmDirty || !!value;
            },
            compareToNewPassword(rule, value, callback) {
                const form = this.form;
                if (value && value !== form.getFieldValue('newPassword')) {
                    callback('两次输入的密码不一致');
                } else {
                    callback();
                }
            },

            ...mapMutations(['CLEAR_TOKEN','CLEAR_USERNAME']),
            logout(){
                // 清除token
                this.CLEAR_TOKEN()
                // 清除用户名
                this.CLEAR_USERNAME()
                // 跳转到登录页
                this.$router.push({
                    path:'/login',
                    query:{
                        activeKey:this.$cookie.get("role")
                    }
                })
            },
            updatePassword(){
                this.visible = true
            },
            handleOk(e){
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        console.log(values)
                        axios.put("/updatePassword",values,{headers: {
                                'Content-Type': 'application/json'
                            }}).then(res=>{
                                if (res.data.code==="0"){
                                    this.$message.success("修改密码成功")
                                    this.visible = false
                                    this.form.resetFields()
                                }
                                else
                                    this.$message.error(res.data.msg)
                        })

                    }

                });
            },
            handleCancel(){
                this.visible=false
                this.form.resetFields()
            }
        }
    }
</script>

<style scoped>

</style>