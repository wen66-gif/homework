<template>
    <div>
        <div style="width: 95%;height: 620px;margin-left:30px;margin-right:30px;margin-top:20px;background-color: white;">
            <div style="padding-left: 20px">
                <a-tabs v-model="activeKey" size="large" :animated="false" :tabBarGutter=100>
                    <a-tab-pane key="1" tab="教师管理">
                        <TeacherManager />
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="学生管理">
                        <StudentManager />
                    </a-tab-pane>
                    <a-tab-pane key="3" tab="班级管理">
                        <ClassManager />
                    </a-tab-pane>
                </a-tabs>
            </div>
        </div>
    </div>
</template>

<script>
    import TeacherManager from "../components/TeacherManager";
    import StudentManager from "../components/StudentManager";
    import ClassManager from "../components/ClassManager";
    import axios from "axios"
    import {mapMutations} from "vuex";
    export default {
        name: "ManagerHome",
        components: {ClassManager, StudentManager, TeacherManager},
        data(){
            return{
                activeKey:"1",
            }
        },
        mounted() {
            axios.get("/manager/getManagerName").then(res=>{
                this.SET_USERNAME(res.data.data)
            })
        },
        methods:{
            ...mapMutations(['SET_USERNAME']),
        }
    }
</script>

<style scoped>

</style>