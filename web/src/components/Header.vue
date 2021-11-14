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
                        <a href="javascript:;">修改密码</a>
                    </a-menu-item>
                    <a-menu-item>
                        <a href="javascript:;">退出登录</a>
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "Header",
        computed:{
            ...mapState(['username'])
        }
    }
</script>

<style scoped>

</style>