<template>
    <div style="padding-left: 50px;padding-right: 50px;padding-top: 35px">
        <div  style="margin-bottom: 18px;">
            <span style="color: black">我学的课</span>
        </div>
        <div >
            <a-row :gutter="[{ xs: 8, sm: 16, md: 24, lg: 32 }, { xs: 8, sm: 16, md: 24, lg: 32 }]">
                <a-col class="gutter-row" :span="6" v-for="course in courses" :key="course.id">

                        <a-card :hoverable="true" style="width: 300px;height: 220px" >
                            <router-link :to="{
                                            path:'/myHomework',
                                            query:{
                                                courseId:course.id
                                            }
                                        }">
                                <p style="text-align: center;font-weight: bold;font-size: 30px">{{course.name}}</p>
                                <p style="color: black">教师：{{course.teacher.name}}</p>
                                <div style="height: 21px">
                                    <span style="color: black">班级：</span>
                                    <span style="color: black;" v-for="c in course.classesList">{{c.name+" "}}</span>
                                </div>

                            </router-link>
                        </a-card>

                </a-col>
            </a-row>


        </div >
    </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'
    import {mapMutations, mapState} from "vuex";
    export default {
        name: "SCourse",
        data(){
            return{
                username:"",
                courses:[],
            }
        },
        mounted(){
            this.load()
        },
        methods:{
            ...mapMutations(['SET_USERNAME']),
            // 加载页面数据
            load() {
                axios.get("/course/allCourse",{params:{role:1}}).then(res=>{
                    this.courses = res.data.data.courses
                    this.SET_USERNAME(res.data.data.userName)
                })
            },
        }
    }
</script>

<style scoped>
    .gutter-example >>> .ant-row > div {
        background: transparent;
        border: 0;
    }
    .gutter-box {
        background: #00a0e9;
        padding: 5px 0;
    }
</style>