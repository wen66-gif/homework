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
                        <span>修改密码</span>
                    </a-menu-item>
                    <a-menu-item>
                        <span @click="logout">退出登录</span>
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
    </div>
</template>

<script>
    import {mapMutations, mapState} from "vuex";

    export default {
        name: "Header",
        computed:{
            ...mapState(['username'])
        },
        methods:{
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
            }
        }
    }
</script>

<style scoped>

</style>